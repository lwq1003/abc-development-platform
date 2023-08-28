package tech.abc.platform.workflow.controller;


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
import tech.abc.platform.workflow.entity.WorkflowJumpNodeConfig;
import tech.abc.platform.workflow.service.WorkflowJumpNodeConfigService;
import tech.abc.platform.workflow.vo.WorkflowJumpNodeConfigVO;
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
* 工作流程跳转环节配置 前端控制器类
*
* @author wqliu
* @date 2023-08-21
*/
@RestController
@RequestMapping("/workflow/workflowJumpNodeConfig")
@Slf4j
public class WorkflowJumpNodeConfigController extends BaseController {
    @Autowired
    private WorkflowJumpNodeConfigService workflowJumpNodeConfigService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowJumpNodeConfig entity=workflowJumpNodeConfigService.init();
        WorkflowJumpNodeConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "工作流程跳转环节配置-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowJumpNodeConfig:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowJumpNodeConfigVO vo) {
        WorkflowJumpNodeConfig entity=convert2Entity(vo);
        workflowJumpNodeConfigService.add(entity);
        WorkflowJumpNodeConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "工作流程跳转环节配置-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowJumpNodeConfig:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowJumpNodeConfigVO vo) {
        WorkflowJumpNodeConfig entity=convert2Entity(vo);
        workflowJumpNodeConfigService.modify(entity);
        WorkflowJumpNodeConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "工作流程跳转环节配置-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowJumpNodeConfig:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowJumpNodeConfigService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "工作流程跳转环节配置-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowJumpNodeConfig:query')")
    public ResponseEntity<Result> page(WorkflowJumpNodeConfigVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<WorkflowJumpNodeConfig> page = new Page<WorkflowJumpNodeConfig>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<WorkflowJumpNodeConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowJumpNodeConfig.class,queryVO,sortInfo);

        //查询数据
        workflowJumpNodeConfigService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowJumpNodeConfigVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowJumpNodeConfigVO>  workflowJumpNodeConfigVOList=convert2VO(page.getRecords());
        pageVO.setRecords(workflowJumpNodeConfigVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "工作流程跳转环节配置-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowJumpNodeConfig:query')")
    public ResponseEntity<Result> list(WorkflowJumpNodeConfigVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowJumpNodeConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowJumpNodeConfig.class, queryVO,sortInfo);
        List<WorkflowJumpNodeConfig> list= workflowJumpNodeConfigService.list(queryWrapper);
        //转换vo
        List<WorkflowJumpNodeConfigVO>  workflowJumpNodeConfigVOList=convert2VO(list);
        return ResultUtil.success(workflowJumpNodeConfigVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "工作流程跳转环节配置-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowJumpNodeConfig:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowJumpNodeConfig entity = workflowJumpNodeConfigService.query(id);
        WorkflowJumpNodeConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "工作流程跳转环节配置-复制新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowJumpNodeConfig:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        workflowJumpNodeConfigService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作

    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    private WorkflowJumpNodeConfigVO convert2VO(WorkflowJumpNodeConfig entity){
        WorkflowJumpNodeConfigVO vo=mapperFacade.map(entity,WorkflowJumpNodeConfigVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<WorkflowJumpNodeConfigVO> convert2VO(List<WorkflowJumpNodeConfig> entityList) {
        List<WorkflowJumpNodeConfigVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            WorkflowJumpNodeConfigVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private WorkflowJumpNodeConfig convert2Entity(WorkflowJumpNodeConfigVO vo){
        WorkflowJumpNodeConfig entity=mapperFacade.map(vo,WorkflowJumpNodeConfig.class);
        return entity;
    }

    //endregion
 }