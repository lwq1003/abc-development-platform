package tech.abc.platform.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.workflow.entity.ProcessInstance;

import java.util.List;


/**
 * 流程实例 服务类
 * @author wqliu
 * @date 2020-09-18
 *
 */
public interface ProcessInstanceService extends BaseService<ProcessInstance> {

    /**
     * 查询我的申请
     * @param page 分页信息
     * @param queryWrapper 查询对象
     * @return 分页数据
     */
    IPage<ProcessInstance> getApply(IPage<ProcessInstance> page, QueryWrapper<ProcessInstance> queryWrapper);


    /**
     * 获取某实例的某环节最后一次任务处理人
     * @param processInstanceId 流程实例
     * @param nodeId 节点标识
     * @return
     */
    List<User> getLastCommitter(String processInstanceId, String nodeId);


    /**
     * 获取环节处理人
     * @param processInstanceId 流程实例
     * @param nodeId 节点标识
     * @return
     */
    List<User> getNodeHandler(String processInstanceId, String nodeId);


    /**
     * 获取流程实例
     * @param id 流程实例标识
     * @return 流程实例
     */
    ProcessInstance get(String id);

    /**
     * 获取 我的申请 组件数据
     *
     * @param count 数
     * @return {@link List}<{@link ProcessInstance}>
     */
    List<ProcessInstance> getApplyPortletData(Integer count);
}
