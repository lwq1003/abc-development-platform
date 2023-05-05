package tech.abc.platform.cip.api.handler.system;

import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.api.exception.ApiException;
import tech.abc.platform.cip.api.framework.BaseServiceHandler;
import tech.abc.platform.cip.service.MessageLogService;
import tech.abc.platform.common.utils.SpringUtil;


/**
 * 消息确认处理器
 *
 * @author wqliu
 * @date 2022-02-14
 */
@Slf4j
public class MessageConfirmHandler extends BaseServiceHandler<MessageConfirmParameter> {


    @Override
    protected String handleBusiness(MessageConfirmParameter parameter, String appCode) {
        MessageLogService service = SpringUtil.getBean(MessageLogService.class);
        try {
            service.confirm(parameter.getMessageId(), appCode);
        } catch (Exception e) {
            throw new ApiException("B01", e.getMessage());
        }

        log.info("消息确认成功");
        return null;
    }

}
