package tech.abc.platform.cip.common.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.common.enums.MessageTypeEnum;

import java.util.List;

/**
 * 消息类型解码
 *
 * @author wqliu
 * @date 2021-10-6
 **/
public class MessageTypeDecodeHandler extends MessageToMessageDecoder<TextWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
        String message = msg.text();
        // 消息解析
        JSONObject jsonObject = JSONObject.parseObject(message);
        String messageType = jsonObject.getString("messageType");
        if (messageType.equals(MessageTypeEnum.REQUEST.name())) {
            RequestMessage requestMessage = JSON.parseObject(message, RequestMessage.class);
            out.add(requestMessage);

        } else if (messageType.equals(MessageTypeEnum.RESPONSE.name())) {

            ResponseMessage responseMessage = JSON.parseObject(message, ResponseMessage.class);
            out.add(responseMessage);
        }
    }

}
