package tech.popsoft.cip.client.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.popsoft.cip.client.manage.entity.ApiMessageTopic;
import tech.popsoft.cip.client.manage.exception.ApiMessageTopicExceptionEnum;
import tech.popsoft.cip.client.manage.mapper.ApiMessageTopicMapper;
import tech.popsoft.cip.client.manage.service.ApiMessageTopicService;

import java.util.List;

/**
 * 消息主题 服务实现类
 *
 * @author wqliu
 * @date 2021-08-21
 */
@Service
public class ApiMessageTopicServiceImpl extends BaseServiceImpl<ApiMessageTopicMapper, ApiMessageTopic> implements ApiMessageTopicService {


    @Override
    public ApiMessageTopic init() {
        ApiMessageTopic entity = new ApiMessageTopic();
        entity.setStatus(StatusEnum.NORMAL.name());
        return entity;
    }

    @Override
    public void beforeAdd(ApiMessageTopic entity) {


        // 验证编码全局唯一
        QueryWrapper<ApiMessageTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ApiMessageTopic::getCode, entity.getCode());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }


        // 验证名称全局唯一
        queryWrapper.lambda().eq(ApiMessageTopic::getName, entity.getName());
        count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }


    }

    @Override
    public void beforeModify(ApiMessageTopic entity) {


        // 验证编码全局唯一
        QueryWrapper<ApiMessageTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ApiMessageTopic::getCode, entity.getCode())
                .ne(ApiMessageTopic::getId, entity.getId());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }


        // 验证名称全局唯一
        queryWrapper.lambda().eq(ApiMessageTopic::getName, entity.getName())
                .ne(ApiMessageTopic::getId, entity.getId());
        count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }


    }

    @Override
    public void enable(String id) {
        ApiMessageTopic entity = getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        ApiMessageTopic entity = getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }


    @Override
    public String getHandlerByCode(String code) {
        List<ApiMessageTopic> list = this.lambdaQuery().eq(ApiMessageTopic::getCode, code).list();
        if (CollectionUtils.isEmpty(list)) {
            throw new CustomException(ApiMessageTopicExceptionEnum.TOPIC_NOT_EXIST);
        }
        String handler = list.get(0).getHandler();
        if (StringUtils.isBlank(handler)) {
            throw new CustomException(ApiMessageTopicExceptionEnum.TOPIC_NOT_SET_HANDLER);
        }
        return handler;
    }

    @Override
    public String getResponseTopicCodeByCode(String code) {
        List<ApiMessageTopic> list = this.lambdaQuery().eq(ApiMessageTopic::getCode, code).list();
        if (CollectionUtils.isEmpty(list)) {
            throw new CustomException(ApiMessageTopicExceptionEnum.TOPIC_NOT_EXIST);
        }
        String responseTopicCode = list.get(0).getResponseTopicCode();
        if (StringUtils.isBlank(responseTopicCode)) {
            throw new CustomException(ApiMessageTopicExceptionEnum.RESPONSE_TOPIC_NOT_SET_HANDLER);
        }
        return responseTopicCode;
    }

    @Override
    public String getIdByCode(String code) {
        return getByCode(code).getId();
    }

    @Override
    public ApiMessageTopic getByCode(String code) {
        List<ApiMessageTopic> list = this.lambdaQuery().eq(ApiMessageTopic::getCode, code).list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);

        } else {
            throw new CustomException(ApiMessageTopicExceptionEnum.TOPIC_NOT_EXIST);
        }
    }

    @Override
    public String getSenderByCode(String code) {
        ApiMessageTopic apiMessageTopic = getByCode(code);
        String sender = apiMessageTopic.getSender();
        if (StringUtils.isBlank(sender)) {
            throw new CustomException(ApiMessageTopicExceptionEnum.TOPIC_NOT_SET_SENDER);
        }
        return sender;
    }

}