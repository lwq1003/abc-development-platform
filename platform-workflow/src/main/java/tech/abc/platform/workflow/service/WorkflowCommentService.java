package tech.abc.platform.workflow.service;

import tech.abc.platform.workflow.entity.WorkflowComment;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.workflow.enums.CommitTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * 处理意见 服务接口类
 *
 * @author wqliu
 * @date 2023-07-25
 */
public interface WorkflowCommentService extends BaseService<WorkflowComment> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);

   /**
    * 新增处理意见
    * @param processInstanceId 流程实例标识
    * @param stepName 环节名称
    * @param comment 处理意见
    * @param commitType 提交类型
    */
   void addComment(String processInstanceId,String stepName, String comment, CommitTypeEnum commitType);

}

