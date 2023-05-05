package tech.abc.platform.cip.common.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import tech.abc.platform.cip.common.entity.BaseMessage;

import java.util.List;

/**
 * 将对象序列化为json格式字符串
 *
 * @author wqliu
 * @date 2021-10-6
 **/
public class JsonEncodeHandler extends MessageToMessageEncoder<BaseMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage msg, List<Object> out) throws Exception {

        if (msg instanceof BaseMessage) {
            out.add(JSONObject.toJSONString(msg));
        } else {
            out.add(msg);
        }
    }


}
