package tech.abc.platform.workflow.controller;

import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.vo.UserVO;
import tech.abc.platform.workflow.entity.ProcessInstance;
import tech.abc.platform.workflow.service.ProcessInstanceService;
import tech.abc.platform.workflow.vo.ProcessInstanceVO;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 流程实例 控制器
 * @author wqliu
 */
@RestController
@RequestMapping("/workflow/processInstance")
@Slf4j
public class ProcessInstanceController extends BaseController {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private IdentityService identityService;

  @Autowired
  private HistoryService historyService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private ProcessInstanceService processInstanceService;



  /**
   * 获取任务标识 通常用于流程启动后获取首环节任务标识
   */
  @GetMapping("/{processInstanceId}/getTaskId")
  public ResponseEntity<Result> getTaskId(@PathVariable String processInstanceId) {

    // 获取当前任务信息
    TaskQuery query = taskService.createTaskQuery();
    query.processInstanceId(processInstanceId);
    Task task = query.singleResult();
    return ResultUtil.success(task.getId());

    
  }

  /**
   * 流程实例查看
   */
  @GetMapping("{id}")
  @SystemLog(value = "流程实例-详情")
  public ResponseEntity<Result> get(@PathVariable String id) {
    ProcessInstance entity = processInstanceService.get(id);
    ProcessInstanceVO vo = convert2VO(entity);
    return ResultUtil.success(vo);

  }


  /**
   * 获取指定环节最后一次提交人
   *
   */
  @GetMapping("{processInstanceId}/{nodeId}/getLastCommitter")
  public ResponseEntity<Result> getLastCommitter(@PathVariable String processInstanceId, @PathVariable String nodeId) {

    List<User> userList = processInstanceService.getLastCommitter(processInstanceId,nodeId);
    List<UserVO> voList = mapperFacade.mapAsList(userList, UserVO.class);
    return ResultUtil.success(voList);


  }

  /**
   * 获取环节处理人
   */
  @GetMapping("{processInstanceId}/{nodeId}/getNodeHandler")
  public ResponseEntity<Result> getNodeHandler(@PathVariable String processInstanceId, @PathVariable String nodeId) {

    List<User> userList = processInstanceService.getNodeHandler(processInstanceId,nodeId);
    List<UserVO> voList = mapperFacade.mapAsList(userList, UserVO.class);
    return ResultUtil.success(voList);


  }


  private ProcessInstanceVO convert2VO(ProcessInstance entity) {
    ProcessInstanceVO vo = mapperFacade.map(entity, ProcessInstanceVO.class);
    vo.setStateName(dictionaryUtil.getNameByCode("FlowStatus", entity.getState()));
    return vo;
  }

}
