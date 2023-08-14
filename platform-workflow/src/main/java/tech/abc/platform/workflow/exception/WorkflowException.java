package tech.abc.platform.workflow.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;


/**
 * 工作流异常类
 *
 * @author wqliu
 * @date 2023-08-11
 */
@Getter
public enum WorkflowException  implements ExceptionInterface {

    /**
     * 流程模板已发布过
     */
    HAVE_PUBLISHED("流程模板已发布过"),
    /**
     * 流程模板已发布过
     */
    IS_NOT_RUNNING("流程模板当前未处于运行状态"),
    // PROCESS_TEMPLATE_HAVE_PUBLISHED("流程模板已发布，请先撤销"),
    //
    //
    // PROCESS_TEMPLATE_EDITOR_SOURCE_CREATE_FAILURE("流程模板创建服务异常"),
    // PROCESS_TEMPLATE_DATA_EMPTY("流程模板数据为空"),
    // PROCESS_TEMPLATE_PUBLISH_ERROR("流程模板发布出错，请确认是否未绘制流程图"),
    //
    // PROCESS_TEMPLATE_REVOKE_PUBLISH_ERROR("流程模板撤销发布出错，请确认是否存在在途流程实例"),
    ;


    private String message;
    WorkflowException(String message) {

        this.message = message;
    }

}
