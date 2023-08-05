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
import tech.abc.platform.workflow.entity.ProcessTask;
import tech.abc.platform.workflow.service.ProcessTaskService;
import tech.abc.platform.workflow.vo.ProcessTaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
/**
 *   我的待办 控制器
 *
 * @author wqliu
 * @date 2023-07-26
 */
@RestController
@RequestMapping("/workflow/todo")
@Slf4j
public class TodoController extends BaseController {


    @Autowired
    private ProcessTaskService processTaskService;


    /**
     * 待办任务查询
     */
    @GetMapping("/page")
    @SystemLog(value = "我的待办-分页查询")
    public ResponseEntity<Result> getTodo(ProcessTaskVO queryVO,PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<ProcessTask> page = new Page<ProcessTask>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<ProcessTask> queryWrapper = QueryGenerator.generateQueryWrapper(ProcessTask.class, queryVO,
                sortInfo);

        //查询数据
        processTaskService.getTodo(page, queryWrapper);

        //转换vo
        IPage<ProcessTaskVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ProcessTaskVO>  processTaskVOList=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            ProcessTaskVO vo = convert2VO(page.getRecords().get(i));
            processTaskVOList.add(vo);
        }
        pageVO.setRecords(processTaskVOList);        ;
        return ResultUtil.success(pageVO);
    }

    private ProcessTaskVO convert2VO(ProcessTask entity) {
        ProcessTaskVO vo = mapperFacade.map(entity, ProcessTaskVO.class);
        return vo;
    }


}
