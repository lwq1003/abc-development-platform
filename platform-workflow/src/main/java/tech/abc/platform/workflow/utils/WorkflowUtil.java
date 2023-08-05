package tech.abc.platform.workflow.utils;

import lombok.experimental.UtilityClass;

/**
 * 工作流工具类
 *
 * @author  wqliu
 * @date: 2020-10-11
 */

@UtilityClass
public class WorkflowUtil {

    /**
     * 生成流程定义临时版本的流程定义标识
     * @param processDefinitionKey 流程定义编码
     * @param version 当前发布的最新版本号
     * @return 临时版本的流程定义标识
     */
    public String generateTempVersionId(String processDefinitionKey,int version){
        return processDefinitionKey+":"+(version+1);
    }
}
