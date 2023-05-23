package tech.abc.platform.support.service.impl;

import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.PortletParam;
import tech.abc.platform.support.mapper.PortletParamMapper;
import tech.abc.platform.support.service.PortletParamService;

/**
 * 组件参数 服务实现类
 *
 * @author wqliu
 */
@Service
public class PortletParamServiceImpl extends BaseServiceImpl<PortletParamMapper, PortletParam> implements PortletParamService {
    @Override
    public PortletParam init() {
        PortletParam entity = new PortletParam();

        return entity;
    }

    @Override
    public void beforeAdd(PortletParam entity) {
        // 验证名称同目录下唯一
        if (this.lambdaQuery().eq(PortletParam::getParamName, entity.getParamName())
                .eq(PortletParam::getPortletId, entity.getPortletId())
                .count() > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }

        // 验证编码同目录下唯一
        if (this.lambdaQuery().eq(PortletParam::getParamCode, entity.getParamCode())
                .eq(PortletParam::getPortletId, entity.getPortletId())
                .count() > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }


    @Override
    public void beforeModify(PortletParam entity) {

        // 验证名称同目录下唯一
        if (this.lambdaQuery().eq(PortletParam::getParamName, entity.getParamName())
                .eq(PortletParam::getPortletId, entity.getPortletId())
                .ne(PortletParam::getId, entity.getId())
                .count() > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }

        // 验证编码同目录下唯一
        if (this.lambdaQuery().eq(PortletParam::getParamCode, entity.getParamCode())
                .eq(PortletParam::getPortletId, entity.getPortletId())
                .ne(PortletParam::getId, entity.getId())
                .count() > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }


    /**
     * 数据不为空检查，检查通过后返回对象
     *
     * @param id
     * @return
     */
    public PortletParam getEntity(String id) {
        // 标识非空判断
        if (id == null) {
            throw new CustomException(CommonException.ID_EMPTY);
        }
        // 对象非空判断
        PortletParam entity = query(id);
        if (entity == null) {
            throw new CustomException(CommonException.NOT_EXIST);
        }
        return entity;
    }

}
