package tech.abc.platform.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.constant.TableFieldConstant;
import tech.abc.platform.common.constant.TreeDefaultConstant;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.utils.TreeUtil;
import tech.abc.platform.common.vo.*;
import tech.abc.platform.system.entity.DictionaryItem;
import tech.abc.platform.system.entity.DictionaryType;
import tech.abc.platform.system.service.DictionaryTypeService;
import tech.abc.platform.system.vo.DictionaryTypeVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典类型 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-26
 */
@RestController
@RequestMapping("/system/dictionaryType")
@Slf4j
public class DictionaryTypeController extends BaseController {
    @Autowired
    private DictionaryTypeService dictionaryTypeService;


    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        DictionaryType entity = dictionaryTypeService.init();
        DictionaryTypeVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "字典类型-新增")
    @PreAuthorize("hasPermission(null,'system:dictionaryType:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody DictionaryTypeVO vo) {
        DictionaryType entity = convert2Entity(vo);
        dictionaryTypeService.add(entity);
        DictionaryTypeVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "字典类型-修改")
    @PreAuthorize("hasPermission(null,'system:dictionaryType:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody DictionaryTypeVO vo) {
        DictionaryType entity = convert2Entity(vo);
        dictionaryTypeService.modify(entity);
        DictionaryTypeVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "字典类型-删除")
    @PreAuthorize("hasPermission(null,'system:dictionaryType:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        dictionaryTypeService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "字典类型-分页")
    @PreAuthorize("hasPermission(null,'system:dictionaryType:query')")
    public ResponseEntity<Result> page(DictionaryTypeVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<DictionaryType> page = new Page<DictionaryType>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setDictionaryType(null);
        }

        // 构造查询条件
        QueryWrapper<DictionaryType> queryWrapper = QueryGenerator.generateQueryWrapper(DictionaryType.class, queryVO, sortInfo);

        // 查询数据
        dictionaryTypeService.page(page, queryWrapper);
        // 转换vo
        IPage<DictionaryTypeVO> pageVO = mapperFacade.map(page, IPage.class);
        List<DictionaryTypeVO> dictionaryTypeVOList = convert2VO(page.getRecords());
        pageVO.setRecords(dictionaryTypeVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "字典类型-列表")
    @PreAuthorize("hasPermission(null,'system:dictionaryType:query')")
    public ResponseEntity<Result> list(DictionaryTypeVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<DictionaryType> queryWrapper = QueryGenerator.generateQueryWrapper(DictionaryType.class, queryVO, sortInfo);
        List<DictionaryType> list = dictionaryTypeService.list(queryWrapper);
        // 转换vo
        List<DictionaryTypeVO> dictionaryTypeVOList = convert2VO(list);
        return ResultUtil.success(dictionaryTypeVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "字典类型-详情")
    @PreAuthorize("hasPermission(null,'system:dictionaryType:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        DictionaryType entity = dictionaryTypeService.query(id);
        DictionaryTypeVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "字典类型-复制新增")
    @PreAuthorize("hasPermission(null,'system:dictionaryType:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        dictionaryTypeService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作


    /**
     * 通过编码获取单条数据
     *
     * @param code 代码
     * @return {@link ResponseEntity}<{@link Result}>
     */
    @GetMapping("/getByCode/{code}")
    @SystemLog(value = "字典类型-详情")
    @PreAuthorize("hasPermission(null,'system:dictionaryType:query')")
    public ResponseEntity<Result> getByCode(@PathVariable String code) {
        DictionaryType entity = dictionaryTypeService.getByCode(code);
        DictionaryTypeVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 获取指定字典编码下所有字典项列表
     */
    @GetMapping("/getItem")
    public ResponseEntity<Result> getItem(String code) {
        List<DictionaryItem> dictionaryItemList = dictionaryTypeService.getItem(code);

        // 转换为列表项 视图
        List<ListItemVO> listItemVOList = new ArrayList<>();
        for (int i = 0; i < dictionaryItemList.size(); i++) {
            ListItemVO vo = new ListItemVO();
            DictionaryItem dictionaryItem = dictionaryItemList.get(i);
            vo.setValue(dictionaryItem.getId());
            vo.setLabel(dictionaryItem.getName());
            vo.setCode(dictionaryItem.getCode());
            listItemVOList.add(vo);
        }
        return ResultUtil.success(listItemVOList);
    }

    // endregion

    // region 树操作

    /**
     * 获取树数据
     *
     * @return
     */
    @GetMapping("/tree")
    @PreAuthorize("hasPermission(null,'system:dictionaryType:query')")
    public ResponseEntity<Result> tree() {
        QueryWrapper<DictionaryType> queryWrapper = new QueryWrapper<>();
        // 附加按照排序号排序
        queryWrapper.orderByAsc(TableFieldConstant.DEFAULT_SORT_FILED);
        List<DictionaryType> list = dictionaryTypeService.list(queryWrapper);
        // 转化成树结构数据
        List<TreeVO> treeList = list.stream().map(e -> convert2TreeVO(e)).collect(Collectors.toList());
        List<TreeVO> tree = TreeUtil.buildTree(treeList, TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID);
        return ResultUtil.success(tree);
    }

    /**
     * 转换为树视图对象
     */
    private TreeVO convert2TreeVO(DictionaryType entity) {
        TreeVO tree = new TreeVO();
        tree.setId(entity.getId());
        tree.setParentId(entity.getDictionaryType());
        tree.setLabel(entity.getName());
        // 字典相比普通树多了编码属性处理
        tree.setCode(entity.getCode());
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
    private DictionaryTypeVO convert2VO(DictionaryType entity) {
        DictionaryTypeVO vo = mapperFacade.map(entity, DictionaryTypeVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<DictionaryTypeVO> convert2VO(List<DictionaryType> entityList) {
        List<DictionaryTypeVO> voList = new ArrayList<>(entityList.size());

        // 获取 上级 集合
        List<String> dataDictionaryList = entityList.stream().map(x -> x.getDictionaryType()).collect(Collectors.toList());
        Map<String, String> dataDictionaryNameMap = dictionaryTypeService.getNameMap(dataDictionaryList);
        entityList.stream().forEach(x -> {
            DictionaryTypeVO vo = convert2VO(x);
            // 设置 上级
            vo.setDictionaryTypeName(dataDictionaryNameMap.get(x.getDictionaryType()));
            voList.add(vo);
        });
        return voList;
    }


    private DictionaryType convert2Entity(DictionaryTypeVO vo) {
        DictionaryType entity = mapperFacade.map(vo, DictionaryType.class);
        return entity;
    }

    // endregion
}