package tech.popsoft.cip.client.sender.request.system;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.popsoft.cip.client.framework.sender.RequestMessageSender;

/**
 * 登录请求发送器
 *
 * @author wqliu
 * @date 2021-10-6 14:13
 **/
public class LoginRequestSender extends RequestMessageSender {


    public LoginRequestSender() {
        super("framework.login.request");

        // 使用账号密钥构建消息内容
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appCode", messageClientConfig.getAppCode());
        // 对密钥进行加密处理
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(messageClientConfig.getAppSecret());
        jsonObject.put("appSecret", password);
        super.setContent(jsonObject.toJSONString());

    }


}
