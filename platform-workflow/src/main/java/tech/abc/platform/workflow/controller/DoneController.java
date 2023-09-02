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
import tech.abc.platform.workflow.entity.ProcessHistoricTask;
import tech.abc.platform.workflow.service.ProcessHistoricTaskService;
import tech.abc.platform.workflow.vo.ProcessHistoricTaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的已办 控制器
 *
 * @author wqliu
 * @date 2023-07-26
 */
@RestController
@RequestMapping("/workflow/done")
@Slf4j
public class DoneController extends BaseController {

  @Autowired
  private ProcessHistoricTaskService processHistoricTaskService;


  /**
   * 已办任务查询
   */
  @GetMapping("/page")
  @SystemLog(value = "我的已办-分页查询")
  public ResponseEntity<Result> getTodo(ProcessHistoricTaskVO queryVO,PageInfo pageInfo, SortInfo sortInfo) {
    //构造分页对象
    IPage<ProcessHistoricTask> page = new Page<ProcessHistoricTask>(pageInfo.getPageNum(), pageInfo.getPageSize());
    //构造查询条件
    QueryWrapper<ProcessHistoricTask> queryWrapper = QueryGenerator.generateQueryWrapper(ProcessHistoricTask.class, queryVO,
            sortInfo);

    //查询数据
    processHistoricTaskService.getDone(page, queryWrapper);

    //转换vo
    IPage<ProcessHistoricTaskVO> pageVO = mapperFacade.map(page, IPage.class);
    List<ProcessHistoricTaskVO>  processHistoricTaskVOList=new ArrayList<>();
    for (int i = 0; i < page.getRecords().size(); i++) {
      ProcessHistoricTaskVO vo = convert2VO(page.getRecords().get(i));
      processHistoricTaskVOList.add(vo);
    }
    pageVO.setRecords(processHistoricTaskVOList);        ;
    return ResultUtil.success(pageVO);
  }

  private ProcessHistoricTaskVO convert2VO(ProcessHistoricTask entity) {
    ProcessHistoricTaskVO vo = mapperFacade.map(entity, ProcessHistoricTaskVO.class);
    return vo;
  }


}
