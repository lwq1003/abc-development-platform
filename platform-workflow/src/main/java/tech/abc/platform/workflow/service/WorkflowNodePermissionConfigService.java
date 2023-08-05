package tech.abc.platform.workflow.service;

import tech.abc.platform.workflow.entity.WorkflowNodePermissionConfig;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 工作流程环节权限配置 服务接口类
 *
 * @author wqliu
 * @date 2023-07-26
 */
public interface WorkflowNodePermissionConfigService extends BaseService<WorkflowNodePermissionConfig> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);
}

