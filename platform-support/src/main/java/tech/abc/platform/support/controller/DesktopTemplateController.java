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
import tech.abc.platform.support.entity.DesktopTemplate;
import tech.abc.platform.support.service.DesktopTemplateService;
import tech.abc.platform.support.vo.DesktopTemplateVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 桌面模板 前端控制器类
 *
 * @author wqliu
 * @date 2023-06-02
 */
@RestController
@RequestMapping("/support/desktopTemplate")
@Slf4j
public class DesktopTemplateController extends BaseController {
    @Autowired
    private DesktopTemplateService desktopTemplateService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        DesktopTemplate entity = desktopTemplateService.init();
        DesktopTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "桌面模板-新增")
    @PreAuthorize("hasPermission(null,'support:desktopTemplate:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody DesktopTemplateVO vo) {
        DesktopTemplate entity = convert2Entity(vo);
        desktopTemplateService.add(entity);
        DesktopTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "桌面模板-修改")
    @PreAuthorize("hasPermission(null,'support:desktopTemplate:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody DesktopTemplateVO vo) {
        DesktopTemplate entity = convert2Entity(vo);
        desktopTemplateService.modify(entity);
        DesktopTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "桌面模板-删除")
    @PreAuthorize("hasPermission(null,'support:desktopTemplate:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        desktopTemplateService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "桌面模板-分页")
    @PreAuthorize("hasPermission(null,'support:desktopTemplate:query')")
    public ResponseEntity<Result> page(DesktopTemplateVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<DesktopTemplate> page = new Page<DesktopTemplate>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<DesktopTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(DesktopTemplate.class, queryVO, sortInfo);

        // 查询数据
        desktopTemplateService.page(page, queryWrapper);
        // 转换vo
        IPage<DesktopTemplateVO> pageVO = mapperFacade.map(page, IPage.class);
        List<DesktopTemplateVO> desktopTemplateVOList = convert2VO(page.getRecords());
        pageVO.setRecords(desktopTemplateVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "桌面模板-列表")
    @PreAuthorize("hasPermission(null,'support:desktopTemplate:query')")
    public ResponseEntity<Result> list(DesktopTemplateVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<DesktopTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(DesktopTemplate.class, queryVO, sortInfo);
        List<DesktopTemplate> list = desktopTemplateService.list(queryWrapper);
        // 转换vo
        List<DesktopTemplateVO> desktopTemplateVOList = convert2VO(list);
        return ResultUtil.success(desktopTemplateVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "桌面模板-详情")
    @PreAuthorize("hasPermission(null,'support:desktopTemplate:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        DesktopTemplate entity = desktopTemplateService.query(id);
        DesktopTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "桌面模板-复制新增")
    @PreAuthorize("hasPermission(null,'support:desktopTemplate:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        desktopTemplateService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 启用
     */
    @PutMapping("/{id}/enable")
    @SystemLog(value = "组件-启用")
    @PreAuthorize("hasPermission(null,'support:desktopTemplate:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        desktopTemplateService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "组件-停用")
    @PreAuthorize("hasPermission(null,'support:desktopTemplate:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        desktopTemplateService.disable(id);
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
    private DesktopTemplateVO convert2VO(DesktopTemplate entity) {
        DesktopTemplateVO vo = mapperFacade.map(entity, DesktopTemplateVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<DesktopTemplateVO> convert2VO(List<DesktopTemplate> entityList) {
        List<DesktopTemplateVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            DesktopTemplateVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private DesktopTemplate convert2Entity(DesktopTemplateVO vo) {
        DesktopTemplate entity = mapperFacade.map(vo, DesktopTemplate.class);
        return entity;
    }

    // endregion
}