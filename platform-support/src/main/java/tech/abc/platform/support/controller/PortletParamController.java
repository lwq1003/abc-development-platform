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
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.support.entity.PortletParam;
import tech.abc.platform.support.service.PortletParamService;
import tech.abc.platform.support.service.PortletService;
import tech.abc.platform.support.vo.PortletParamVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 组件参数 前端控制器类
 *
 * @author wqliu
 * @date 2023-06-02
 */
@RestController
@RequestMapping("/support/portletParam")
@Slf4j
public class PortletParamController extends BaseController {
    @Autowired
    private PortletParamService portletParamService;
    @Autowired
    private PortletService portletService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        PortletParam entity = portletParamService.init();
        PortletParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "组件参数-新增")
    @PreAuthorize("hasPermission(null,'support:portletParam:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody PortletParamVO vo) {
        PortletParam entity = convert2Entity(vo);
        portletParamService.add(entity);
        PortletParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "组件参数-修改")
    @PreAuthorize("hasPermission(null,'support:portletParam:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody PortletParamVO vo) {
        PortletParam entity = convert2Entity(vo);
        portletParamService.modify(entity);
        PortletParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "组件参数-删除")
    @PreAuthorize("hasPermission(null,'support:portletParam:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        portletParamService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "组件参数-分页")
    @PreAuthorize("hasPermission(null,'support:portletParam:query')")
    public ResponseEntity<Result> page(PortletParamVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<PortletParam> page = new Page<PortletParam>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<PortletParam> queryWrapper = QueryGenerator.generateQueryWrapper(PortletParam.class, queryVO, sortInfo);

        // 查询数据
        portletParamService.page(page, queryWrapper);
        // 转换vo
        IPage<PortletParamVO> pageVO = mapperFacade.map(page, IPage.class);
        List<PortletParamVO> portletParamVOList = convert2VO(page.getRecords());
        pageVO.setRecords(portletParamVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "组件参数-列表")
    @PreAuthorize("hasPermission(null,'support:portletParam:query')")
    public ResponseEntity<Result> list(PortletParamVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<PortletParam> queryWrapper = QueryGenerator.generateQueryWrapper(PortletParam.class, queryVO, sortInfo);
        List<PortletParam> list = portletParamService.list(queryWrapper);
        // 转换vo
        List<PortletParamVO> portletParamVOList = convert2VO(list);
        return ResultUtil.success(portletParamVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "组件参数-详情")
    @PreAuthorize("hasPermission(null,'support:portletParam:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        PortletParam entity = portletParamService.query(id);
        PortletParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "组件参数-复制新增")
    @PreAuthorize("hasPermission(null,'support:portletParam:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        portletParamService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private PortletParamVO convert2VO(PortletParam entity) {
        PortletParamVO vo = mapperFacade.map(entity, PortletParamVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<PortletParamVO> convert2VO(List<PortletParam> entityList) {
        List<PortletParamVO> voList = new ArrayList<>(entityList.size());

        // 获取 组件 集合
        List<String> portletList = entityList.stream().map(x -> x.getPortlet()).collect(Collectors.toList());
        Map<String, String> portletNameMap = portletService.getNameMap(portletList);
        entityList.stream().forEach(x -> {
            PortletParamVO vo = convert2VO(x);
            // 设置 组件
            vo.setPortletName(portletNameMap.get(x.getPortlet()));
            voList.add(vo);
        });
        return voList;
    }


    private PortletParam convert2Entity(PortletParamVO vo) {
        PortletParam entity = mapperFacade.map(vo, PortletParam.class);
        return entity;
    }

    // endregion
}