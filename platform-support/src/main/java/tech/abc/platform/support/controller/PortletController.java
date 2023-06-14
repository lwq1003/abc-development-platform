package tech.abc.platform.support.controller;


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
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.support.entity.Portlet;
import tech.abc.platform.support.service.PortletService;
import tech.abc.platform.support.vo.PortletVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件 前端控制器类
 *
 * @author wqliu
 * @date 2023-06-01
 */
@RestController
@RequestMapping("/support/portlet")
@Slf4j
public class PortletController extends BaseController {
    @Autowired
    private PortletService portletService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Portlet entity = portletService.init();
        PortletVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "组件-新增")
    @PreAuthorize("hasPermission(null,'support:portlet:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody PortletVO vo) {
        Portlet entity = convert2Entity(vo);
        portletService.add(entity);
        PortletVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "组件-修改")
    @PreAuthorize("hasPermission(null,'support:portlet:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody PortletVO vo) {
        Portlet entity = convert2Entity(vo);
        portletService.modify(entity);
        PortletVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "组件-删除")
    @PreAuthorize("hasPermission(null,'support:portlet:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        portletService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "组件-分页")
    @PreAuthorize("hasPermission(null,'support:portlet:query')")
    public ResponseEntity<Result> page(PortletVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<Portlet> page = new Page<Portlet>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<Portlet> queryWrapper = QueryGenerator.generateQueryWrapper(Portlet.class, queryVO, sortInfo);

        // 查询数据
        portletService.page(page, queryWrapper);
        // 转换vo
        IPage<PortletVO> pageVO = mapperFacade.map(page, IPage.class);
        List<PortletVO> portletVOList = convert2VO(page.getRecords());
        pageVO.setRecords(portletVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "组件-列表")
    @PreAuthorize("hasPermission(null,'support:portlet:query')")
    public ResponseEntity<Result> list(PortletVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<Portlet> queryWrapper = QueryGenerator.generateQueryWrapper(Portlet.class, queryVO, sortInfo);
        List<Portlet> list = portletService.list(queryWrapper);
        // 转换vo
        List<PortletVO> portletVOList = convert2VO(list);
        return ResultUtil.success(portletVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "组件-详情")
    @PreAuthorize("hasPermission(null,'support:portlet:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Portlet entity = portletService.query(id);
        PortletVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "组件-复制新增")
    @PreAuthorize("hasPermission(null,'support:portlet:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        portletService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 启用
     */
    @PutMapping("/{id}/enable")
    @SystemLog(value = "组件-启用")
    @PreAuthorize("hasPermission(null,'support:portlet:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        portletService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "组件-停用")
    @PreAuthorize("hasPermission(null,'support:portlet:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        portletService.disable(id);
        return ResultUtil.success();
    }


    /**
     * 获取列表
     * 状态为正常且按照分类、排序升序排列，包含参数
     */
    @GetMapping("/getPortletList")
    @SystemLog(value = "组件-获取列表")
    @PreAuthorize("hasPermission(null,'support:portlet:query')")
    public ResponseEntity<Result> getPortletList() {
        // 构造查询条件
        QueryWrapper<Portlet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Portlet::getStatus, StatusEnum.NORMAL.name())
                .orderByAsc(Portlet::getCategory).orderByAsc(Portlet::getOrderNo);
        List<Portlet> list = portletService.getPortletList(queryWrapper);
        // 转换vo
        List<PortletVO> portletVOList = convert2VO(list);
        return ResultUtil.success(portletVOList);
    }

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private PortletVO convert2VO(Portlet entity) {
        PortletVO vo = mapperFacade.map(entity, PortletVO.class);
        vo.setCategoryName(dictionaryUtil.getNameByCode("PortletCategory", entity.getCategory()));
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<PortletVO> convert2VO(List<Portlet> entityList) {
        List<PortletVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            PortletVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private Portlet convert2Entity(PortletVO vo) {
        Portlet entity = mapperFacade.map(vo, Portlet.class);
        return entity;
    }

    // endregion
}