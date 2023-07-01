package tech.abc.platform.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.service.PermissionItemService;
import tech.abc.platform.system.vo.PermissionItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限项 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-25
 */
@RestController
@RequestMapping("/system/permissionItem")
@Slf4j
public class PermissionItemController extends BaseController {
    @Autowired
    private PermissionItemService permissionItemService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        PermissionItem entity = permissionItemService.init();
        PermissionItemVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "权限项-新增")
    @PreAuthorize("hasPermission(null,'system:permissionItem:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody PermissionItemVO vo) {
        PermissionItem entity = convert2Entity(vo);
        permissionItemService.add(entity);
        PermissionItemVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "权限项-修改")
    @PreAuthorize("hasPermission(null,'system:permissionItem:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody PermissionItemVO vo) {
        PermissionItem entity = convert2Entity(vo);
        permissionItemService.modify(entity);
        PermissionItemVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "权限项-删除")
    @PreAuthorize("hasPermission(null,'system:permissionItem:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        permissionItemService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "权限项-分页")
    @PreAuthorize("hasPermission(null,'system:permissionItem:query')")
    public ResponseEntity<Result> page(PermissionItemVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<PermissionItem> page = new Page<PermissionItem>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setPermissionItem(null);
        }

        // 构造查询条件
        QueryWrapper<PermissionItem> queryWrapper = QueryGenerator.generateQueryWrapper(PermissionItem.class, queryVO, sortInfo);

        // 查询数据
        permissionItemService.page(page, queryWrapper);
        // 转换vo
        IPage<PermissionItemVO> pageVO = mapperFacade.map(page, IPage.class);
        List<PermissionItemVO> permissionItemVOList = convert2VO(page.getRecords());
        pageVO.setRecords(permissionItemVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "权限项-列表")
    @PreAuthorize("hasPermission(null,'system:permissionItem:query')")
    public ResponseEntity<Result> list(PermissionItemVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<PermissionItem> queryWrapper = QueryGenerator.generateQueryWrapper(PermissionItem.class, queryVO, sortInfo);
        List<PermissionItem> list = permissionItemService.list(queryWrapper);
        // 转换vo
        List<PermissionItemVO> permissionItemVOList = convert2VO(list);
        return ResultUtil.success(permissionItemVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "权限项-详情")
    @PreAuthorize("hasPermission(null,'system:permissionItem:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        PermissionItem entity = permissionItemService.query(id);
        PermissionItemVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "权限项-复制新增")
    @PreAuthorize("hasPermission(null,'system:permissionItem:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        permissionItemService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 启用
     */
    @PutMapping("/{id}/enable")
    @SystemLog(value = "权限-启用")
    @PreAuthorize("hasPermission(null,'system:permissionItem:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        permissionItemService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "权限-停用")
    @PreAuthorize("hasPermission(null,'system:permissionItem:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        permissionItemService.disable(id);
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
    @PreAuthorize("hasPermission(null,'system:permissionItem:query')")
    public ResponseEntity<Result> tree() {
        QueryWrapper<PermissionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PermissionItem::getStatus, StatusEnum.NORMAL.toString());
        // 附加按照排序号排序
        queryWrapper.orderByAsc(TableFieldConstant.DEFAULT_SORT_FILED);
        List<PermissionItem> list = permissionItemService.list(queryWrapper);
        // 转化成树结构数据
        List<TreeVO> treeList = list.stream().map(e -> convert2TreeVO(e)).collect(Collectors.toList());
        List<TreeVO> tree = TreeUtil.buildTree(treeList, TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID);
        return ResultUtil.success(tree);
    }

    /**
     * 转换为树视图对象
     */
    private TreeVO convert2TreeVO(PermissionItem entity) {
        TreeVO tree = new TreeVO();
        tree.setId(entity.getId());
        tree.setParentId(entity.getPermissionItem());
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
    private PermissionItemVO convert2VO(PermissionItem entity) {
        PermissionItemVO vo = mapperFacade.map(entity, PermissionItemVO.class);
        vo.setTypeName(dictionaryUtil.getNameByCode("PermissionType", entity.getType()));
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<PermissionItemVO> convert2VO(List<PermissionItem> entityList) {
        List<PermissionItemVO> voList = new ArrayList<>(entityList.size());

        // 获取 上级 集合
        List<String> permissionItemList = entityList.stream().map(x -> x.getPermissionItem()).collect(Collectors.toList());
        Map<String, String> permissionItemNameMap = permissionItemService.getNameMap(permissionItemList);
        entityList.stream().forEach(x -> {
            PermissionItemVO vo = convert2VO(x);
            // 设置 上级
            vo.setPermissionItemName(permissionItemNameMap.get(x.getPermissionItem()));
            voList.add(vo);
        });
        return voList;
    }


    private PermissionItem convert2Entity(PermissionItemVO vo) {
        PermissionItem entity = mapperFacade.map(vo, PermissionItem.class);
        return entity;
    }

    // endregion
}