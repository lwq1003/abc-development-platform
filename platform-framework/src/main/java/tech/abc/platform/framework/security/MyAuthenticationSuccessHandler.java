package tech.abc.platform.framework.security;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.constant.TreeDefaultConstant;
import tech.abc.platform.common.entity.MyUserDetails;
import tech.abc.platform.common.enums.ExecuteResultEnum;
import tech.abc.platform.common.enums.LogTypeEnum;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.utils.CacheUtil;
import tech.abc.platform.common.utils.JwtUtil;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.framework.config.PlatformConfig;
import tech.abc.platform.system.entity.Organization;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.enums.OrganizationTypeEnum;
import tech.abc.platform.system.enums.PermissionTypeEnum;
import tech.abc.platform.system.service.GroupUserService;
import tech.abc.platform.system.service.OrganizationService;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.system.vo.MenuTreeVO;
import tech.abc.platform.system.vo.MetaVO;
import tech.abc.platform.system.vo.UserVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 自定义登录成功处理器
 * 如继承父类则会内置跳转首页或权限验证失败前一页，会影响前端跳转，自己实现接口，去除了后端自动跳转
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Component
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public static final double VALIDATE_LICENCE_PERCENT = 0.2;
    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private CacheUtil cacheUtil;


    @Autowired
    private PlatformConfig platformConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private GroupUserService groupUserService;


    @Override
    @SystemLog(value = "登录成功", logType = LogTypeEnum.AUDIT, logRequestParam = false, executeResult = ExecuteResultEnum.SUCCESS)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {


        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        // 获取用户名
        String account = userDetails.getUsername().toLowerCase();

        // 查询用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getAccount, account);
        User user = userService.getOne(userQueryWrapper);

        // 重置登录失败次数
        userService.resetLoginFailureCount(user.getId());


        // 构造返回对象
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setAccount(user.getAccount());
        userVO.setName(user.getName());

        // 强制修改密码标志位
        userVO.setForceChangePasswordFlag(user.getForceChangePasswordFlag());
        // 判断是否超出密码修改时间
        if (userService.checkExceedPasswordChangeDays(user.getId())) {
            userVO.setForceChangePasswordFlag(YesOrNoEnum.YES.name());
        }
        // 生成令牌
        String token = jwtUtil.generateTokenWithSubject(user.getAccount(), platformConfig.getSystem().getTokenValidSpan() * 60);
        // 设置令牌
        userVO.setToken(token);


        // 获取权限
        List<PermissionItem> permissionList = userService.getPermission(user.getId());
        if (CollectionUtils.isNotEmpty(permissionList)) {
            // 获取按钮权限
            List<String> buttonPermission = permissionList.stream().filter(x -> x.getType()
                            .equals(PermissionTypeEnum.BUTTON.toString()))
                    .map(PermissionItem::getPermissionCode).distinct().collect(Collectors.toList());
            userVO.setButtonPermission(buttonPermission);
            // 获取菜单权限
            List<MenuTreeVO> moduleList = getMenu(permissionList);
            userVO.setMenuPermission(moduleList);
        }
        // 用户常用属性放入redis缓存
        fillUserProperty(user);
        cacheUser(user);

        // 构建返回
        ResponseEntity<Result> result = ResultUtil.success(userVO, "登录成功");
        ResultUtil.returnJsonToFront(response, result);

    }

    /**
     * 填充用户属性
     *
     * @param user 用户
     */
    private void fillUserProperty(User user) {
        // 填充属性
        user.setOrganizationName(organizationService.getById(user.getOrganization()).getName());
        // 逐级查找组织机构类型
        List<Organization> list = organizationService.list();
        String currentOrganizationId = user.getOrganization();
        StringBuilder organizationFullName = new StringBuilder();

        do {
            // 取当前组织机构
            Organization currentOrganization = null;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(currentOrganizationId)) {
                    currentOrganization = list.get(i);
                    break;
                }
            }
            organizationFullName.insert(0, currentOrganization.getName()).insert(0, "/");
            String organizationType = currentOrganization.getType();
            if (StringUtils.isNotBlank(organizationType)) {
                OrganizationTypeEnum organizationTypeEnum = OrganizationTypeEnum.valueOf(organizationType);
                switch (organizationTypeEnum) {
                    case MODULE:
                        user.setModuleId(currentOrganizationId);
                        break;
                    case DEPARTMENT:
                        // 存在多级部门时，只取直接上级部门，忽略高层级部门
                        if (StringUtils.isBlank(user.getDepartmentId())) {
                            user.setDepartmentId(currentOrganizationId);
                        }
                        break;
                    case COMPANY:
                        user.setCompanyId(currentOrganizationId);
                        break;
                    case GROUP:
                        user.setGroupId(currentOrganizationId);
                        break;
                    default:
                        break;
                }

            }
            // 指向上级
            currentOrganizationId = currentOrganization.getOrganization();
        }
        while (!currentOrganizationId.equals(TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID));
        user.setOrganizationFullName(organizationFullName.toString());

        //  填充用户组
        List<String> userGroupList = groupUserService.getUserGroupList(user.getId());
        user.setUserGroupList(userGroupList);
    }

    /**
     * 缓存用户
     *
     * @param user 用户
     */
    private void cacheUser(User user) {
        cacheUtil.set(user.getAccount(), JSON.toJSONString(user), platformConfig.getSystem().getTokenValidSpan() * 60, TimeUnit.SECONDS);
    }

    /**
     * 获取菜单
     * 目前支持两级
     *
     * @return
     */
    public List<MenuTreeVO> getMenu(List<PermissionItem> permissionList) {

        // 生成模块
        List<MenuTreeVO> moduleList
                = generateModule(permissionList, TreeDefaultConstant.DEFAULT_TREE_ROOT_ID);

        for (MenuTreeVO module : moduleList) {
            // 生成菜单
            List<MenuTreeVO> menus = generateMenu(permissionList, module.getId());
            // 若菜单为空，设置模块隐藏
            if (menus.size() == 0) {
                module.getMeta().setHidden(true);
            }
            module.setChildren(menus);

        }
        return moduleList;
    }


    /**
     * 生成模块
     */
    private List<MenuTreeVO> generateModule(List<PermissionItem> list, String parentId) {
        List<MenuTreeVO> result = new ArrayList<>();
        for (PermissionItem node : list) {
            // 获取类型为模块权限项
            if (node.getType().equals(PermissionTypeEnum.MODULE.toString())) {
                if (node.getPermissionItem().equals(parentId)) {
                    MenuTreeVO vo = new MenuTreeVO();
                    vo.setId(node.getId());
                    vo.setParentId(node.getPermissionItem());
                    vo.setName(node.getCode());
                    vo.setPath("/" + node.getCode());
                    vo.setComponent(node.getComponent());

                    MetaVO metaVO = new MetaVO();
                    metaVO.setTitle(node.getName());
                    metaVO.setIcon(node.getIcon());
                    metaVO.setHidden(false);
                    vo.setMeta(metaVO);
                    result.add(vo);
                }
            }
        }
        return result;
    }

    /**
     * 生成菜单
     */
    private List<MenuTreeVO> generateMenu(List<PermissionItem> list, String parentId) {
        List<MenuTreeVO> menus = new ArrayList<MenuTreeVO>();

        List<PermissionItem> permissionList
                = list.stream().filter(x -> x.getPermissionItem().equals(parentId)).collect(Collectors.toList());
        for (PermissionItem permission : permissionList) {

            if (permission.getType().equals(PermissionTypeEnum.MENU.toString())
                    || permission.getType().equals(PermissionTypeEnum.PAGE.toString())) {
                MenuTreeVO vo = new MenuTreeVO();
                vo.setId(permission.getId());
                vo.setParentId(permission.getPermissionItem());
                vo.setName(permission.getCode());
                vo.setPath(permission.getCode());
                vo.setComponent(permission.getComponent());


                MetaVO metaVO = new MetaVO();
                metaVO.setTitle(permission.getName());
                metaVO.setIcon(permission.getIcon());
                // 如果为页面，非菜单，则设置隐藏
                metaVO.setHidden(permission.getType().equals(PermissionTypeEnum.PAGE.toString()));

                vo.setMeta(metaVO);
                menus.add(vo);
                // 查找下级
                // 菜单规划为两级，因此此处未使用递归，而是再往下找一级即可
                List<MenuTreeVO> children = generateMenu(list, permission.getId());
                if (children != null && children.size() > 0) {
                    menus.addAll(children);
                }

            } else if (permission.getType().equals(PermissionTypeEnum.GROUP.toString())) {
                // 实际存在模块-分组-页面的情况，该分支将此部分的页面也纳入路由
                List<MenuTreeVO> children = generateMenu(list, permission.getId());
                if (children != null && children.size() > 0) {
                    menus.addAll(children);
                }
            }
        }
        return menus;
    }


}
