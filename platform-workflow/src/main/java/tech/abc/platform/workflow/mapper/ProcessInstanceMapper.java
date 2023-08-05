package tech.abc.platform.workflow.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import tech.abc.platform.workflow.entity.ProcessInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 流程实例 Mapper 接口
 * @author wqliu
 * @date 2020-09-18
 *
 */
public interface ProcessInstanceMapper extends BaseMapper<ProcessInstance>{

    /**
     * 自定义分页查询
     * @param page 分页信息
     * @param queryWrapper 查询对象
     * @return 分页数据
     */
    IPage<ProcessInstance> customPage(IPage<ProcessInstance> page,
                                      @Param(Constants.WRAPPER) Wrapper<ProcessInstance> queryWrapper);

    /**
     * 查询,会签任务时会查出多条
     * @param queryWrapper 查询对象
     * @return 查询结果
     */
    List<ProcessInstance> get(@Param(Constants.WRAPPER) Wrapper<ProcessInstance> queryWrapper);
}
