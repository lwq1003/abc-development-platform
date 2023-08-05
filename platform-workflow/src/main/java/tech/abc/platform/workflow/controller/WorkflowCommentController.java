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
import tech.abc.platform.workflow.entity.WorkflowComment;
import tech.abc.platform.workflow.service.WorkflowCommentService;
import tech.abc.platform.workflow.vo.WorkflowCommentVO;
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
* 处理意见 前端控制器类
*
* @author wqliu
* @date 2023-07-25
*/
@RestController
@RequestMapping("/workflow/workflowComment")
@Slf4j
public class WorkflowCommentController extends BaseController {
    @Autowired
    private WorkflowCommentService workflowCommentService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        WorkflowComment entity=workflowCommentService.init();
        WorkflowCommentVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "处理意见-新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowComment:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody WorkflowCommentVO vo) {
        WorkflowComment entity=convert2Entity(vo);
        workflowCommentService.add(entity);
        WorkflowCommentVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "处理意见-修改")
    @PreAuthorize("hasPermission(null,'workflow:workflowComment:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody WorkflowCommentVO vo) {
        WorkflowComment entity=convert2Entity(vo);
        workflowCommentService.modify(entity);
        WorkflowCommentVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "处理意见-删除")
    @PreAuthorize("hasPermission(null,'workflow:workflowComment:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        workflowCommentService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "处理意见-分页")
    @PreAuthorize("hasPermission(null,'workflow:workflowComment:query')")
    public ResponseEntity<Result> page(WorkflowCommentVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<WorkflowComment> page = new Page<WorkflowComment>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<WorkflowComment> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowComment.class,queryVO,sortInfo);

        //查询数据
        workflowCommentService.page(page, queryWrapper);
        //转换vo
        IPage<WorkflowCommentVO> pageVO = mapperFacade.map(page, IPage.class);
        List<WorkflowCommentVO>  workflowCommentVOList=convert2VO(page.getRecords());
        pageVO.setRecords(workflowCommentVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "处理意见-列表")
    @PreAuthorize("hasPermission(null,'workflow:workflowComment:query')")
    public ResponseEntity<Result> list(WorkflowCommentVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<WorkflowComment> queryWrapper = QueryGenerator.generateQueryWrapper(WorkflowComment.class, queryVO,sortInfo);
        List<WorkflowComment> list= workflowCommentService.list(queryWrapper);
        //转换vo
        List<WorkflowCommentVO>  workflowCommentVOList=convert2VO(list);
        return ResultUtil.success(workflowCommentVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "处理意见-详情")
    @PreAuthorize("hasPermission(null,'workflow:workflowComment:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        WorkflowComment entity = workflowCommentService.query(id);
        WorkflowCommentVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "处理意见-复制新增")
    @PreAuthorize("hasPermission(null,'workflow:workflowComment:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        workflowCommentService.addByCopy(id);
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
    private WorkflowCommentVO convert2VO(WorkflowComment entity){
        WorkflowCommentVO vo=mapperFacade.map(entity,WorkflowCommentVO.class);
        vo.setCommitTypeName(dictionaryUtil.getNameByCode("CommitType", entity.getCommitType()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<WorkflowCommentVO> convert2VO(List<WorkflowComment> entityList) {
        List<WorkflowCommentVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            WorkflowCommentVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private WorkflowComment convert2Entity(WorkflowCommentVO vo){
        WorkflowComment entity=mapperFacade.map(vo,WorkflowComment.class);
        return entity;
    }

    //endregion
 }