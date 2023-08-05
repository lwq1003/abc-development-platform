package tech.abc.platform.workflow.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.common.annotation.AllowAll;
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
import tech.abc.platform.workflow.entity.WorkflowNodeConfig;
import tech.abc.platform.workflow.service.WorkflowNodeConfigService;
import tech.abc.platform.workflow.vo.WorkflowNodeConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
* 流程环节配置 前端控制器类
*
* @author wqliu
* @date 2023-07-16
*/
@RestController
@RequestMapping("/workflow/workflowNodeConfig")
@Slf4j
public class WorkflowNodeConfigController extends BaseController {
    @Autowired
    private WorkflowNodeConfigService flowNodeConfigService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowNodeConfig entity=flowNodeConfigService.init();
        WorkflowNodeConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "流程环节配置-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodeConfig:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowNodeConfigVO vo) {
        WorkflowNodeConfig entity=convert2Entity(vo);
        flowNodeConfigService.add(entity);
        WorkflowNodeConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "流程环节配置-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodeConfig:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowNodeConfigVO vo) {
        WorkflowNodeConfig entity=convert2Entity(vo);
        flowNodeConfigService.modify(entity);
        WorkflowNodeConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "流程环节配置-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodeConfig:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        flowNodeConfigService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "流程环节配置-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodeConfig:query')")
    public ResponseEntity<Result> page(WorkflowNodeConfigVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<WorkflowNodeConfig> page = new Page<WorkflowNodeConfig>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<WorkflowNodeConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowNodeConfig.class,queryVO,sortInfo);

        //查询数据
        flowNodeConfigService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowNodeConfigVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowNodeConfigVO>  flowNodeConfigVOList=convert2VO(page.getRecords());
        pageVO.setRecords(flowNodeConfigVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "流程环节配置-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodeConfig:query')")
    public ResponseEntity<Result> list(WorkflowNodeConfigVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowNodeConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowNodeConfig.class, queryVO,sortInfo);
        List<WorkflowNodeConfig> list= flowNodeConfigService.list(queryWrapper);
        //转换vo
        List<WorkflowNodeConfigVO>  flowNodeConfigVOList=convert2VO(list);
        return ResultUtil.success(flowNodeConfigVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "流程环节配置-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodeConfig:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowNodeConfig entity = flowNodeConfigService.query(id);
        WorkflowNodeConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "流程环节配置-复制新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowNodeConfig:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        flowNodeConfigService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作


    /**
     * 获取单条环节设置
     */
    @ApiOperation(value = "获取单条环节设置")
    @SystemLog(value = "流程环节配置-详情")
    @GetMapping("/getNodeConfig")
    @AllowAll
    public ResponseEntity<Result> getNodeConfig(String processDefinitionId,String definitionKey) {
        WorkflowNodeConfig entity = flowNodeConfigService.getNodeConfig(processDefinitionId,definitionKey);
        if(entity!=null) {
            WorkflowNodeConfigVO vo = convert2VO(entity);
            return ResultUtil.success(vo);
        }else{
            return ResultUtil.success();
        }
    }


    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    private WorkflowNodeConfigVO convert2VO(WorkflowNodeConfig entity){
        WorkflowNodeConfigVO vo=mapperFacade.map(entity, WorkflowNodeConfigVO.class);
        vo.setModeName(dictionaryUtil.getNameByCode("NodeMode", entity.getMode()));
        vo.setSetAssigneeFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getSetAssigneeFlag()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<WorkflowNodeConfigVO> convert2VO(List<WorkflowNodeConfig> entityList) {
        List<WorkflowNodeConfigVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            WorkflowNodeConfigVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private WorkflowNodeConfig convert2Entity(WorkflowNodeConfigVO vo){
        WorkflowNodeConfig entity=mapperFacade.map(vo, WorkflowNodeConfig.class);
        return entity;
    }

    //endregion
 }