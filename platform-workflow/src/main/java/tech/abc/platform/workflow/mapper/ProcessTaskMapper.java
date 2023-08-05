package tech.abc.platform.workflow.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import tech.abc.platform.workflow.entity.ProcessTask;
import org.apache.ibatis.annotations.Param;


/**
 * 流程任务 Mapper 接口
 * @author wqliu
 * @date 2020-09-14
 *
 */
public interface ProcessTaskMapper extends BaseMapper<ProcessTask>{

    /**
     * 自定义分页查询
     * @param page 分页信息
     * @param queryWrapper 查询对象
     * @return 分页数据
     */
    IPage<ProcessTask> customPage(IPage<ProcessTask> page, @Param(Constants.WRAPPER) Wrapper<ProcessTask> queryWrapper);

    /**
     * 单条查询
     * @param queryWrapper 查询对象
     * @return 单条数据
     */
    ProcessTask get(@Param(Constants.WRAPPER) Wrapper<ProcessTask> queryWrapper);


    /**
     * 待分配任务查询
     * @param page 分页信息
     * @param queryWrapper 查询对象
     * @return 分页数据
     */
    IPage<ProcessTask> todoTask(IPage<ProcessTask> page,
                                   @Param(Constants.WRAPPER) Wrapper<ProcessTask> queryWrapper);


}
