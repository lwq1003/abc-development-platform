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
import tech.abc.platform.workflow.entity.WorkflowListenerConfig;
import tech.abc.platform.workflow.service.WorkflowListenerConfigService;
import tech.abc.platform.workflow.vo.WorkflowListenerConfigVO;
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
* 工作流程监听器配置 前端控制器类
*
* @author wqliu
* @date 2023-08-29
*/
@RestController
@RequestMapping("/workflow/workflowListenerConfig")
@Slf4j
public class WorkflowListenerConfigController extends BaseController {
    @Autowired
    private WorkflowListenerConfigService workflowListenerConfigService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowListenerConfig entity=workflowListenerConfigService.init();
        WorkflowListenerConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "工作流程监听器配置-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowListenerConfig:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowListenerConfigVO vo) {
        WorkflowListenerConfig entity=convert2Entity(vo);
        workflowListenerConfigService.add(entity);
        WorkflowListenerConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "工作流程监听器配置-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowListenerConfig:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowListenerConfigVO vo) {
        WorkflowListenerConfig entity=convert2Entity(vo);
        workflowListenerConfigService.modify(entity);
        WorkflowListenerConfigVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "工作流程监听器配置-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowListenerConfig:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowListenerConfigService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "工作流程监听器配置-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowListenerConfig:query')")
    public ResponseEntity<Result> page(WorkflowListenerConfigVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<WorkflowListenerConfig> page = new Page<WorkflowListenerConfig>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<WorkflowListenerConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowListenerConfig.class,queryVO,sortInfo);

        //查询数据
        workflowListenerConfigService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowListenerConfigVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowListenerConfigVO>  workflowListenerConfigVOList=convert2VO(page.getRecords());
        pageVO.setRecords(workflowListenerConfigVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "工作流程监听器配置-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowListenerConfig:query')")
    public ResponseEntity<Result> list(WorkflowListenerConfigVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowListenerConfig> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowListenerConfig.class, queryVO,sortInfo);
        List<WorkflowListenerConfig> list= workflowListenerConfigService.list(queryWrapper);
        //转换vo
        List<WorkflowListenerConfigVO>  workflowListenerConfigVOList=convert2VO(list);
        return ResultUtil.success(workflowListenerConfigVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "工作流程监听器配置-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowListenerConfig:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowListenerConfig entity = workflowListenerConfigService.query(id);
        WorkflowListenerConfigVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "工作流程监听器配置-复制新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowListenerConfig:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        workflowListenerConfigService.addByCopy(id);
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
    private WorkflowListenerConfigVO convert2VO(WorkflowListenerConfig entity){
        WorkflowListenerConfigVO vo=mapperFacade.map(entity,WorkflowListenerConfigVO.class);
        vo.setCategoryName(dictionaryUtil.getNameByCode("WorkflowListenerCategory", entity.getCategory()));
        vo.setTypeName(dictionaryUtil.getNameByCode("WorkflowListenerType", entity.getType()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<WorkflowListenerConfigVO> convert2VO(List<WorkflowListenerConfig> entityList) {
        List<WorkflowListenerConfigVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            WorkflowListenerConfigVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private WorkflowListenerConfig convert2Entity(WorkflowListenerConfigVO vo){
        WorkflowListenerConfig entity=mapperFacade.map(vo,WorkflowListenerConfig.class);
        return entity;
    }

    //endregion
 }