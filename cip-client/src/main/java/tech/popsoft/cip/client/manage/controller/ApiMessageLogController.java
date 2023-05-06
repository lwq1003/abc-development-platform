package tech.popsoft.cip.client.manage.controller;

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
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.popsoft.cip.client.manage.entity.ApiMessageLog;
import tech.popsoft.cip.client.manage.service.ApiMessageLogService;
import tech.popsoft.cip.client.manage.vo.ApiMessageLogVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息日志 前端控制器
 *
 * @author wqliu
 * @date 2021-08-21
 */
@RestController
@RequestMapping("/cip/apiMessageLog")
@Slf4j
public class ApiMessageLogController extends BaseController {
    @Autowired
    private ApiMessageLogService apiMessageLogService;

    // region 基本操作

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ApiMessageLog entity = apiMessageLogService.init();
        ApiMessageLogVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "消息日志-新增")
    @PreAuthorize("hasPermission(null,'cip:apiMessageLog:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ApiMessageLogVO vo) {
        ApiMessageLog entity = convert2Entity(vo);
        apiMessageLogService.add(entity);
        ApiMessageLogVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "消息日志-修改")
    @PreAuthorize("hasPermission(null,'cip:apiMessageLog:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ApiMessageLogVO vo) {
        ApiMessageLog entity = convert2Entity(vo);
        // 此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        // 个别需展示的该类信息的地方可以重新查询后返回
        apiMessageLogService.modify(entity);
        ApiMessageLogVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "消息日志-删除")
    @PreAuthorize("hasPermission(null,'cip:apiMessageLog:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        apiMessageLogService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "消息日志-分页")
    @PreAuthorize("hasPermission(null,'cip:apiMessageLog:query')")
    public ResponseEntity<Result> page(ApiMessageLogVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        // 构造分页对象
        IPage<ApiMessageLog> page = new Page<ApiMessageLog>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<ApiMessageLog> queryWrapper = QueryGenerator.generateQueryWrapper(ApiMessageLog.class, queryVO, sortInfo);

        // 查询数据
        apiMessageLogService.page(page, queryWrapper);
        // 转换vo
        IPage<ApiMessageLogVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ApiMessageLogVO> apiMessageLogVOList = new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            ApiMessageLogVO vo = convert2VO(page.getRecords().get(i));
            apiMessageLogVOList.add(vo);
        }
        pageVO.setRecords(apiMessageLogVOList);
        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "消息日志-列表")
    @PreAuthorize("hasPermission(null,'cip:apiMessageLog:query')")
    public ResponseEntity<Result> list(ApiMessageLogVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ApiMessageLog> queryWrapper = QueryGenerator.generateQueryWrapper(ApiMessageLog.class, queryVO, sortInfo);
        List<ApiMessageLog> list = apiMessageLogService.list(queryWrapper);
        // 转换vo
        List<ApiMessageLogVO> apiMessageLogVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ApiMessageLogVO vo = convert2VO(list.get(i));
            apiMessageLogVOList.add(vo);
        }
        return ResultUtil.success(apiMessageLogVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    @SystemLog(value = "消息日志-详情")
    @PreAuthorize("hasPermission(null,'cip:apiMessageLog:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ApiMessageLog entity = apiMessageLogService.query(id);
        ApiMessageLogVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    // endregion

    // region 扩展操作

    // endregion

    // region 辅助操作

    private ApiMessageLogVO convert2VO(ApiMessageLog entity) {
        ApiMessageLogVO vo = mapperFacade.map(entity, ApiMessageLogVO.class);
        String name = dictionaryUtil.getNameByCode("MessageResponseResult", entity.getResponseResult());
        vo.setResponseResultName(name);
        String statusName = dictionaryUtil.getNameByCode("MessageStatus", entity.getResponseResult());
        vo.setStatusName(statusName);

        return vo;
    }

    private ApiMessageLog convert2Entity(ApiMessageLogVO vo) {

        ApiMessageLog entity = mapperFacade.map(vo, ApiMessageLog.class);
        return entity;
    }

    // endregion
}
