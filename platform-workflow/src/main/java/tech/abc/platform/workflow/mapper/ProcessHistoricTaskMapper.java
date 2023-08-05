package tech.abc.platform.workflow.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import tech.abc.platform.workflow.entity.ProcessHistoricTask;
import org.apache.ibatis.annotations.Param;


/**
 * 流程历史任务 Mapper 接口
 * @author wqliu
 * @date 2020-09-20
 *
 */
public interface ProcessHistoricTaskMapper extends BaseMapper<ProcessHistoricTask>{

    /**
     * 自定义分页查询
     * @param page 分页信息
     * @param queryWrapper 查询对象
     * @return 分页数据
     */
    IPage<ProcessHistoricTask> customPage(IPage<ProcessHistoricTask> page, @Param(Constants.WRAPPER) Wrapper<ProcessHistoricTask> queryWrapper);

    /**
     * 单条查询
     * @param queryWrapper 查询对象
     * @return 单条数据
     */
    ProcessHistoricTask get(@Param(Constants.WRAPPER) Wrapper<ProcessHistoricTask> queryWrapper);


}
