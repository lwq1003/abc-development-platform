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
import tech.abc.platform.entityconfig.entity.ViewQueryCondition;
import tech.abc.platform.entityconfig.service.ViewQueryConditionService;
import tech.abc.platform.entityconfig.vo.SortedObject;
import tech.abc.platform.entityconfig.vo.ViewQueryConditionVO;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图查询条件 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@RestController
@RequestMapping("/entityconfig/viewQueryCondition")
@Slf4j
public class ViewQueryConditionController extends BaseController {
    @Autowired
    private ViewQueryConditionService viewQueryConditionService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ViewQueryCondition entity = viewQueryConditionService.init();
        ViewQueryConditionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "视图查询条件-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ViewQueryConditionVO vo) {
        ViewQueryCondition entity = convert2Entity(vo);
        viewQueryConditionService.add(entity);
        ViewQueryConditionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "视图查询条件-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ViewQueryConditionVO vo) {
        ViewQueryCondition entity = convert2Entity(vo);
        viewQueryConditionService.modify(entity);
        ViewQueryConditionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "视图查询条件-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        viewQueryConditionService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "视图查询条件-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:query')")
    public ResponseEntity<Result> page(ViewQueryConditionVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<ViewQueryCondition> page = new Page<ViewQueryCondition>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<ViewQueryCondition> queryWrapper = QueryGenerator.generateQueryWrapper(ViewQueryCondition.class, queryVO, sortInfo);

        // 查询数据
        viewQueryConditionService.page(page, queryWrapper);
        // 转换vo
        IPage<ViewQueryConditionVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ViewQueryConditionVO> viewQueryConditionVOList = convert2VO(page.getRecords());
        pageVO.setRecords(viewQueryConditionVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "视图查询条件-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:query')")
    public ResponseEntity<Result> list(ViewQueryConditionVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ViewQueryCondition> queryWrapper = QueryGenerator.generateQueryWrapper(ViewQueryCondition.class, queryVO, sortInfo);
        List<ViewQueryCondition> list = viewQueryConditionService.list(queryWrapper);
        // 转换vo
        List<ViewQueryConditionVO> viewQueryConditionVOList = convert2VO(list);
        return ResultUtil.success(viewQueryConditionVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "视图查询条件-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ViewQueryCondition entity = viewQueryConditionService.query(id);
        ViewQueryConditionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "视图查询条件-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        viewQueryConditionService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 列表
     */
    @GetMapping("/{viewId}/list")
    @SystemLog(value = "实体查询条件-列表")
    @AllowAll
    public ResponseEntity<Result> listByView(@PathVariable String viewId) {

        List<ViewQueryCondition> list = viewQueryConditionService.listByView(viewId);
        // 转换vo
        List<ViewQueryConditionVO> voList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ViewQueryConditionVO vo = convert2VO(list.get(i));
            voList.add(vo);
        }
        return ResultUtil.success(voList);
    }

    /**
     * 通过模型属性添加单个
     */
    @PostMapping("/{viewId}/addFromModelProperty/{code}")
    @SystemLog(value = "视图查询条件-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:add')")
    public ResponseEntity<Result> addFromModelProperty(@PathVariable String viewId, @PathVariable String code) {

        viewQueryConditionService.addFromModelProperty(viewId, code);

        return ResultUtil.success();
    }

    /**
     * 通过模型属性批量添加
     */
    @PostMapping("/{viewId}/addBatchFromModelProperty")
    @SystemLog(value = "视图查询条件-批量新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:add')")
    public ResponseEntity<Result> addBatchFromModelProperty(@PathVariable String viewId) {
        viewQueryConditionService.addBatchFromModelProperty(viewId);
        return ResultUtil.success();
    }


    /**
     * 更新次序
     */
    @PutMapping("/{viewId}/updateSort")
    @SystemLog(value = "视图查询条件-更新次序")
    @AllowAll
    public ResponseEntity<Result> updateSort(@PathVariable String viewId, @RequestBody List<SortedObject> list) {
        log.info(JSON.toJSONString(list));
        viewQueryConditionService.updateSort(viewId, list);

        return ResultUtil.success();
    }


    /**
     * 清空视图所有查询条件
     */
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{viewId}/clear")
    @SystemLog(value = "视图查询条件-清空")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:remove')")
    public ResponseEntity<Result> clear(@PathVariable String viewId) {
        log.info("视图标识：{}", viewId);
        viewQueryConditionService.clear(viewId);
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
    private ViewQueryConditionVO convert2VO(ViewQueryCondition entity) {
        ViewQueryConditionVO vo = mapperFacade.map(entity, ViewQueryConditionVO.class);
        vo.setDataTypeName(dictionaryUtil.getNameByCode("EntityModelPropertyType", entity.getDataType()));
        vo.setMatchRuleName(dictionaryUtil.getNameByCode("TextPatternRule", entity.getMatchRule()));
        vo.setReadonlyFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getReadonlyFlag()));
        vo.setShowFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getShowFlag()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<ViewQueryConditionVO> convert2VO(List<ViewQueryCondition> entityList) {
        List<ViewQueryConditionVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ViewQueryConditionVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private ViewQueryCondition convert2Entity(ViewQueryConditionVO vo) {
        ViewQueryCondition entity = mapperFacade.map(vo, ViewQueryCondition.class);
        return entity;
    }

    // endregion
}