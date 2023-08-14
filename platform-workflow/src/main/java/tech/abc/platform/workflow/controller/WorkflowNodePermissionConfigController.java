package tech.abc.platform.workflow.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.workflow.entity.WorkflowNodePermissionConfig;

import tech.abc.platform.workflow.service.WorkflowNodePermissionConfigService;
import tech.abc.platform.workflow.vo.WorkflowNodePermissionConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.workflow.vo.WorkflowRightConfigVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 工作流程环节权限配置 前端控制器类
*
* @author wqliu
* @date 2023-08-07
*/
@RestController
@RequestMapping("/workflow/workflowNodePermissionConfig")
@Slf4j
public class WorkflowNodePermissionConfigController extends BaseController {
    @Autowired
    private WorkflowNodePermissionConfigService workflowNodePermissionConfigService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowNodePermissionConfig entity=workflowNodePermissionConfigService.init();
        WorkflowNodePermissionConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "工作流程环节权限配置-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodePermissionConfig:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowNodePermissionConfigVO vo) {
        WorkflowNodePermissionConfig entity=convert2Entity(vo);
        workflowNodePermissionConfigService.add(entity);
        WorkflowNodePermissionConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "工作流程环节权限配置-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodePermissionConfig:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowNodePermissionConfigVO vo) {
        WorkflowNodePermissionConfig entity=convert2Entity(vo);
        workflowNodePermissionConfigService.modify(entity);
        WorkflowNodePermissionConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "工作流程环节权限配置-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodePermissionConfig:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowNodePermissionConfigService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "工作流程环节权限配置-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodePermissionConfig:query')")
    public ResponseEntity<Result> page(WorkflowNodePermissionConfigVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<WorkflowNodePermissionConfig> page = new Page<WorkflowNodePermissionConfig>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<WorkflowNodePermissionConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowNodePermissionConfig.class,queryVO,sortInfo);

        //查询数据
        workflowNodePermissionConfigService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowNodePermissionConfigVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowNodePermissionConfigVO>  workflowNodePermissionConfigVOList=convert2VO(page.getRecords());
        pageVO.setRecords(workflowNodePermissionConfigVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "工作流程环节权限配置-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodePermissionConfig:query')")
    public ResponseEntity<Result> list(WorkflowNodePermissionConfigVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowNodePermissionConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowNodePermissionConfig.class, queryVO,sortInfo);
        List<WorkflowNodePermissionConfig> list= workflowNodePermissionConfigService.list(queryWrapper);
        //转换vo
        List<WorkflowNodePermissionConfigVO>  workflowNodePermissionConfigVOList=convert2VO(list);
        return ResultUtil.success(workflowNodePermissionConfigVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "工作流程环节权限配置-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodePermissionConfig:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowNodePermissionConfig entity = workflowNodePermissionConfigService.query(id);
        WorkflowNodePermissionConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "工作流程环节权限配置-复制新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodePermissionConfig:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        workflowNodePermissionConfigService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作
    /**
     * 获取环节权限设置
     */
    @SystemLog(value = "工作流程环节权限配置-获取环节权限设置")
    @GetMapping("/getNodePermissionConfig")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodePermissionConfig:query')")
    public ResponseEntity<Result> getNodePermissionConfig(String processDefinitionId,
                                          String definitionKey) {
        List<WorkflowNodePermissionConfig> list = workflowNodePermissionConfigService.getNodePermissionConfig(processDefinitionId,
                definitionKey);
        //转换vo
        //转换vo
        List<WorkflowNodePermissionConfigVO>  workflowNodePermissionConfigVOList=convert2VO(list);
        return ResultUtil.success(workflowNodePermissionConfigVOList);
    }


    /**
     * 获取环节权限设置
     */
    @SystemLog(value = "工作流程环节权限配置-获取表单浏览模式下权限")
    @GetMapping("/getNodePermissionConfigForView")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodePermissionConfig:query')")
    public ResponseEntity<Result> getNodePermissionConfigForView(String processDefinitionId,
                                                          String definitionKey) {
        List<WorkflowNodePermissionConfig> list = workflowNodePermissionConfigService.getNodePermissionConfigForView(processDefinitionId);

        //转换vo
        List<WorkflowNodePermissionConfigVO>  workflowNodePermissionConfigVOList=convert2VO(list);
        return ResultUtil.success(workflowNodePermissionConfigVOList);
    }


    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    private WorkflowNodePermissionConfigVO convert2VO(WorkflowNodePermissionConfig entity){
        WorkflowNodePermissionConfigVO vo=mapperFacade.map(entity,WorkflowNodePermissionConfigVO.class);
        vo.setPermissionName(dictionaryUtil.getNameByCode("NodePermissionCode", entity.getPermission()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<WorkflowNodePermissionConfigVO> convert2VO(List<WorkflowNodePermissionConfig> entityList) {
        List<WorkflowNodePermissionConfigVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            WorkflowNodePermissionConfigVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private WorkflowNodePermissionConfig convert2Entity(WorkflowNodePermissionConfigVO vo){
        WorkflowNodePermissionConfig entity=mapperFacade.map(vo,WorkflowNodePermissionConfig.class);
        return entity;
    }

    //endregion
 }