package tech.abc.platform.entityconfig.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.AllowAll;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.entityconfig.entity.ViewQueryResult;
import tech.abc.platform.entityconfig.service.ViewQueryResultService;
import tech.abc.platform.entityconfig.vo.SortedObject;
import tech.abc.platform.entityconfig.vo.ViewQueryResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图查询结果 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-16
 */
@RestController
@RequestMapping("/entityconfig/viewQueryResult")
@Slf4j
public class ViewQueryResultController extends BaseController {
    @Autowired
    private ViewQueryResultService viewQueryResultService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ViewQueryResult entity = viewQueryResultService.init();
        ViewQueryResultVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "视图查询结果-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryResult:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ViewQueryResultVO vo) {
        ViewQueryResult entity = convert2Entity(vo);
        viewQueryResultService.add(entity);
        ViewQueryResultVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "视图查询结果-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryResult:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ViewQueryResultVO vo) {
        ViewQueryResult entity = convert2Entity(vo);
        viewQueryResultService.modify(entity);
        ViewQueryResultVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "视图查询结果-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryResult:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        viewQueryResultService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "视图查询结果-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryResult:query')")
    public ResponseEntity<Result> page(ViewQueryResultVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<ViewQueryResult> page = new Page<ViewQueryResult>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<ViewQueryResult> queryWrapper = QueryGenerator.generateQueryWrapper(ViewQueryResult.class, queryVO, sortInfo);

        // 查询数据
        viewQueryResultService.page(page, queryWrapper);
        // 转换vo
        IPage<ViewQueryResultVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ViewQueryResultVO> viewQueryResultVOList = convert2VO(page.getRecords());
        pageVO.setRecords(viewQueryResultVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "视图查询结果-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryResult:query')")
    public ResponseEntity<Result> list(ViewQueryResultVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ViewQueryResult> queryWrapper = QueryGenerator.generateQueryWrapper(ViewQueryResult.class, queryVO, sortInfo);
        List<ViewQueryResult> list = viewQueryResultService.list(queryWrapper);
        // 转换vo
        List<ViewQueryResultVO> viewQueryResultVOList = convert2VO(list);
        return ResultUtil.success(viewQueryResultVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "视图查询结果-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryResult:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ViewQueryResult entity = viewQueryResultService.query(id);
        ViewQueryResultVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "视图查询结果-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryResult:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        viewQueryResultService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 列表
     * @param viewId
     * @return
     */
    @GetMapping("/{viewId}/list")
    @SystemLog(value = "视图查询结果-列表")
    @AllowAll
    public ResponseEntity<Result> listByView(@PathVariable String viewId) {

        List<ViewQueryResult> list = viewQueryResultService.listByView(viewId);
        // 转换vo
        List<ViewQueryResultVO> voList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ViewQueryResultVO vo = convert2VO(list.get(i));
            voList.add(vo);
        }
        return ResultUtil.success(voList);
    }

    /**
     * 新增
     */
    @PostMapping("/{viewId}/addFromModelProperty/{code}")
    @SystemLog(value = "视图查询结果-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryResult:add')")
    public ResponseEntity<Result> addFromModelProperty(@PathVariable String viewId, @PathVariable String code) {

        viewQueryResultService.addFromModelProperty(viewId, code);

        return ResultUtil.success();
    }

    /**
     * 通过模型属性批量添加
     */
    @PostMapping("/{viewId}/addBatchFromModelProperty")
    @SystemLog(value = "视图查询结果-批量新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:add')")
    public ResponseEntity<Result> addBatchFromModelProperty(@PathVariable String viewId) {
        viewQueryResultService.addBatchFromModelProperty(viewId);
        return ResultUtil.success();
    }


    /**
     * 更新次序
     */
    @PutMapping("/{viewId}/updateSort")
    @SystemLog(value = "视图查询结果-更新次序")
    @AllowAll
    public ResponseEntity<Result> updateSort(@PathVariable String viewId, @RequestBody List<SortedObject> list) {
        log.info(JSON.toJSONString(list));
        viewQueryResultService.updateSort(viewId, list);

        return ResultUtil.success();
    }


    /**
     * 清空视图所有查询结果属性
     */
    @DeleteMapping("/{viewId}/clear")
    @SystemLog(value = "视图查询结果-清空")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryResult:remove')")
    public ResponseEntity<Result> clear(@PathVariable String viewId) {
        viewQueryResultService.clear(viewId);
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
    private ViewQueryResultVO convert2VO(ViewQueryResult entity) {
        ViewQueryResultVO vo = mapperFacade.map(entity, ViewQueryResultVO.class);
        vo.setDataTypeName(dictionaryUtil.getNameByCode("EntityModelPropertyType", entity.getDataType()));
        vo.setSortableFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getSortableFlag()));
        vo.setShowOverflowTooltipFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getShowOverflowTooltipFlag()));
        vo.setShowFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getShowFlag()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<ViewQueryResultVO> convert2VO(List<ViewQueryResult> entityList) {
        List<ViewQueryResultVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ViewQueryResultVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private ViewQueryResult convert2Entity(ViewQueryResultVO vo) {
        ViewQueryResult entity = mapperFacade.map(vo, ViewQueryResult.class);
        return entity;
    }

    // endregion
}