package tech.abc.platform.cip.message.sender.response.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.common.enums.MessageResponseResultEnum;
import tech.abc.platform.cip.message.framework.MessageServerHolder;
import tech.abc.platform.cip.message.framework.sender.ResponseMessageSender;
import tech.abc.platform.cip.message.sender.request.lms.transportbill.ConsignmentBillCreateRequestSender;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.common.utils.SpringUtil;


/**
 * 登录响应发送器
 *
 * @author wqliu
 * @date 2022-1-31 8:49
 **/
public class LoginResponseSender extends ResponseMessageSender {
    public LoginResponseSender() {
        super("framework.login.response");
    }


    @Override
    protected void setResponseContent(RequestMessage requestMessage, ResponseMessage responseMessage, Channel channel) {
        // 进行身份认证
        String content = requestMessage.getContent();
        JSONObject jsonObject = JSON.parseObject(content);
        String appCode = jsonObject.getString("appCode");
        String appSecret = jsonObject.getString("appSecret");
        AppService apiAppService = SpringUtil.getBean(AppService.class);

        try {
            apiAppService.authorize(appCode, appSecret);
            // 登录成功
            responseMessage.setResult(MessageResponseResultEnum.SUCCESS.name());
            // 登录成功后，将channel添加到全局集合
            MessageServerHolder.addChannel(channel, appCode);

            // 业务接口测试
            ConsignmentBillCreateRequestSender sender=new ConsignmentBillCreateRequestSender();
            sender.sendMessage("委托单创建");

        } catch (Exception e) {
            responseMessage.setResult(MessageResponseResultEnum.ERROR.name());
            responseMessage.setErrorCode("S600");
            responseMessage.setErrorMessage(e.getMessage());

        }
    }

}
