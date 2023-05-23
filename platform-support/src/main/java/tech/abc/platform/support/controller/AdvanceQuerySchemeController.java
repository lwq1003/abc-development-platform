package tech.abc.platform.support.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.support.entity.AdvanceQueryScheme;
import tech.abc.platform.support.service.AdvanceQuerySchemeService;
import tech.abc.platform.support.vo.AdvanceQuerySchemeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 高级查询方案 前端控制器
 *
 * @author wqliu
 * *
 */
@RestController
@RequestMapping("/support/advanceQueryScheme")
@Slf4j
public class AdvanceQuerySchemeController extends BaseController {
    @Autowired
    private AdvanceQuerySchemeService advanceQuerySchemeService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        AdvanceQueryScheme entity = advanceQuerySchemeService.init();
        AdvanceQuerySchemeVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "高级查询方案-新增")
    @PreAuthorize("hasPermission(null,'support:advanceQueryScheme:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody AdvanceQuerySchemeVO vo) {
        AdvanceQueryScheme entity = convert2Entity(vo);
        advanceQuerySchemeService.add(entity);
        AdvanceQuerySchemeVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "高级查询方案-修改")
    @PreAuthorize("hasPermission(null,'support:advanceQueryScheme:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody AdvanceQuerySchemeVO vo) {
        AdvanceQueryScheme entity = convert2Entity(vo);
        //此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        //个别需展示的该类信息的地方可以重新查询后返回
        advanceQuerySchemeService.modify(entity);
        AdvanceQuerySchemeVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "高级查询方案-删除")
    @PreAuthorize("hasPermission(null,'support:advanceQueryScheme:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        advanceQuerySchemeService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "高级查询方案-分页")
    @PreAuthorize("hasPermission(null,'support:advanceQueryScheme:query')")
    public ResponseEntity<Result> page(AdvanceQuerySchemeVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<AdvanceQueryScheme> page = new Page<AdvanceQueryScheme>(pageInfo.getPageNum(), pageInfo.getPageSize());

        //附加当前用户标识
        queryVO.setUserId(UserUtil.getId());

        //构造查询条件
        QueryWrapper<AdvanceQueryScheme> queryWrapper = QueryGenerator.generateQueryWrapper(AdvanceQueryScheme.class, queryVO, sortInfo);

        //查询数据
        advanceQuerySchemeService.page(page, queryWrapper);
        //转换vo
        IPage<AdvanceQuerySchemeVO> pageVO = mapperFacade.map(page, IPage.class);
        List<AdvanceQuerySchemeVO> advanceQuerySchemeVOList = new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            AdvanceQuerySchemeVO vo = convert2VO(page.getRecords().get(i));
            advanceQuerySchemeVOList.add(vo);
        }
        pageVO.setRecords(advanceQuerySchemeVOList);
        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "高级查询方案-列表")
    @PreAuthorize("hasPermission(null,'support:advanceQueryScheme:query')")
    public ResponseEntity<Result> list(AdvanceQuerySchemeVO queryVO, SortInfo sortInfo) {
        //附加当前用户标识
        queryVO.setUserId(UserUtil.getId());
        //构造查询条件
        QueryWrapper<AdvanceQueryScheme> queryWrapper = QueryGenerator.generateQueryWrapper(AdvanceQueryScheme.class, queryVO, sortInfo);
        List<AdvanceQueryScheme> list = advanceQuerySchemeService.list(queryWrapper);
        //转换vo
        List<AdvanceQuerySchemeVO> advanceQuerySchemeVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AdvanceQuerySchemeVO vo = convert2VO(list.get(i));
            advanceQuerySchemeVOList.add(vo);
        }
        return ResultUtil.success(advanceQuerySchemeVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    @SystemLog(value = "高级查询方案-详情")
    @PreAuthorize("hasPermission(null,'support:advanceQueryScheme:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        AdvanceQueryScheme entity = advanceQuerySchemeService.query(id);
        AdvanceQuerySchemeVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    private AdvanceQuerySchemeVO convert2VO(AdvanceQueryScheme entity) {
        AdvanceQuerySchemeVO vo = mapperFacade.map(entity, AdvanceQuerySchemeVO.class);
        return vo;
    }

    private AdvanceQueryScheme convert2Entity(AdvanceQuerySchemeVO vo) {

        AdvanceQueryScheme entity = mapperFacade.map(vo, AdvanceQueryScheme.class);
        return entity;
    }

}
