package tech.abc.platform.workflow.controller;

import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.workflow.entity.ProcessTask;
import tech.abc.platform.workflow.entity.WorkflowNodeConfig;
import tech.abc.platform.workflow.service.ProcessTaskService;
import tech.abc.platform.workflow.vo.ProcessTaskVO;
import tech.abc.platform.workflow.vo.TaskCommitVO;
import tech.abc.platform.workflow.vo.WorkflowNodeConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程任务 前端控制器
 * @author wqliu
 * @date 2020-09-14
 *
 */
@RestController
@RequestMapping("/workflow/task")
@Slf4j
public class ProcessTaskController extends BaseController {
 @Autowired
 private ProcessTaskService processTaskService;


 //region 任务处理
 /**
  * 任务提交
  */
 @PutMapping("{id}/commit")
 @SystemLog(value = "待办任务-任务提交")
 public ResponseEntity<Result> commit(@PathVariable String id, @RequestBody TaskCommitVO vo) {

  processTaskService.commit(vo.getTaskId(),vo.getComment(),vo.getNextStepId(),
          vo.getAssigneeList());
  return ResultUtil.success();
 }

 /**
  * 任务驳回
  */
 @PutMapping("{id}/reject")
 @SystemLog(value = "待办任务-任务提交")
 public ResponseEntity<Result> reject(@PathVariable String id, @RequestBody TaskCommitVO vo) {

  processTaskService.reject(vo.getTaskId(),vo.getComment(),vo.getNextStepId(),
          vo.getAssigneeList());
  return ResultUtil.success();
 }

 /**
  * 任务转办
  */
 @PutMapping("{id}/transfer")
 @SystemLog(value = "任务处理-任务转办")
 public ResponseEntity<Result> transfer(@PathVariable String id,
                                        String assignee,
                                        String comment) {
  processTaskService.transfer(id,assignee,comment);
  return ResultUtil.success();
 }

 /**
  * 任务委派
  */
 @PutMapping("{id}/delegate")
 @SystemLog(value = "任务处理-任务委派")
 public ResponseEntity<Result> delegate(@PathVariable String id, String assignee,
                                        String comment) {
  processTaskService.delegate(id,assignee,comment);
  return ResultUtil.success();
 }

 /**
  * 任务签收
  */
 @PutMapping("{id}/signIn")
 @SystemLog(value = "任务处理-任务签收")
 public ResponseEntity<Result> signIn(@PathVariable String id) {
  processTaskService.signIn(id);
  return ResultUtil.success();
 }

 /**
  * 任务撤销签收
  */
 @PutMapping("{id}/cancelSignIn")
 @SystemLog(value = "任务处理-任务撤销签收")
 public ResponseEntity<Result> cancelSignIn(@PathVariable String id) {
  processTaskService.cancelSignIn(id);
  return ResultUtil.success();
 }


 /**
  * 获取后续任务节点列表
  *
  * 直接找当前环节所有出线指向的环节，可能为用户任务，也可能为网关
  * 排除标记为回退的条件边
  */
 @GetMapping("{taskId}/getForwardNodeList")
 public ResponseEntity<Result> getForwardNodeList(@PathVariable String taskId) {
  List<WorkflowNodeConfig> nodeConfigList=processTaskService.getForwardNodeList(taskId);
  List<WorkflowNodeConfigVO> voList = mapperFacade.mapAsList(nodeConfigList, WorkflowNodeConfigVO.class);
  return ResultUtil.success(voList);
 }

 //endregion

 /**
  * 获取回退节点
  * 标记为回退条件边指向的目标环节
  */
 @GetMapping("{taskId}/getBackNodeList")
 public ResponseEntity<Result> getBackNodeList(@PathVariable String taskId) {

  List<WorkflowNodeConfig> nodeConfigList=processTaskService.getBackNodeList(taskId);
  List<WorkflowNodeConfigVO> voList = mapperFacade.mapAsList(nodeConfigList, WorkflowNodeConfigVO.class);
  return ResultUtil.success(voList);

 }

 /**
  * 任务查看
  */
 @GetMapping("{taskId}")
 public ResponseEntity<Result> get(@PathVariable String taskId) {

  ProcessTask task = processTaskService.get(taskId);
  ProcessTaskVO vo = convert2VO(task);
  return ResultUtil.success(vo);
 }

//region 查询


//endregion


 private ProcessTaskVO convert2VO(ProcessTask entity) {

  ProcessTaskVO vo = mapperFacade.map(entity, ProcessTaskVO.class);

  return vo;
 }


}
