package tech.abc.platform.workflow.controller;


import io.swagger.annotations.ApiImplicitParam;
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
import tech.abc.platform.workflow.entity.WorkflowListener;
import tech.abc.platform.workflow.service.WorkflowListenerService;
import tech.abc.platform.workflow.vo.WorkflowListenerVO;
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
* 工作流监听器 前端控制器类
*
* @author wqliu
* @date 2023-08-29
*/
@RestController
@RequestMapping("/workflow/workflowListener")
@Slf4j
public class WorkflowListenerController extends BaseController {
    @Autowired
    private WorkflowListenerService workflowListenerService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowListener entity=workflowListenerService.init();
        WorkflowListenerVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "工作流监听器-新增")
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
    @PutMapping("/")
    @SystemLog(value = "工作流监听器-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowListenerVO vo) {
        WorkflowListener entity=convert2Entity(vo);
        workflowListenerService.modify(entity);
        WorkflowListenerVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "工作流监听器-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowListenerService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "工作流监听器-分页")
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
        List<WorkflowListenerVO>  workflowListenerVOList=convert2VO(page.getRecords());
        pageVO.setRecords(workflowListenerVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "工作流监听器-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:query')")
    public ResponseEntity<Result> list(WorkflowListenerVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowListener> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowListener.class, queryVO,sortInfo);
        List<WorkflowListener> list= workflowListenerService.list(queryWrapper);
        //转换vo
        List<WorkflowListenerVO>  workflowListenerVOList=convert2VO(list);
        return ResultUtil.success(workflowListenerVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "工作流监听器-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowListener entity = workflowListenerService.query(id);
        WorkflowListenerVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "工作流监听器-复制新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        workflowListenerService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作
    /**
     * 启用
     */
    @PutMapping("/{id}/enable")
    @SystemLog(value = "工作流监听器-启用")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        workflowListenerService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */

    @PutMapping("/{id}/disable")
    @SystemLog(value = "工作流监听器-停用")
    @PreAuthorize("hasPermission(null,'workflow:workflowListener:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        workflowListenerService.disable(id);
        return ResultUtil.success();
    }

    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    private WorkflowListenerVO convert2VO(WorkflowListener entity){
        WorkflowListenerVO vo=mapperFacade.map(entity,WorkflowListenerVO.class);
        vo.setCategoryName(dictionaryUtil.getNameByCode("WorkflowListenerCategory", entity.getCategory()));
        vo.setTypeName(dictionaryUtil.getNameByCode("WorkflowListenerType", entity.getType()));
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<WorkflowListenerVO> convert2VO(List<WorkflowListener> entityList) {
        List<WorkflowListenerVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            WorkflowListenerVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private WorkflowListener convert2Entity(WorkflowListenerVO vo){
        WorkflowListener entity=mapperFacade.map(vo,WorkflowListener.class);
        return entity;
    }

    //endregion
 }