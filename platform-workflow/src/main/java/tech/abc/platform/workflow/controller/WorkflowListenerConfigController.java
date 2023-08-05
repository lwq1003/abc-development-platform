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
import tech.abc.platform.workflow.entity.WorkflowListenerConfig;
import tech.abc.platform.workflow.enums.WorkflowListenerCategoryEnum;
import tech.abc.platform.workflow.service.WorkflowListenerConfigService;
import tech.abc.platform.workflow.vo.WorkflowListenerConfigVO;
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
 * 流程监听器配置 前端控制器
 * @author wqliu
 * @date 2020-10-08
 *
 */
@RestController
@RequestMapping("/workflow/workflowListenerConfig")
@Slf4j
public class WorkflowListenerConfigController extends BaseController {
 @Autowired
 private WorkflowListenerConfigService workflowListenerConfigService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowListenerConfig entity=workflowListenerConfigService.init();
        WorkflowListenerConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "流程监听器配置-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowListenerConfigVO vo) {
        WorkflowListenerConfig entity=convert2Entity(vo);
        workflowListenerConfigService.add(entity);
        WorkflowListenerConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "流程监听器配置-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowListenerConfigVO vo)  {
        WorkflowListenerConfig entity=convert2Entity(vo);
        //此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        //个别需展示的该类信息的地方可以重新查询后返回
        workflowListenerConfigService.modify(entity);
        WorkflowListenerConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "流程监听器配置-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowListenerConfigService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "流程监听器配置-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> page(WorkflowListenerConfigVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<WorkflowListenerConfig> page = new Page<WorkflowListenerConfig>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<WorkflowListenerConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowListenerConfig.class,queryVO,sortInfo);

        //查询数据
        workflowListenerConfigService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowListenerConfigVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowListenerConfigVO>  workflowListenerConfigVOList=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            WorkflowListenerConfigVO vo = convert2VO(page.getRecords().get(i));
            workflowListenerConfigVOList.add(vo);
        }
        pageVO.setRecords(workflowListenerConfigVOList);        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "流程监听器配置-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> list(WorkflowListenerConfigVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowListenerConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowListenerConfig.class, queryVO,sortInfo);
        List<WorkflowListenerConfig> list= workflowListenerConfigService.list(queryWrapper);
        //转换vo
        List<WorkflowListenerConfigVO>  workflowListenerConfigVOList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            WorkflowListenerConfigVO vo = convert2VO(list.get(i));
            workflowListenerConfigVOList.add(vo);
        }
        return ResultUtil.success(workflowListenerConfigVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @GetMapping("/{id}")
    @SystemLog(value = "流程监听器配置-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowListenerConfig entity = workflowListenerConfigService.query(id);
        WorkflowListenerConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }




    private WorkflowListenerConfigVO convert2VO(WorkflowListenerConfig entity){
        WorkflowListenerConfigVO vo=mapperFacade.map(entity,WorkflowListenerConfigVO.class);
        vo.setCategoryName(dictionaryUtil.getNameByCode("WorkflowListenerCategory", entity.getCategory()));
        vo.setListenerTypeName(dictionaryUtil.getNameByCode("WorkflowListenerType", entity.getListenerType()));
        String eventDictionaryCode="";
        if(entity.getCategory().equals(WorkflowListenerCategoryEnum.TASK.name())){
            eventDictionaryCode="TaskListenerEventCategory";
        }else if(entity.getCategory().equals(WorkflowListenerCategoryEnum.EXECUTION.name())) {
            eventDictionaryCode="ExecutionListenerEventCategory";
        }
        vo.setEventName(dictionaryUtil.getNameByCode(eventDictionaryCode, entity.getEvent()));

        return vo;
    }

    private WorkflowListenerConfig convert2Entity(WorkflowListenerConfigVO vo){

        WorkflowListenerConfig entity=mapperFacade.map(vo,WorkflowListenerConfig.class);
        return entity;
    }

}
