package tech.abc.platform.workflow.vo;

import lombok.Data;

import java.util.List;


/**
 * 流程模板分类视图对象
 *
 * @author wqliu
 * @date 2023-07-04
 */
@Data
public class WorkflowTemplateCategoryVO {


  /**
   * 编码
   */
  private String code;

  /**
   * 名称
   */
  private String name;


  /**
   * 流程定义
   */
  private List<WorkflowTemplateVO> workflowTemplateVOList;

}
