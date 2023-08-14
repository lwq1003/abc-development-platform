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
import tech.abc.platform.entityconfig.entity.ViewProperty;
import tech.abc.platform.entityconfig.service.ViewPropertyService;
import tech.abc.platform.entityconfig.vo.SortedObject;
import tech.abc.platform.entityconfig.vo.ViewPropertyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图属性 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@RestController
@RequestMapping("/entityconfig/viewProperty")
@Slf4j
public class ViewPropertyController extends BaseController {
    @Autowired
    private ViewPropertyService viewPropertyService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ViewProperty entity = viewPropertyService.init();
        ViewPropertyVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "视图属性-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewProperty:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ViewPropertyVO vo) {
        ViewProperty entity = convert2Entity(vo);
        viewPropertyService.add(entity);
        ViewPropertyVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "视图属性-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:viewProperty:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ViewPropertyVO vo) {
        ViewProperty entity = convert2Entity(vo);
        viewPropertyService.modify(entity);
        ViewPropertyVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "视图属性-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:viewProperty:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        viewPropertyService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "视图属性-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:viewProperty:query')")
    public ResponseEntity<Result> page(ViewPropertyVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<ViewProperty> page = new Page<ViewProperty>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<ViewProperty> queryWrapper = QueryGenerator.generateQueryWrapper(ViewProperty.class, queryVO, sortInfo);

        // 查询数据
        viewPropertyService.page(page, queryWrapper);
        // 转换vo
        IPage<ViewPropertyVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ViewPropertyVO> viewPropertyVOList = convert2VO(page.getRecords());
        pageVO.setRecords(viewPropertyVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "视图属性-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:viewProperty:query')")
    public ResponseEntity<Result> list(ViewPropertyVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ViewProperty> queryWrapper = QueryGenerator.generateQueryWrapper(ViewProperty.class, queryVO, sortInfo);
        List<ViewProperty> list = viewPropertyService.list(queryWrapper);
        // 转换vo
        List<ViewPropertyVO> viewPropertyVOList = convert2VO(list);
        return ResultUtil.success(viewPropertyVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "视图属性-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:viewProperty:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ViewProperty entity = viewPropertyService.query(id);
        ViewPropertyVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "视图属性-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewProperty:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        viewPropertyService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 列表
     */
    @GetMapping("/{viewId}/list")
    @SystemLog(value = "实体属性-列表")
    @AllowAll
    public ResponseEntity<Result> listByView(@PathVariable String viewId) {

        List<ViewProperty> list = viewPropertyService.listByView(viewId);
        // 转换vo
        List<ViewPropertyVO> voList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ViewPropertyVO vo = convert2VO(list.get(i));
            voList.add(vo);
        }
        return ResultUtil.success(voList);
    }

    /**
     * 通过模型属性添加单个
     */
    @PostMapping("/{viewId}/addFromModelProperty/{code}")
    @SystemLog(value = "视图属性-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:add')")
    public ResponseEntity<Result> addFromModelProperty(@PathVariable String viewId, @PathVariable String code) {

        viewPropertyService.addFromModelProperty(viewId, code);

        return ResultUtil.success();
    }

    /**
     * 通过模型属性批量添加
     */
    @PostMapping("/{viewId}/addBatchFromModelProperty")
    @SystemLog(value = "视图属性-批量新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:add')")
    public ResponseEntity<Result> addBatchFromModelProperty(@PathVariable String viewId) {
        viewPropertyService.addBatchFromModelProperty(viewId);
        return ResultUtil.success();
    }


    /**
     * 更新次序
     */
    @PutMapping("/{viewId}/updateSort")
    @SystemLog(value = "视图属性-更新次序")
    @AllowAll
    public ResponseEntity<Result> updateSort(@PathVariable String viewId, @RequestBody List<SortedObject> list) {
        log.info(JSON.toJSONString(list));
        viewPropertyService.updateSort(viewId, list);

        return ResultUtil.success();
    }


    /**
     * 清空视图所有属性
     */
    @DeleteMapping("/{viewId}/clear")
    @SystemLog(value = "视图属性-清空")
    @PreAuthorize("hasPermission(null,'entityconfig:viewQueryCondition:remove')")
    public ResponseEntity<Result> clear(@PathVariable String viewId) {
        viewPropertyService.clear(viewId);
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
    private ViewPropertyVO convert2VO(ViewProperty entity) {
        ViewPropertyVO vo = mapperFacade.map(entity, ViewPropertyVO.class);
        vo.setDataTypeName(dictionaryUtil.getNameByCode("EntityModelPropertyType", entity.getDataType()));
        vo.setReadonlyFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getReadonlyFlag()));
        vo.setShowFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getShowFlag()));
        vo.setRequireFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getRequireFlag()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<ViewPropertyVO> convert2VO(List<ViewProperty> entityList) {
        List<ViewPropertyVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ViewPropertyVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private ViewProperty convert2Entity(ViewPropertyVO vo) {
        ViewProperty entity = mapperFacade.map(vo, ViewProperty.class);
        return entity;
    }

    // endregion
}