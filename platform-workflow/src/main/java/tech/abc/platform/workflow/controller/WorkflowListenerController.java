package tech.abc.platform.workflow.controller;

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
import tech.abc.platform.workflow.entity.WorkflowListener;
import tech.abc.platform.workflow.service.WorkflowListenerService;
import tech.abc.platform.workflow.vo.WorkflowListenerVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程监听器 前端控制器
 * @author wqliu
 * @date 2020-07-13
 *
 */
@RestController
@RequestMapping("/workflow/workflowListener")
@Slf4j
public class WorkflowListenerController extends BaseController {
 @Autowired
 private WorkflowListenerService workflowListenerService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowListener entity=workflowListenerService.init();
        WorkflowListenerVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "流程监听器-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowListenerVO vo) {
        WorkflowListener entity=convert2Entity(vo);
        workflowListenerService.add(entity);
        WorkflowListenerVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "流程监听器-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowListenerVO vo)  {
        WorkflowListener entity=convert2Entity(vo);
        //此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        //个别需展示的该类信息的地方可以重新查询后返回
        workflowListenerService.modify(entity);
        WorkflowListenerVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "流程监听器-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowListenerService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "流程监听器-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:query')")
    public ResponseEntity<Result> page(WorkflowListenerVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<WorkflowListener> page = new Page<WorkflowListener>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<WorkflowListener> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowListener.class,queryVO,sortInfo);

        //查询数据
        workflowListenerService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowListenerVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowListenerVO>  workflowListenerVOList=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            WorkflowListenerVO vo = convert2VO(page.getRecords().get(i));
            workflowListenerVOList.add(vo);
        }
        pageVO.setRecords(workflowListenerVOList);        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "流程监听器-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:query')")
    public ResponseEntity<Result> list(WorkflowListenerVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowListener> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowListener.class, queryVO,sortInfo);
        List<WorkflowListener> list= workflowListenerService.list(queryWrapper);
        //转换vo
        List<WorkflowListenerVO> workflowListenerVOList = mapperFacade.mapAsList(list, WorkflowListenerVO.class);
        return ResultUtil.success(workflowListenerVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @GetMapping("/{id}")
    @SystemLog(value = "流程监听器-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowListener entity = workflowListenerService.query(id);
        WorkflowListenerVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    /**
     * 启用
     */
    @ApiOperation(value = "启用")
    @PutMapping("/{id}/enable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @SystemLog(value = "流程监听器-启用")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        workflowListenerService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @ApiOperation(value = "停用")
    @PutMapping("/{id}/disable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @SystemLog(value = "流程监听器-停用")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        workflowListenerService.disable(id);
        return ResultUtil.success();
    }




    private WorkflowListenerVO convert2VO(WorkflowListener entity){
        WorkflowListenerVO vo=mapperFacade.map(entity,WorkflowListenerVO.class);
        String statusName = dictionaryUtil.getNameByCode("Status", entity.getStatus());
        vo.setStatusName(statusName);
        String categoryName = dictionaryUtil.getNameByCode("WorkflowListenerCategory", entity.getCategory());
        vo.setCategoryName(categoryName);
//        String typeCode="";
//        if(vo.getCategory().equals(FlowListenerCategoryEnum.TASK.name())){
//            typeCode="TaskListenerEventCategory";
//
//        }else if(vo.getCategory().equals(FlowListenerCategoryEnum.TASK.name())) {
//            typeCode="ExecutionListenerEventCategory";
//        }
        String typeName = dictionaryUtil.getNameByCode("WorkflowListenerType", entity.getType());
        vo.setTypeName(typeName);
        return vo;
    }

    private WorkflowListener convert2Entity(WorkflowListenerVO vo){

        WorkflowListener entity=mapperFacade.map(vo,WorkflowListener.class);

        return entity;
    }

}
