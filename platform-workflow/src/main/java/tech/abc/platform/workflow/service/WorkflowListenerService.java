package tech.abc.platform.workflow.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.workflow.entity.WorkflowListener;


/**
 * 流程监听器 服务类
 * @author wqliu
 * @date 2020-07-13
 *
 */
public interface WorkflowListenerService extends BaseService<WorkflowListener> {

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
}
