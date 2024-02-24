package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.LogTypeEnum;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.CacheUtil;
import tech.abc.platform.common.utils.EncryptUtil;
import tech.abc.platform.system.config.SystemConfig;
import tech.abc.platform.system.constant.SystemConstant;
import tech.abc.platform.system.entity.GroupUser;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.entity.UserPasswordChangeLog;
import tech.abc.platform.common.enums.ExecuteResultEnum;
import tech.abc.platform.system.enums.UserStatusEnum;
import tech.abc.platform.system.exception.PermissionItemExceptionEnum;
import tech.abc.platform.system.exception.UserExceptionEnum;
import tech.abc.platform.system.mapper.UserMapper;
import tech.abc.platform.system.service.*;
import tech.abc.platform.system.utils.PasswordUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 服务实现类
 *
 * @author wqliu
 * @date 2023-04-23
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ParamService paramService;

    @Autowired
    private UserPasswordChangeLogService userPasswordChangeLogService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionItemService permissionItemService;

    @Override
    public User init() {
        User entity = new User();
        entity.setStatus(StatusEnum.NORMAL.name());
        // 首次新增默认下次修改密码
        entity.setForceChangePasswordFlag(YesOrNoEnum.YES.toString());
        entity.setFailLoginCount(0);
        return entity;
    }

    @Override
    public void beforeAdd(User entity) {
        // 新增用户时，保存用户名为小写
        entity.setAccount(entity.getAccount().toLowerCase());


        // 验证账号全局唯一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getAccount, entity.getAccount());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(UserExceptionEnum.ACCOUNT_EXIST);
        }

        // 创建账号时初始化密码
        String initPassword = systemConfig.getUserInitPassword();
        entity.setPassword(EncryptUtil.bCryptPasswordEncode(initPassword));
        // 首次新增默认下次修改密码
        entity.setForceChangePasswordFlag(YesOrNoEnum.YES.toString());

    }

    @Override
    public void beforeModify(User entity) {
        // 修改用户时，保存用户名为小写
        entity.setAccount(entity.getAccount().toLowerCase());
        // 验证账号全局唯一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getAccount, entity.getAccount())
                .ne(User::getId, entity.getId());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(UserExceptionEnum.ACCOUNT_EXIST);
        }

    }


    @Override
    public void beforeRemove(User entity) {
        // 判断用户是否被授权用户组。
        QueryWrapper<GroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupUser::getUserId, entity.getId());
        long count = groupUserService.count(queryWrapper);
        if (count > 0) {
            // 若被授予用户组，则先清空
            groupUserService.remove(queryWrapper);
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<User> list = this.lambdaQuery().in(User::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(User entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    public void enable(String id) {
        User entity = getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        User entity = getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

    @Override
    public void resetPassword(String id) {
        User entity = getEntity(id);
        // 获取初始密码
        String initPassword = systemConfig.getUserInitPassword();
        // 变更状态
        entity.setStatus(UserStatusEnum.NORMAL.toString());
        // 清空锁定时间
        entity.setLockTime(null);
        // 重置登录失败次数
        entity.setFailLoginCount(0);

        // 设置密码加密
        String encrypPassword = EncryptUtil.bCryptPasswordEncode(initPassword);
        entity.setPassword(encrypPassword);
        // 设置下次登录强制修改密码
        entity.setForceChangePasswordFlag(YesOrNoEnum.YES.toString());
        // 修改
        modify(entity);

    }

    @Override
    @SystemLog(value = "锁定用户", logType = LogTypeEnum.AUDIT, executeResult = ExecuteResultEnum.SUCCESS)
    public void lock(String id) {
        User entity = getEntity(id);
        // 变更状态
        entity.setStatus(UserStatusEnum.LOCK.toString());
        // 清空锁定时间
        entity.setLockTime(LocalDateTime.now());
        // 修改
        modify(entity);
    }


    @Override
    @SystemLog(value = "解锁用户", logType = LogTypeEnum.AUDIT, executeResult = ExecuteResultEnum.SUCCESS)
    public void unlock(String id) {
        User entity = getEntity(id);
        // 变更状态
        entity.setStatus(UserStatusEnum.NORMAL.toString());
        // 清空锁定时间
        entity.setLockTime(null);
        // 重置登录失败次数
        entity.setFailLoginCount(0);
        // 修改
        modify(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String id, String oldPassword, String newPassword) {
        // 检验账号以及旧密码，验证新密码强度
        User user = checkAccountPassword(id, oldPassword, newPassword);
        // 校验密码安全规则
        checkSafePolicy(user, newPassword);
        // 修改密码
        // 设置密码加密
        String encrtyPassword = EncryptUtil.bCryptPasswordEncode(newPassword);
        user.setPassword(encrtyPassword);
        // 将强制修改密码标识位设置为否
        user.setForceChangePasswordFlag(YesOrNoEnum.NO.name());
        // 修改
        modify(user);

        // 记录日志
        UserPasswordChangeLog log = new UserPasswordChangeLog();
        log.setAccount(user.getAccount());
        log.setUserId(user.getId());
        log.setChangeTime(LocalDateTime.now());
        log.setOriginPassword(EncryptUtil.bCryptPasswordEncode(oldPassword));
        userPasswordChangeLogService.add(log);
    }

    /**
     * 校验账号账号以及旧密码校验
     */
    public User checkAccountPassword(String id, String oldPassword, String newPassword) {
        // 获取用户
        User entity = getEntity(id);

        // 验证旧密码
        if (!EncryptUtil.bCryptPasswordMatches(oldPassword, entity.getPassword())) {
            //  更新累计错误次数
            updateLoginFailureCount(entity.getId());
            // 口令不相同，出错
            throw new CustomException(UserExceptionEnum.PWD_CHANGE_NOT_CORRECT);
        }

        // 新密码是否与原密码相同
        if (oldPassword.equals(newPassword)) {
            throw new CustomException(UserExceptionEnum.PWD_OLD_NEW_SAME);
        }
        // 新密码复杂度验证：包括大写字母、小写字母、数字、特殊符号这4种类型中的3种
        if (!PasswordUtil.isComplexPassword(newPassword)) {
            throw new CustomException(UserExceptionEnum.PWD_CHANGE_NOT_STRONG);
        }
        // 验证新密码不能包含账号、电话号码或出生日期三者中任何一项
        if (!checkPasswordSimple(newPassword, entity)) {
            throw new CustomException(UserExceptionEnum.PWD_CHANGE_EASY);
        }
        return entity;
    }

    /**
     * 更新用户登录失败次数
     *
     * @param id 标识
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginFailureCount(String id) {
        // 获取用户
        User user = getEntity(id);
        // 计算累计输入次数
        int tryNumber = 0;
        if (user.getFailLoginCount() != null) {
            tryNumber = user.getFailLoginCount() + 1;
        } else {
            tryNumber = 1;
        }
        user.setFailLoginCount(tryNumber);
        // 更新用户
        modify(user);

    }

    @Override
    public Boolean checkTryCount(String id) {
        // 获取用户
        User user = getEntity(id);
        // 获取错误次数上限
        int maxTryCount = Integer.parseInt(paramService.getParamValue(SystemConstant.PASSWORD_INPUT_ERROR_TIMES));
        if (user.getFailLoginCount() >= maxTryCount) {
            return true;
        }
        return false;
    }

    @Override
    public void resetLoginFailureCount(String id) {
        User user = getEntity(id);
        if (user.getFailLoginCount() > 0) {
            user.setFailLoginCount(0);
            // 更新用户
            modify(user);
        }
    }

    @Override
    public boolean checkExceedPasswordChangeDays(String id) {

        // 获取系统配置，密码需修改的天数
        int passwordChangeDays = Integer.parseInt(paramService.getParamValue(SystemConstant.PASSWORD_UPDATE_INTERVAL));
        // 从密码修改日志中获取最后一次修改密码的时间
        QueryWrapper<UserPasswordChangeLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().last(" limit 1").orderByDesc(UserPasswordChangeLog::getChangeTime);

        UserPasswordChangeLog log = userPasswordChangeLogService.getOne(queryWrapper);
        // 当修改日志不为空，且超出配置时间时，视为超出
        if (log != null) {
            Duration duration = Duration.between(log.getChangeTime(), LocalDateTime.now());
            if (duration.toDays() >= passwordChangeDays) {
                return true;
            }
        }
        return false;

    }

    @Override
    public List<PermissionItem> getPermission(String id) {

        if (id.equals(SystemConstant.ADMIN_USER_ID)) {
            // 如果为系统内置超级管理员用户admin，id为1，则默认拥有所有权限项，无需配置
            QueryWrapper<PermissionItem> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().orderByAsc(PermissionItem::getOrderNo);
            return permissionItemService.list(queryWrapper);
        } else {
            return userMapper.getPermission(id);
        }

    }

    @Override
    public void checkPermission(String id, String permissionCode) {
        long count = userMapper.checkPermission(id, permissionCode);
        if (count == 0) {
            throw new CustomException(PermissionItemExceptionEnum.NO_PERMISSION);
        }
    }

    @Override
    public String getNameById(String id) {
        User user = query(id);
        return user.getName();
    }


    /**
     * 安全认证校验
     *
     * @param user
     * @param password
     * @return
     */
    public void checkSafePolicy(User user, String password) {
        // 密码长度
        // 系统配置密码最小长度
        Integer passwordLength = Integer.parseInt(paramService.getParamValue(SystemConstant.PASSWORD_LENGTH));
        // 比较密码长度
        if (password.length() < passwordLength) {
            throw new CustomException(UserExceptionEnum.PWD_CHANGE_NEED_LENGTH);
        }
        // n次以内不得设置相同的密码
        // 系统配置密码最小长度
        Integer passwordCount = Integer.parseInt(paramService.getParamValue(SystemConstant.PASSWORD_UPDATE_SAME_TIMES));

        // 从密码修改日志中获取最后N次修改的密码
        QueryWrapper<UserPasswordChangeLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserPasswordChangeLog::getId, user.getId())
                .orderByDesc(UserPasswordChangeLog::getChangeTime);
        queryWrapper.last("limit 0," + passwordCount);
        List<UserPasswordChangeLog> logList = userPasswordChangeLogService.list(queryWrapper);
        for (UserPasswordChangeLog log : logList) {
            if (EncryptUtil.bCryptPasswordMatches(password, log.getOriginPassword())) {
                throw new CustomException(UserExceptionEnum.PWD_CHANGE_CYCLE_PASS);
            }
        }

    }


    /**
     * 验证新密码不能包含账号、电话号码或出生日期三者中任何一项
     *
     * @param password
     * @param user
     * @return
     */
    private boolean checkPasswordSimple(String password, User user) {

        if (password.contains(user.getAccount())) {
            return false;
        }
        if (StringUtils.isNotBlank(user.getTelephone()) && password.contains(user.getTelephone())) {
            return false;
        }
        if (user.getBirthday() != null) {
            String birthday = user.getBirthday().toString().substring(0, 10);
            if (password.contains(birthday)) {
                return false;
            }
            birthday = birthday.replace("-", "");
            if (password.contains(birthday)) {
                return false;
            }
        }
        return true;
    }


}
