package tech.abc.platform.support.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import tech.abc.platform.support.entity.Notice;

import java.util.List;


/**
 * 通知公告 Mapper 接口
 *
 * @author wqliu
 * @date 2023-05-20
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 获取组件数据
     *
     * @param count          数量
     * @param organizationId 组织机构标识
     * @return {@link List}<{@link Notice}>
     */
    List<Notice> getPortletData(@Param("organizationId") String organizationId, @Param("count") int count);


    /**
     * 得到通知视图列表
     * 获取通知通告查看列表
     *
     * @param page           页面
     * @param queryWrapper   查询包装
     * @param organizationId 组织机构标识
     * @return {@link IPage}<{@link Notice}>
     */
    IPage<Notice> getNoticeViewList(IPage<Notice> page,
                                    @Param(Constants.WRAPPER) Wrapper<Notice> queryWrapper, @Param("organizationId") String organizationId);

}

