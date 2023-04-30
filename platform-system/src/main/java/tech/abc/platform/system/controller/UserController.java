package tech.abc.platform.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.AllowAuthenticated;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.enums.LogTypeEnum;
import tech.abc.platform.common.exception.SessionExpiredException;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.system.entity.GroupUser;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.service.GroupUserService;
import tech.abc.platform.system.service.OrganizationService;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.system.vo.UserVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-23
 */
@RestController
@RequestMapping("/system/user")
@Slf4j
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    @Autowired
    private GroupUserService groupUserService;


    @Autowired
    private OrganizationService organizationService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        User entity = userService.init();
        UserVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "用户-新增")
    @PreAuthorize("hasPermission(null,'system:user:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody UserVO vo) {
        User entity = convert2Entity(vo);
        userService.add(entity);
        UserVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "用户-修改")
    @PreAuthorize("hasPermission(null,'system:user:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody UserVO vo) {
        User entity = convert2Entity(vo);
        userService.modify(entity);
        UserVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "用户-删除")
    @PreAuthorize("hasPermission(null,'system:user:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        userService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "用户-分页")
    @PreAuthorize("hasPermission(null,'system:user:query')")
    public ResponseEntity<Result> page(UserVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<User> page = new Page<User>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setOrganization(null);
        }

        // 构造查询条件
        QueryWrapper<User> queryWrapper = QueryGenerator.generateQueryWrapper(User.class, queryVO, sortInfo);

        // 查询数据
        userService.page(page, queryWrapper);
        // 转换vo
        IPage<UserVO> pageVO = mapperFacade.map(page, IPage.class);
        List<UserVO> userVOList = convert2VO(page.getRecords());
        pageVO.setRecords(userVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "用户-列表")
    @PreAuthorize("hasPermission(null,'system:user:query')")
    public ResponseEntity<Result> list(UserVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<User> queryWrapper = QueryGenerator.generateQueryWrapper(User.class, queryVO, sortInfo);
        List<User> list = userService.list(queryWrapper);
        // 转换vo
        List<UserVO> userVOList = convert2VO(list);
        return ResultUtil.success(userVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "用户-详情")
    @PreAuthorize("hasPermission(null,'system:user:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        User entity = userService.query(id);
        UserVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "用户-复制新增")
    @PreAuthorize("hasPermission(null,'system:user:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        userService.addByCopy(id);
        return ResultUtil.success();
    }

    // endregion

    // region 扩展操作

    /**
     * 启用
     */
    @PutMapping("/{id}/enable")
    @SystemLog(value = "用户-启用")
    @PreAuthorize("hasPermission(null,'system:user:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        userService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "用户-停用")
    @PreAuthorize("hasPermission(null,'system:user:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        userService.disable(id);
        return ResultUtil.success();
    }


    /**
     * 管理员重置密码
     */
    @PutMapping("/{id}/resetPassword")
    @SystemLog(value = "重置密码", logType = LogTypeEnum.AUDIT)
    @PreAuthorize("hasPermission(null,'system:user:resetPassword')")
    public ResponseEntity<Result> resetPassword(@PathVariable String id) {
        userService.resetPassword(id);
        return ResultUtil.success();
    }


    /**
     * 解锁用户
     * 此处没加日志注解是因为在服务层处理
     */
    @PutMapping("/{id}/unlock")
    @PreAuthorize("hasPermission(null,'system:user:unlock')")
    public ResponseEntity<Result> unlock(@PathVariable String id) {
        userService.unlock(id);
        return ResultUtil.success();
    }

    /**
     * 用户修改密码
     */
    @PutMapping("/{id}/changePassword")
    @SystemLog(value = "修改密码", logType = LogTypeEnum.AUDIT)
    @AllowAuthenticated
    public ResponseEntity<Result> changePassword(@PathVariable String id, String oldPassword, String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return ResultUtil.success();
    }


    /**
     * 会话失效
     */
    @RequestMapping("/sessionInvalid")
    public ResponseEntity<Result> sessionInvalid() {
        log.info("会话超时");
        throw new SessionExpiredException("会话超时,请重新登录");
    }


    /**
     * 查询用户组对应的用户信息
     */
    @GetMapping("/userGroup/{userGroupId}")
    @SystemLog(value = "用户组-用户查询")
    @PreAuthorize("hasPermission(null,'system:userGroup:configUser')")
    public ResponseEntity<Result> getUser(@PathVariable String userGroupId, UserVO queryVO, PageInfo pageInfo,
                                          SortInfo sortInfo) {


        // 构造分页对象
        IPage<User> page = new Page<User>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setOrganization(null);
        }

        // 构造查询条件
        QueryWrapper<User> queryWrapper = QueryGenerator.generateQueryWrapper(User.class, queryVO, sortInfo);

        // 当未勾选全部用户复选框时，查询指定用户组下的用户
        if (queryVO.getIgnoreUserGroup() == null || queryVO.getIgnoreUserGroup() == false) {

            // 查找关联的用户
            QueryWrapper<GroupUser> userGroupQueryWrapper = new QueryWrapper<>();
            userGroupQueryWrapper.lambda().eq(GroupUser::getGroupId, userGroupId);
            List<GroupUser> groupUserList = groupUserService.list(userGroupQueryWrapper);
            List<String> userIdList = groupUserList.stream().map(x -> x.getUserId()).collect(Collectors.toList());
            if (userIdList.size() > 0) {
                // 附加用户组限制的范围
                queryWrapper.lambda().in(User::getId, userIdList);
            } else {
                // 无数据，构造空对象返回
                return ResultUtil.success(new Page<UserVO>());
            }
        }

        // 查询数据
        userService.page(page, queryWrapper);
        // 转换vo
        IPage<UserVO> pageVO = mapperFacade.map(page, IPage.class);
        List<UserVO> userVOList = convert2VO(page.getRecords());
        pageVO.setRecords(userVOList);
        return ResultUtil.success(pageVO);

    }


    /**
     * 获取用户所有用户组
     */
    @GetMapping("/{id}/getUserGroup")
    @SystemLog(value = "用户-获取用户组")
    @PreAuthorize("hasPermission(null,'system:user:query')")
    public ResponseEntity<Result> getUserGroup(@PathVariable String id) {

        List<String> idList = groupUserService.lambdaQuery().eq(GroupUser::getUserId, id).list()
                .stream().map(x -> x.getGroupId()).collect(Collectors.toList());

        return ResultUtil.success(idList);
    }


    /**
     * 保存用户与用户组对应关系
     */
    @PutMapping("/{id}/saveUserGroup")
    @SystemLog(value = "人员-配置用户组")
    public ResponseEntity<Result> saveUserGroup(@PathVariable String id,
                                                @RequestBody(required = false) List<String> userGroupIdList) {
        groupUserService.saveGroup(id, userGroupIdList);
        return ResultUtil.success();
    }


    /**
     * 保存用户组与用户对应关系
     */
    @PutMapping("/{id}/saveGroupUser")
    @SystemLog(value = "人员-配置用户组")
    public ResponseEntity<Result> saveGroupUser(@PathVariable String id,
                                                @RequestBody(required = false) List<String> userIdList) {
        groupUserService.saveGroup(id, userIdList);
        return ResultUtil.success();
    }

    /**
     * 获取用户信息
     * 根据用户id列表
     */
    @GetMapping("/getUser")
    @PreAuthorize("hasPermission(null,'system:user:query')")
    public ResponseEntity<Result> getUser(@RequestParam(required = false) List<String> idList) {

        if (CollectionUtils.isNotEmpty(idList)) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(User::getId, idList);
            List<User> userList = userService.list(queryWrapper);
            List<UserVO> userVOList = mapperFacade.mapAsList(userList, UserVO.class);
            return ResultUtil.success(userVOList);
        }
        return ResultUtil.success(new ArrayList<UserVO>());
    }


    private UserVO convert2VO(User entity) {
        UserVO vo = mapperFacade.map(entity, UserVO.class);
        vo.setGenderName(dictionaryUtil.getNameByCode("Gender", entity.getGender()));
        vo.setStatusName(dictionaryUtil.getNameByCode("UserStatus", entity.getStatus()));
        vo.setForceChangePasswordFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getForceChangePasswordFlag()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<UserVO> convert2VO(List<User> entityList) {
        List<UserVO> voList = new ArrayList<>(entityList.size());

        // 获取 组织机构 集合
        List<String> organizationList = entityList.stream().map(x -> x.getOrganization()).collect(Collectors.toList());
        Map<String, String> organizationNameMap = organizationService.getNameMap(organizationList);
        entityList.stream().forEach(x -> {
            UserVO vo = convert2VO(x);
            // 设置 组织机构
            vo.setOrganizationName(organizationNameMap.get(x.getOrganization()));
            voList.add(vo);
        });
        return voList;
    }


    private User convert2Entity(UserVO vo) {
        User entity = mapperFacade.map(vo, User.class);
        return entity;
    }

    // endregion
}