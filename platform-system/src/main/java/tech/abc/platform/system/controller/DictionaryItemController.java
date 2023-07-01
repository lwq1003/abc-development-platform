package tech.abc.platform.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.system.entity.DictionaryItem;
import tech.abc.platform.system.service.DictionaryItemService;
import tech.abc.platform.system.service.DictionaryTypeService;
import tech.abc.platform.system.vo.DictionaryItemVO;
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
 * 字典项 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-26
 */
@RestController
@RequestMapping("/system/dictionaryItem")
@Slf4j
public class DictionaryItemController extends BaseController {
    @Autowired
    private DictionaryItemService dictionaryItemService;
    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        DictionaryItem entity = dictionaryItemService.init();
        DictionaryItemVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "字典项-新增")
    @PreAuthorize("hasPermission(null,'system:dictionaryItem:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody DictionaryItemVO vo) {
        DictionaryItem entity = convert2Entity(vo);
        dictionaryItemService.add(entity);
        DictionaryItemVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "字典项-修改")
    @PreAuthorize("hasPermission(null,'system:dictionaryItem:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody DictionaryItemVO vo) {
        DictionaryItem entity = convert2Entity(vo);
        dictionaryItemService.modify(entity);
        DictionaryItemVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "字典项-删除")
    @PreAuthorize("hasPermission(null,'system:dictionaryItem:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        dictionaryItemService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "字典项-分页")
    @PreAuthorize("hasPermission(null,'system:dictionaryItem:query')")
    public ResponseEntity<Result> page(DictionaryItemVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<DictionaryItem> page = new Page<DictionaryItem>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setDictionaryType(null);
        }

        // 构造查询条件
        QueryWrapper<DictionaryItem> queryWrapper = QueryGenerator.generateQueryWrapper(DictionaryItem.class, queryVO, sortInfo);

        // 查询数据
        dictionaryItemService.page(page, queryWrapper);
        // 转换vo
        IPage<DictionaryItemVO> pageVO = mapperFacade.map(page, IPage.class);
        List<DictionaryItemVO> dictionaryItemVOList = convert2VO(page.getRecords());
        pageVO.setRecords(dictionaryItemVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "字典项-列表")
    @PreAuthorize("hasPermission(null,'system:dictionaryItem:query')")
    public ResponseEntity<Result> list(DictionaryItemVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<DictionaryItem> queryWrapper = QueryGenerator.generateQueryWrapper(DictionaryItem.class, queryVO, sortInfo);
        List<DictionaryItem> list = dictionaryItemService.list(queryWrapper);
        // 转换vo
        List<DictionaryItemVO> dictionaryItemVOList = convert2VO(list);
        return ResultUtil.success(dictionaryItemVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "字典项-详情")
    @PreAuthorize("hasPermission(null,'system:dictionaryItem:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        DictionaryItem entity = dictionaryItemService.query(id);
        DictionaryItemVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "字典项-复制新增")
    @PreAuthorize("hasPermission(null,'system:dictionaryItem:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        dictionaryItemService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 启用
     */
    @PutMapping("/{id}/enable")
    @SystemLog(value = "字典项-启用")
    @PreAuthorize("hasPermission(null,'system:dictionaryItem:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        dictionaryItemService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "字典项-停用")
    @PreAuthorize("hasPermission(null,'system:dictionaryItem:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        dictionaryItemService.disable(id);
        return ResultUtil.success();
    }


    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private DictionaryItemVO convert2VO(DictionaryItem entity) {
        DictionaryItemVO vo = mapperFacade.map(entity, DictionaryItemVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<DictionaryItemVO> convert2VO(List<DictionaryItem> entityList) {
        List<DictionaryItemVO> voList = new ArrayList<>(entityList.size());

        // 获取 字典类型 集合
        List<String> dictionaryTypeList = entityList.stream().map(x -> x.getDictionaryType()).collect(Collectors.toList());
        Map<String, String> dictionaryTypeNameMap = dictionaryTypeService.getNameMap(dictionaryTypeList);
        entityList.stream().forEach(x -> {
            DictionaryItemVO vo = convert2VO(x);
            // 设置 字典类型
            vo.setDictionaryTypeName(dictionaryTypeNameMap.get(x.getDictionaryType()));
            voList.add(vo);
        });
        return voList;
    }


    private DictionaryItem convert2Entity(DictionaryItemVO vo) {
        DictionaryItem entity = mapperFacade.map(vo, DictionaryItem.class);
        return entity;
    }

    // endregion
}