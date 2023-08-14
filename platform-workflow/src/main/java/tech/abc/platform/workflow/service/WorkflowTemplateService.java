package tech.abc.platform.workflow.service;

import tech.abc.platform.workflow.entity.WorkflowTemplate;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 流程模板 服务接口类
 *
 * @author wqliu
 * @date 2023-07-06
 */
public interface WorkflowTemplateService extends BaseService<WorkflowTemplate> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);


   /**
    * 启用
    *
    * @param id 标识
    */
   void enable(String id);

   /**
    * 停用
    *
    * @param id 标识
    */
   void disable(String id);




   /**
    * 获取当前用户可发起的流程模板清单
    *
    * @return {@link List}<{@link String}>
    */
   List<WorkflowTemplate> listByPermission();


   /**
    * 验证是否具备流程发起权限
    *
    * @param code 流程编码
    */
   void checkProcessStartPermission(String code);


   /**
    * 获取流程模板
    *
    * @param code 模板编码
    * @return {@link WorkflowTemplate}
    */
   WorkflowTemplate getByCode(String code);


   /**
    * 生成临时版本
    *
    * @param processDefinitionKey 流程定义键
    * @return {@link String}
    */
   String generateTemporaryVersion(String processDefinitionKey);

   /**
    * 发布
    *
    * @param id id
    */
   void publish(String id);

   /**
    * 升级
    *
    * @param id id
    */
   void upgrade(String id);
}

