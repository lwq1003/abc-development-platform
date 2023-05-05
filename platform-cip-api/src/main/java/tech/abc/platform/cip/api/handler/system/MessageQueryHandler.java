package tech.abc.platform.cip.api.handler.system;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.api.framework.BaseServiceHandler;
import tech.abc.platform.cip.entity.MessageLog;
import tech.abc.platform.cip.service.MessageLogService;
import tech.abc.platform.common.utils.SpringUtil;

import java.util.List;

/**
 * 消息查询处理器
 *
 * @author wqliu
 * @date 2021-8-20
 **/
@Slf4j
public class MessageQueryHandler extends BaseServiceHandler<MessageQueryParameter> {


    @Override
    protected String handleBusiness(MessageQueryParameter parameter, String appCode) {
        MessageLogService service = SpringUtil.getBean(MessageLogService.class);
        List<MessageLog> list = service.queryWaitHandleMessages(parameter.getCount(), appCode);
        String data = JSON.toJSONString(list);
        log.info("查询到的待处理消息为：{}", data);
        return data;
    }
}
