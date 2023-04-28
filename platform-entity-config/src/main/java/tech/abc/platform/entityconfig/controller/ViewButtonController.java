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
import tech.abc.platform.entityconfig.entity.ViewButton;
import tech.abc.platform.entityconfig.service.ViewButtonService;
import tech.abc.platform.entityconfig.vo.SortedObject;
import tech.abc.platform.entityconfig.vo.ViewButtonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图按钮 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-16
 */
@RestController
@RequestMapping("/entityconfig/viewButton")
@Slf4j
public class ViewButtonController extends BaseController {
    @Autowired
    private ViewButtonService viewButtonService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ViewButton entity = viewButtonService.init();
        ViewButtonVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "视图按钮-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButton:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ViewButtonVO vo) {
        ViewButton entity = convert2Entity(vo);
        viewButtonService.add(entity);
        ViewButtonVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "视图按钮-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButton:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ViewButtonVO vo) {
        ViewButton entity = convert2Entity(vo);
        viewButtonService.modify(entity);
        ViewButtonVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "视图按钮-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButton:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        viewButtonService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "视图按钮-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButton:query')")
    public ResponseEntity<Result> page(ViewButtonVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<ViewButton> page = new Page<ViewButton>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<ViewButton> queryWrapper = QueryGenerator.generateQueryWrapper(ViewButton.class, queryVO, sortInfo);

        // 查询数据
        viewButtonService.page(page, queryWrapper);
        // 转换vo
        IPage<ViewButtonVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ViewButtonVO> viewButtonVOList = convert2VO(page.getRecords());
        pageVO.setRecords(viewButtonVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "视图按钮-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButton:query')")
    public ResponseEntity<Result> list(ViewButtonVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ViewButton> queryWrapper = QueryGenerator.generateQueryWrapper(ViewButton.class, queryVO, sortInfo);
        List<ViewButton> list = viewButtonService.list(queryWrapper);
        // 转换vo
        List<ViewButtonVO> viewButtonVOList = convert2VO(list);
        return ResultUtil.success(viewButtonVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "视图按钮-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButton:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ViewButton entity = viewButtonService.query(id);
        ViewButtonVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "视图按钮-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButton:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        viewButtonService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    @GetMapping("/{viewId}/listByViewAndType")
    @SystemLog(value = "视图按钮-列表")
    @AllowAll
    public ResponseEntity<Result> listByViewAndType(@PathVariable String viewId, String buttonType) {

        List<ViewButton> list = viewButtonService.listByViewAndType(viewId, buttonType);
        // 转换vo
        List<ViewButtonVO> viewButtonVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ViewButtonVO vo = convert2VO(list.get(i));
            viewButtonVOList.add(vo);
        }
        return ResultUtil.success(viewButtonVOList);
    }


    /**
     * 清空视图所有查询条件
     */
    @DeleteMapping("/{viewId}/{buttonType}/clear")
    @SystemLog(value = "视图页面按钮-清空")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButton:remove')")
    public ResponseEntity<Result> clear(@PathVariable String viewId, @PathVariable String buttonType) {
        log.info(buttonType);
        viewButtonService.clear(viewId, buttonType);
        return ResultUtil.success();
    }

    /**
     * 更新视图按钮次序
     */
    @PutMapping("/{viewId}/updateButtonSort")
    @SystemLog(value = "视图按钮-更新视图按钮次序")
    @AllowAll
    public ResponseEntity<Result> updateButtonSort(@PathVariable String viewId, @RequestBody List<SortedObject> buttonList) {
        log.info(JSON.toJSONString(buttonList));
        viewButtonService.updateButtonSort(viewId, buttonList);

        return ResultUtil.success();
    }


    /**
     * 通过模板添加
     */
    @PostMapping("/{viewId}/{buttonType}/addFromTemplate")
    @SystemLog(value = "视图按钮-通过模板添加")
    @AllowAll
    public ResponseEntity<Result> addFromTemplate(@PathVariable String viewId, @PathVariable String buttonType, @RequestBody List<String> code) {

        viewButtonService.addFromTemplate(viewId, buttonType, code);

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
    private ViewButtonVO convert2VO(ViewButton entity) {
        ViewButtonVO vo = mapperFacade.map(entity, ViewButtonVO.class);
        vo.setButtonTypeName(dictionaryUtil.getNameByCode("ViewButtonType", entity.getButtonType()));
        vo.setConfirmFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getConfirmFlag()));
        vo.setPermissionFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getPermissionFlag()));
        vo.setMoreFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getMoreFlag()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<ViewButtonVO> convert2VO(List<ViewButton> entityList) {
        List<ViewButtonVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ViewButtonVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private ViewButton convert2Entity(ViewButtonVO vo) {
        ViewButton entity = mapperFacade.map(vo, ViewButton.class);
        return entity;
    }

    // endregion
}