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
import tech.abc.platform.system.entity.Organization;
import tech.abc.platform.system.service.OrganizationService;
import tech.abc.platform.system.vo.OrganizationVO;
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
 * 组织机构 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-20
 */
@RestController
@RequestMapping("/system/organization")
@Slf4j
public class OrganizationController extends BaseController {
    @Autowired
    private OrganizationService organizationService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Organization entity = organizationService.init();
        OrganizationVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "组织机构-新增")
    @PreAuthorize("hasPermission(null,'system:organization:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody OrganizationVO vo) {
        Organization entity = convert2Entity(vo);
        organizationService.add(entity);
        OrganizationVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "组织机构-修改")
    @PreAuthorize("hasPermission(null,'system:organization:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody OrganizationVO vo) {
        Organization entity = convert2Entity(vo);
        organizationService.modify(entity);
        OrganizationVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "组织机构-删除")
    @PreAuthorize("hasPermission(null,'system:organization:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        organizationService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "组织机构-分页")
    @PreAuthorize("hasPermission(null,'system:organization:query')")
    public ResponseEntity<Result> page(OrganizationVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<Organization> page = new Page<Organization>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setOrganization(null);
        }

        // 构造查询条件
        QueryWrapper<Organization> queryWrapper = QueryGenerator.generateQueryWrapper(Organization.class, queryVO, sortInfo);

        // 查询数据
        organizationService.page(page, queryWrapper);
        // 转换vo
        IPage<OrganizationVO> pageVO = mapperFacade.map(page, IPage.class);
        List<OrganizationVO> organizationVOList = convert2VO(page.getRecords());
        pageVO.setRecords(organizationVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "组织机构-列表")
    @PreAuthorize("hasPermission(null,'system:organization:query')")
    public ResponseEntity<Result> list(OrganizationVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<Organization> queryWrapper = QueryGenerator.generateQueryWrapper(Organization.class, queryVO, sortInfo);
        List<Organization> list = organizationService.list(queryWrapper);
        // 转换vo
        List<OrganizationVO> organizationVOList = convert2VO(list);
        return ResultUtil.success(organizationVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "组织机构-详情")
    @PreAuthorize("hasPermission(null,'system:organization:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Organization entity = organizationService.query(id);
        OrganizationVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "组织机构-复制新增")
    @PreAuthorize("hasPermission(null,'system:organization:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        organizationService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 启用
     */

    @PutMapping("/{id}/enable")
    @SystemLog(value = "组织机构-启用")
    @PreAuthorize("hasPermission(null,'system:organization:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        organizationService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */

    @PutMapping("/{id}/disable")
    @SystemLog(value = "组织机构-停用")
    @PreAuthorize("hasPermission(null,'system:organization:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        organizationService.disable(id);
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
    @PreAuthorize("hasPermission(null,'system:organization:query')")
    public ResponseEntity<Result> tree() {
        QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Organization::getStatus, StatusEnum.NORMAL.toString());
        // 附加按照排序号排序
        queryWrapper.orderByAsc(TableFieldConstant.DEFAULT_SORT_FILED);
        List<Organization> list = organizationService.list(queryWrapper);
        // 转化成树结构数据
        List<TreeVO> treeList = list.stream().map(e -> convert2TreeVO(e)).collect(Collectors.toList());
        List<TreeVO> tree = TreeUtil.buildTree(treeList, TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID);
        return ResultUtil.success(tree);
    }

    /**
     * 转换为树视图对象
     */
    private TreeVO convert2TreeVO(Organization entity) {
        TreeVO tree = new TreeVO();
        tree.setId(entity.getId());
        tree.setParentId(entity.getOrganization());
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
    private OrganizationVO convert2VO(Organization entity) {
        OrganizationVO vo = mapperFacade.map(entity, OrganizationVO.class);
        vo.setTypeName(dictionaryUtil.getNameByCode("OrganizationType", entity.getType()));
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<OrganizationVO> convert2VO(List<Organization> entityList) {
        List<OrganizationVO> voList = new ArrayList<>(entityList.size());

        // 获取 上级组织 集合
        List<String> organizationList = entityList.stream().map(x -> x.getOrganization()).collect(Collectors.toList());
        Map<String, String> organizationNameMap = organizationService.getNameMap(organizationList);
        entityList.stream().forEach(x -> {
            OrganizationVO vo = convert2VO(x);
            // 设置 上级组织
            vo.setOrganizationName(organizationNameMap.get(x.getOrganization()));
            voList.add(vo);
        });
        return voList;
    }


    private Organization convert2Entity(OrganizationVO vo) {
        Organization entity = mapperFacade.map(vo, Organization.class);
        return entity;
    }

    // endregion
}