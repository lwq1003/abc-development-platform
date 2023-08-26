package tech.abc.platform.workflow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.AllowAll;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.workflow.entity.FlowStep;
import tech.abc.platform.workflow.entity.WorkflowTemplate;
import tech.abc.platform.workflow.service.WorkflowTemplateService;
import tech.abc.platform.workflow.vo.WorkflowTemplateVO;

import java.util.ArrayList;
import java.util.List;

/**
* 流程模板 前端控制器类
*
* @author wqliu
* @date 2023-07-06
*/
@RestController
@RequestMapping("/workflow/workflowTemplate")
@Slf4j
public class WorkflowTemplateController extends BaseController {
    @Autowired
    private WorkflowTemplateService workflowTemplateService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowTemplate entity= workflowTemplateService.init();
        WorkflowTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "流程模板-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowTemplateVO vo) {
        WorkflowTemplate entity=convert2Entity(vo);
        workflowTemplateService.add(entity);
        WorkflowTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "流程模板-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowTemplateVO vo) {
        WorkflowTemplate entity=convert2Entity(vo);
        workflowTemplateService.modify(entity);
        WorkflowTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "流程模板-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowTemplateService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "流程模板-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:query')")
    public ResponseEntity<Result> page(WorkflowTemplateVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<WorkflowTemplate> page = new Page<WorkflowTemplate>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<WorkflowTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowTemplate.class,queryVO,sortInfo);

        //查询数据
        workflowTemplateService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowTemplateVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowTemplateVO>  flowTemplateVOList=convert2VO(page.getRecords());
        pageVO.setRecords(flowTemplateVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "流程模板-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:query')")
    public ResponseEntity<Result> list(WorkflowTemplateVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowTemplate.class, queryVO,sortInfo);
        List<WorkflowTemplate> list= workflowTemplateService.list(queryWrapper);
        //转换vo
        List<WorkflowTemplateVO>  flowTemplateVOList=convert2VO(list);
        return ResultUtil.success(flowTemplateVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "流程模板-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowTemplate entity = workflowTemplateService.query(id);
        WorkflowTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "流程模板-复制新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        workflowTemplateService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作
    /**
     * 启用
     */

    @PutMapping("/{id}/enable")
    @SystemLog(value = "流程模板-启用")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        workflowTemplateService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */

    @PutMapping("/{id}/disable")
    @SystemLog(value = "流程模板-停用")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        workflowTemplateService.disable(id);
        return ResultUtil.success();
    }

    /**
     * 发布
     */

    @PutMapping("/{id}/publish")
    @SystemLog(value = "流程模板-发布")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:publish')")
    public ResponseEntity<Result> publish(@PathVariable("id") String id) {

        workflowTemplateService.publish(id);
        return ResultUtil.success();
    }

    /**
     * 升级
     */

    @PutMapping("/{id}/upgrade")
    @SystemLog(value = "流程模板-升级")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:upgrade')")
    public ResponseEntity<Result> upgrade(@PathVariable("id") String id) {

        workflowTemplateService.upgrade(id);
        return ResultUtil.success();
    }


    /**
     * 启用版本（使生效）
     */

    @PutMapping("/{id}/valid")
    @SystemLog(value = "流程模板-启用版本")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:valid')")
    public ResponseEntity<Result> valid(@PathVariable("id") String id) {

        workflowTemplateService.valid(id);
        return ResultUtil.success();
    }


    /**
     * 生成临时版本
     */
    @GetMapping("/generateTemporaryVersion")
    @SystemLog(value = "流程模板-生成临时版本")
    @PreAuthorize("hasPermission(null,'workflow:workflowTemplate:query')")
    public ResponseEntity<Result> generateTemporaryVersion(String processDefinitionKey) {
        String temporaryVersion = workflowTemplateService.generateTemporaryVersion(processDefinitionKey);
        return ResultUtil.success(temporaryVersion);
    }


    /**
     * 获取模型数据
     */
    @GetMapping("/getModelByProcessDefinitionId")
    @SystemLog(value = "流程模板-获取模型数据")
    @AllowAll
    public ResponseEntity<Result> getModelByProcessDefinitionId(String processDefinitionId) {
        String model = workflowTemplateService.getModelByProcessDefinitionId(processDefinitionId);
        return ResultUtil.success(model);
    }


    /**
     * 获取模型数据
     */
    @GetMapping("/getUserTaskNodeByProcessDefinitionId")
    @SystemLog(value = "流程模板-获取所有用户类型节点")
    @AllowAll
    public ResponseEntity<Result> getUserTaskNodeByProcessDefinitionId(String processDefinitionId) {
        List<FlowStep> flowStepList = workflowTemplateService.getUserTaskNodeByProcessDefinitionId(processDefinitionId);
        return ResultUtil.success(flowStepList);
    }



    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    private WorkflowTemplateVO convert2VO(WorkflowTemplate entity){
        WorkflowTemplateVO vo=mapperFacade.map(entity, WorkflowTemplateVO.class);
        vo.setCategoryName(dictionaryUtil.getNameByCode("ProcessDefinitionCategory", entity.getCategory()));
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        vo.setTemplateStatusName(dictionaryUtil.getNameByCode("WorkflowTemplateStatus", entity.getTemplateStatus()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<WorkflowTemplateVO> convert2VO(List<WorkflowTemplate> entityList) {
        List<WorkflowTemplateVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            WorkflowTemplateVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private WorkflowTemplate convert2Entity(WorkflowTemplateVO vo){
        WorkflowTemplate entity=mapperFacade.map(vo, WorkflowTemplate.class);
        return entity;
    }

    //endregion
 }