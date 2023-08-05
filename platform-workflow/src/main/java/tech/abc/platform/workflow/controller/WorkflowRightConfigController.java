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
import tech.abc.platform.workflow.entity.WorkflowRightConfig;
import tech.abc.platform.workflow.service.WorkflowRightConfigService;
import tech.abc.platform.workflow.vo.WorkflowRightConfigVO;
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
import java.util.Map;

/**
 * 流程权限配置 前端控制器
 * @author wqliu
 * @date 2020-11-08
 *
 */
@RestController
@RequestMapping("/workflow/workflowRightConfig")
@Slf4j
public class WorkflowRightConfigController extends BaseController {
 @Autowired
 private WorkflowRightConfigService workflowRightConfigService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowRightConfig entity=workflowRightConfigService.init();
        WorkflowRightConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "流程权限配置-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowRightConfigVO vo) {
        WorkflowRightConfig entity=convert2Entity(vo);
        workflowRightConfigService.add(entity);
        WorkflowRightConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "流程权限配置-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowRightConfigVO vo)  {
        WorkflowRightConfig entity=convert2Entity(vo);
        //此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        //个别需展示的该类信息的地方可以重新查询后返回
        workflowRightConfigService.modify(entity);
        WorkflowRightConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "流程权限配置-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowRightConfigService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "流程权限配置-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> page(WorkflowRightConfigVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<WorkflowRightConfig> page = new Page<WorkflowRightConfig>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<WorkflowRightConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowRightConfig.class,queryVO,sortInfo);

        //查询数据
        workflowRightConfigService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowRightConfigVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowRightConfigVO>  workflowRightConfigVOList=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            WorkflowRightConfigVO vo = convert2VO(page.getRecords().get(i));
            workflowRightConfigVOList.add(vo);
        }
        pageVO.setRecords(workflowRightConfigVOList);        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "流程权限配置-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> list(WorkflowRightConfigVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowRightConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowRightConfig.class, queryVO,sortInfo);
        List<WorkflowRightConfig> list= workflowRightConfigService.list(queryWrapper);
        //转换vo
        List<WorkflowRightConfigVO>  workflowRightConfigVOList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            WorkflowRightConfigVO vo = convert2VO(list.get(i));
            workflowRightConfigVOList.add(vo);
        }
        return ResultUtil.success(workflowRightConfigVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @GetMapping("/{id}")
    @SystemLog(value = "流程权限配置-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowRightConfig entity = workflowRightConfigService.query(id);
        WorkflowRightConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    /**
     * 获取流程首环节权限数据
     */
    @ApiOperation(value = "获取流程首环节权限数据")
    @GetMapping("/getFirstNodeConfig")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> getFirstNodeConfig(String processDefinitionId) {
        Map<String,Object> result = workflowRightConfigService.getFirstNodeConfig(processDefinitionId);

        return ResultUtil.success(result);
    }

    /**
     * 获取流程环节权限数据
     */
    @ApiOperation(value = "获取流程环节权限数据")
    @GetMapping("/getNodeConfig")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> getNodeConfig(String processDefinitionId,String taskDefinitionKey) {
        Map<String,Object> result = workflowRightConfigService.getNodeConfig(processDefinitionId,taskDefinitionKey);

        return ResultUtil.success(result);
    }

    /**
     * 获取浏览模式下权限数据
     */
    @ApiOperation(value = "获取浏览模式下权限数据")
    @GetMapping("/getNodeConfigForView")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> getNodeConfigForView(String processDefinitionId) {
        Map<String,Object> result = workflowRightConfigService.getNodeConfigForView(processDefinitionId);

        return ResultUtil.success(result);
    }


    /**
     * 获取单条设置
     */
    @ApiOperation(value = "获取单条权限设置")
    @SystemLog(value = "流程权限配置-获取列表")
    @GetMapping("/getConfig")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> getConfig(String processDefinitionId,
                                            String processDefinitionKey,String definitionKey) {
        List<WorkflowRightConfig> list = workflowRightConfigService.getConfig(processDefinitionId,
                processDefinitionKey,definitionKey);
        //转换vo
        List<WorkflowRightConfigVO>  workflowRightConfigVOList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            WorkflowRightConfigVO vo = convert2VO(list.get(i));
            workflowRightConfigVOList.add(vo);
        }
        return ResultUtil.success(workflowRightConfigVOList);
    }


    /**
     * 保存权限配置
     */
    @ApiOperation(value = "保存权限配置")
    @PostMapping("/saveConfig")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> saveConfig(@Validated @RequestBody List<WorkflowRightConfigVO> voList)  {

        List<WorkflowRightConfig> list=mapperFacade.mapAsList(voList,WorkflowRightConfig.class);
        workflowRightConfigService.saveConfig(list);
        //转换vo
        List<WorkflowRightConfigVO>  workflowRightConfigVOList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            WorkflowRightConfigVO vo = convert2VO(list.get(i));
            workflowRightConfigVOList.add(vo);
        }
        return ResultUtil.success(workflowRightConfigVOList);
    }

    private WorkflowRightConfigVO convert2VO(WorkflowRightConfig entity){
        WorkflowRightConfigVO vo=mapperFacade.map(entity,WorkflowRightConfigVO.class);
        return vo;
    }

    private WorkflowRightConfig convert2Entity(WorkflowRightConfigVO vo){

        WorkflowRightConfig entity=mapperFacade.map(vo,WorkflowRightConfig.class);
        return entity;
    }

}
