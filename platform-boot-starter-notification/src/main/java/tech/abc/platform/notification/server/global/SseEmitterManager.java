package tech.abc.platform.notification.server.global;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import tech.abc.platform.notification.entity.SystemMessage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * SSE连接管理器
 *
 * @author wqliu
 * @date 2025-10-28
 */
@Slf4j
public class SseEmitterManager {

    /**
     * 用户连接集合
     */
    private static Map<String, SseEmitter> userEmitterMap = new ConcurrentHashMap<>();

    /**
     * 添加用户连接
     *
     * @param account 账号
     * @param emitter SSE连接对象
     */
    public static void addEmitter(String account, SseEmitter emitter) {
        // 存储连接
        userEmitterMap.put(account, emitter);
        log.info(JSON.toJSONString(userEmitterMap));

        // 监听连接关闭事件，移除无效连接（避免内存泄漏）
        emitter.onCompletion(() -> {
            log.info("用户{}断开SSE", account);
            userEmitterMap.remove(account);
        });
        emitter.onTimeout(() -> {
            log.warn("用户{}连接SSE超时", account);
            userEmitterMap.remove(account);
        });
        emitter.onError((e) -> {
            log.error("用户{}连接SSE出错", account, e);
            userEmitterMap.remove(account);
        });
    }

    /**
     * 移除用户连接
     *
     * @param account 账号
     */
    public static void removeEmitter(String account) {
        userEmitterMap.remove(account);
    }

    /**
     * 获取用户的SSE连接
     *
     * @param account 账号
     * @return 该用户的SseEmitter（可能为null，代表用户未连接）
     */
    public static SseEmitter getEmitter(String account) {
        return userEmitterMap.get(account);
    }


    public static void sendMessage(SystemMessage entity) {

        SseEmitter emitter = getEmitter(entity.getReceiver());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(JSON.toJSONString(entity)));
            } catch (IOException e) {
                log.error("发送内部消息给用户{}失败", entity.getReceiver(), e);
            }
        }


    }
}
