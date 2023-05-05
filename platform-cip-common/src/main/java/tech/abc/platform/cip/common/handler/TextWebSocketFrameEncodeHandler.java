package tech.abc.platform.cip.common.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 将json格式字符串编码为TextWebSocketFrame
 * @author wqliu
 * @date 2021-10-6
 **/
@Slf4j
public class TextWebSocketFrameEncodeHandler extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        log.info("发出消息：{}",msg);
        TextWebSocketFrame frame=new TextWebSocketFrame(msg);
        out.add(frame);

    }

}
