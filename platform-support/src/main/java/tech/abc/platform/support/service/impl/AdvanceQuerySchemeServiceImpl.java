package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.support.entity.AdvanceQueryScheme;
import tech.abc.platform.support.mapper.AdvanceQuerySchemeMapper;
import tech.abc.platform.support.service.AdvanceQuerySchemeService;

/**
 * 高级查询方案 服务实现类
 *
 * @author wqliu
 */
@Service
public class AdvanceQuerySchemeServiceImpl extends BaseServiceImpl<AdvanceQuerySchemeMapper, AdvanceQueryScheme> implements AdvanceQuerySchemeService {
    @Override
    public AdvanceQueryScheme init() {
        AdvanceQueryScheme entity = new AdvanceQueryScheme();

        return entity;
    }


    @Override
    protected void beforeAdd(AdvanceQueryScheme entity) {
        // 将方案归属人设置为当前用户
        entity.setUserId(UserUtil.getId());

        // 验证名称不重复
        QueryWrapper<AdvanceQueryScheme> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdvanceQueryScheme::getName, entity.getName())
                .eq(AdvanceQueryScheme::getUserId, entity.getUserId())
                .eq(AdvanceQueryScheme::getEntityCode, entity.getEntityCode());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }
    }

    @Override
    protected void beforeModify(AdvanceQueryScheme entity) {
        // 验证修改数据是否属于当前用户
        if (!entity.getUserId().equals(UserUtil.getId())) {
            throw new CustomException(CommonException.NO_PRIVILEGE_MODIFY_DATA);
        }

        // 验证名称不重复
        QueryWrapper<AdvanceQueryScheme> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdvanceQueryScheme::getName, entity.getName())
                .eq(AdvanceQueryScheme::getUserId, entity.getUserId())
                .eq(AdvanceQueryScheme::getEntityCode, entity.getEntityCode())
                .ne(AdvanceQueryScheme::getId, entity.getId());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }
    }

    /**
     * 数据不为空检查，检查通过后返回对象
     *
     * @param id
     * @return
     */
    public AdvanceQueryScheme getEntity(String id) {
        // 标识非空判断
        if (id == null) {
            throw new CustomException(CommonException.ID_EMPTY);
        }
        // 对象非空判断
        AdvanceQueryScheme entity = query(id);
        if (entity == null) {
            throw new CustomException(CommonException.NOT_EXIST);
        }
        return entity;
    }

}
