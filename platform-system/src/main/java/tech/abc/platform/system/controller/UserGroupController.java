package tech.abc.platform.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.constant.TableFieldConstant;
import tech.abc.platform.common.constant.TreeDefaultConstant;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.utils.TreeUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.common.vo.TreeVO;
import tech.abc.platform.system.entity.GroupPermissionItem;
import tech.abc.platform.system.entity.GroupUser;
import tech.abc.platform.system.entity.UserGroup;
import tech.abc.platform.system.service.GroupPermissionItemService;
import tech.abc.platform.system.service.GroupUserService;
import tech.abc.platform.system.service.UserGroupService;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.system.vo.UserGroupVO;
import tech.abc.platform.system.vo.UserVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户组 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-24
 */
@RestController
@RequestMapping("/system/userGroup")
@Slf4j
public class UserGroupController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private GroupPermissionItemService groupPermissionItemService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        UserGroup entity = userGroupService.init();
        UserGroupVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "用户组-新增")
    @PreAuthorize("hasPermission(null,'system:userGroup:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody UserGroupVO vo) {
        UserGroup entity = convert2Entity(vo);
        userGroupService.add(entity);
        UserGroupVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "用户组-修改")
    @PreAuthorize("hasPermission(null,'system:userGroup:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody UserGroupVO vo) {
        UserGroup entity = convert2Entity(vo);
        userGroupService.modify(entity);
        UserGroupVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "用户组-删除")
    @PreAuthorize("hasPermission(null,'system:userGroup:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        userGroupService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "用户组-分页")
    @PreAuthorize("hasPermission(null,'system:userGroup:query')")
    public ResponseEntity<Result> page(UserGroupVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<UserGroup> page = new Page<UserGroup>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setUserGroup(null);
        }

        // 构造查询条件
        QueryWrapper<UserGroup> queryWrapper = QueryGenerator.generateQueryWrapper(UserGroup.class, queryVO, sortInfo);

        // 查询数据
        userGroupService.page(page, queryWrapper);
        // 转换vo
        IPage<UserGroupVO> pageVO = mapperFacade.map(page, IPage.class);
        List<UserGroupVO> userGroupVOList = convert2VO(page.getRecords());
        pageVO.setRecords(userGroupVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "用户组-列表")
    @PreAuthorize("hasPermission(null,'system:userGroup:query')")
    public ResponseEntity<Result> list(UserGroupVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<UserGroup> queryWrapper = QueryGenerator.generateQueryWrapper(UserGroup.class, queryVO, sortInfo);
        List<UserGroup> list = userGroupService.list(queryWrapper);
        // 转换vo
        List<UserGroupVO> userGroupVOList = convert2VO(list);
        return ResultUtil.success(userGroupVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "用户组-详情")
    @PreAuthorize("hasPermission(null,'system:userGroup:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        UserGroup entity = userGroupService.query(id);
        UserGroupVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "用户组-复制新增")
    @PreAuthorize("hasPermission(null,'system:userGroup:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        userGroupService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作


    /**
     * 启用
     */
    @PutMapping("/{id}/enable")
    @SystemLog(value = "用户组-启用")
    @PreAuthorize("hasPermission(null,'system:userGroup:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {
        userGroupService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "用户组-停用")
    @PreAuthorize("hasPermission(null,'system:userGroup:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        userGroupService.disable(id);
        return ResultUtil.success();
    }


    /**
     * 根据根标识列表查询
     */
    @ApiOperation(value = "根据根标识列表查询")
    @GetMapping("/getByIdList")
    @PreAuthorize("hasPermission(null,'system:userGroup:query')")
    public ResponseEntity<Result> getByIdList(@RequestParam(required = false) List<String> idList) {

        if (CollectionUtils.isNotEmpty(idList)) {
            QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(UserGroup::getId, idList);
            List<UserGroup> list = userGroupService.list(queryWrapper);
            List<UserGroupVO> voList = mapperFacade.mapAsList(list, UserGroupVO.class);
            return ResultUtil.success(voList);
        }
        return ResultUtil.success(new ArrayList<UserVO>());
    }
    // endregion

    // region 用户相关操作

    /**
     * 新增用户
     */

    @PostMapping("/{id}/user")
    @SystemLog(value = "用户组-新增用户")
    @PreAuthorize("hasPermission(null,'system:userGroup:configUser')")
    public ResponseEntity<Result> addUser(@PathVariable String id, @RequestBody List<String> idList) {
        groupUserService.addUser(id, idList);
        return ResultUtil.success();
    }

    /**
     * 移除用户
     */

    @PutMapping("/{id}/user")
    @SystemLog(value = "用户组-移除用户")
    @PreAuthorize("hasPermission(null,'system:userGroup:configUser')")
    public ResponseEntity<Result> removeUser(@PathVariable String id, @RequestBody List<String> idList) {
        // 构造条件
        QueryWrapper<GroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GroupUser::getGroupId, id)
                .in(GroupUser::getUserId, idList);
        groupUserService.remove(queryWrapper);
        return ResultUtil.success();
    }

    // endregion

    // region 权限相关操作

    /**
     * 获取指权限信息
     */
    @GetMapping("/{id}/getPermission")
    @SystemLog(value = "用户组管理-权限查询")
    @PreAuthorize("hasPermission(null,'system:userGroup:configPermission')")
    public ResponseEntity<Result> getPermission(@PathVariable String id) {

        // 获取已有的权限
        QueryWrapper<GroupPermissionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupPermissionItem::getGroupId, id);
        List<GroupPermissionItem> list = groupPermissionItemService.list(queryWrapper);
        List<String> permissionIdList = list.stream().map(x -> x.getPermissionItemId()).collect(Collectors.toList());
        return ResultUtil.success(permissionIdList);
    }

    /**
     * 保存权限
     */

    @PutMapping("/{id}/savePermission")
    @SystemLog(value = "用户组管理-权限设置", logRequestParam = false)
    @PreAuthorize("hasPermission(null,'system:userGroup:configPermission')")
    public ResponseEntity<Result> savePermission(@PathVariable String id, @RequestBody(required = false) List<String> permissionIdList) {
        groupPermissionItemService.savePermission(id, permissionIdList);
        return ResultUtil.success();
    }


    // endregion

    // region 树操作

    /**
     * 获取树数据
     *
     * @return
     */
    @GetMapping("/tree")
    @PreAuthorize("hasPermission(null,'system:userGroup:query')")
    public ResponseEntity<Result> tree() {
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserGroup::getStatus, StatusEnum.NORMAL.toString());
        // 附加按照排序号排序
        queryWrapper.orderByAsc(TableFieldConstant.DEFAULT_SORT_FILED);
        List<UserGroup> list = userGroupService.list(queryWrapper);
        // 转化成树结构数据
        List<TreeVO> treeList = list.stream().map(e -> convert2TreeVO(e)).collect(Collectors.toList());
        List<TreeVO> tree = TreeUtil.buildTree(treeList, TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID);
        return ResultUtil.success(tree);
    }

    /**
     * 转换为树视图对象
     */
    private TreeVO convert2TreeVO(UserGroup entity) {
        TreeVO tree = new TreeVO();
        tree.setId(entity.getId());
        tree.setParentId(entity.getUserGroup());
        tree.setLabel(entity.getName());
        return tree;
    }

    // endregion
    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private UserGroupVO convert2VO(UserGroup entity) {
        UserGroupVO vo = mapperFacade.map(entity, UserGroupVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<UserGroupVO> convert2VO(List<UserGroup> entityList) {
        List<UserGroupVO> voList = new ArrayList<>(entityList.size());

        // 获取 上级 集合
        List<String> userGroupList = entityList.stream().map(x -> x.getUserGroup()).collect(Collectors.toList());
        Map<String, String> userGroupNameMap = userGroupService.getNameMap(userGroupList);
        entityList.stream().forEach(x -> {
            UserGroupVO vo = convert2VO(x);
            // 设置 上级
            vo.setUserGroupName(userGroupNameMap.get(x.getUserGroup()));
            voList.add(vo);
        });
        return voList;
    }


    private UserGroup convert2Entity(UserGroupVO vo) {
        UserGroup entity = mapperFacade.map(vo, UserGroup.class);
        return entity;
    }

    // endregion
}