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
import tech.abc.platform.workflow.entity.WorkflowBackNodeConfig;
import tech.abc.platform.workflow.service.WorkflowBackNodeConfigService;
import tech.abc.platform.workflow.vo.WorkflowBackNodeConfigVO;
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
* 工作流程回退环节配置 前端控制器类
*
* @author wqliu
* @date 2023-08-23
*/
@RestController
@RequestMapping("/workflow/workflowBackNodeConfig")
@Slf4j
public class WorkflowBackNodeConfigController extends BaseController {
    @Autowired
    private WorkflowBackNodeConfigService workflowBackNodeConfigService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowBackNodeConfig entity=workflowBackNodeConfigService.init();
        WorkflowBackNodeConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "工作流程回退环节配置-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowBackNodeConfig:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowBackNodeConfigVO vo) {
        WorkflowBackNodeConfig entity=convert2Entity(vo);
        workflowBackNodeConfigService.add(entity);
        WorkflowBackNodeConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "工作流程回退环节配置-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowBackNodeConfig:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowBackNodeConfigVO vo) {
        WorkflowBackNodeConfig entity=convert2Entity(vo);
        workflowBackNodeConfigService.modify(entity);
        WorkflowBackNodeConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "工作流程回退环节配置-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowBackNodeConfig:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowBackNodeConfigService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "工作流程回退环节配置-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowBackNodeConfig:query')")
    public ResponseEntity<Result> page(WorkflowBackNodeConfigVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<WorkflowBackNodeConfig> page = new Page<WorkflowBackNodeConfig>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<WorkflowBackNodeConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowBackNodeConfig.class,queryVO,sortInfo);

        //查询数据
        workflowBackNodeConfigService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowBackNodeConfigVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowBackNodeConfigVO>  workflowBackNodeConfigVOList=convert2VO(page.getRecords());
        pageVO.setRecords(workflowBackNodeConfigVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "工作流程回退环节配置-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowBackNodeConfig:query')")
    public ResponseEntity<Result> list(WorkflowBackNodeConfigVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowBackNodeConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowBackNodeConfig.class, queryVO,sortInfo);
        List<WorkflowBackNodeConfig> list= workflowBackNodeConfigService.list(queryWrapper);
        //转换vo
        List<WorkflowBackNodeConfigVO>  workflowBackNodeConfigVOList=convert2VO(list);
        return ResultUtil.success(workflowBackNodeConfigVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "工作流程回退环节配置-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowBackNodeConfig:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowBackNodeConfig entity = workflowBackNodeConfigService.query(id);
        WorkflowBackNodeConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "工作流程回退环节配置-复制新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowBackNodeConfig:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        workflowBackNodeConfigService.addByCopy(id);
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
    private WorkflowBackNodeConfigVO convert2VO(WorkflowBackNodeConfig entity){
        WorkflowBackNodeConfigVO vo=mapperFacade.map(entity,WorkflowBackNodeConfigVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<WorkflowBackNodeConfigVO> convert2VO(List<WorkflowBackNodeConfig> entityList) {
        List<WorkflowBackNodeConfigVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            WorkflowBackNodeConfigVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private WorkflowBackNodeConfig convert2Entity(WorkflowBackNodeConfigVO vo){
        WorkflowBackNodeConfig entity=mapperFacade.map(vo,WorkflowBackNodeConfig.class);
        return entity;
    }

    //endregion
 }