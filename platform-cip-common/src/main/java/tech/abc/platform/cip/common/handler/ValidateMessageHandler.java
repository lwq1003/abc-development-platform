package tech.abc.platform.cip.common.handler;


import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import tech.abc.platform.cip.common.entity.BaseMessage;
import tech.abc.platform.cip.common.entity.MessageException;
import tech.abc.platform.cip.common.enums.MessageTypeEnum;
import tech.abc.platform.cip.common.tool.ValidateUtil;

/**
 * 消息验证处理器
 *
 * @author wqliu
 * @date 2022-1-19
 **/
@Slf4j
public class ValidateMessageHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
        String content = textWebSocketFrame.text();
        log.info("收到消息：{}", content);
        BaseMessage message = JSON.parseObject(content, BaseMessage.class);
        if (message != null) {
            // 验证消息属性
            validateProperty(message);
            ctx.fireChannelRead(textWebSocketFrame);
        } else {
            // 当收到未按约定格式发送过来的无法解析的消息时，仅记录错误日志
            log.error("收到无法解析的消息:{}", content);
        }

    }

    /**
     * 验证消息属性
     *
     * @param message 消息
     */
    protected void validateProperty(BaseMessage message) {

        String errorCode;
        String errorMessage;
        // id
        if (StringUtils.isBlank(message.getId())) {

            errorCode = "S001";
            errorMessage = "消息标识不能为空";
            throw new MessageException(errorCode, errorMessage);
        }
        // 主题
        if (StringUtils.isBlank(message.getTopic())) {

            errorCode = "S002";
            errorMessage = "消息主题不能为空";
            throw new MessageException(errorCode, errorMessage);
        }
        // 发布者
        if (StringUtils.isBlank(message.getPublishAppCode())) {

            errorCode = "S003";
            errorMessage = "消息发布者不能为空";
            throw new MessageException(errorCode, errorMessage);
        }

        // 发布时间
        String publishTimeString = message.getPublishTime();
        if (StringUtils.isBlank(publishTimeString)) {
            errorCode = "S004";
            errorMessage = "发布时间不能为空";
            throw new MessageException(errorCode, errorMessage);

        } else if (!ValidateUtil.dateIsFormat(publishTimeString)) {
            errorCode = "S005";
            errorMessage = "发布时间格式不正确";
            throw new MessageException(errorCode, errorMessage);

        }
        // 消息类型
        if (StringUtils.isBlank(message.getMessageType())) {

            errorCode = "S006";
            errorMessage = "消息类型不能为空";
            throw new MessageException(errorCode, errorMessage);
        } else {
            if (EnumUtils.isValidEnum(MessageTypeEnum.class, message.getMessageType()) == false) {
                errorCode = "S007";
                errorMessage = "消息类型无效";
                throw new MessageException(errorCode, errorMessage);
            }
        }
    }

}
