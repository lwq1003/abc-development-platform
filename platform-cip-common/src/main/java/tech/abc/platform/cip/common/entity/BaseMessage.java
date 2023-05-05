package tech.abc.platform.cip.common.entity;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 消息基类
 *
 * @author wqliu
 * @date 2021-10-5
 */
@Data
public class BaseMessage {

    /**
     * 标识
     */
    private String id;

    /**
     * 主题
     */
    private String topic;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布者
     */
    private String publishAppCode;

    /**
     * 发布时间
     */
    private String publishTime;

    /**
     * 消息类型
     */
    private String messageType;


    public BaseMessage() {
        // 采用雪片算法生成唯一性标识
        this.setId(IdWorker.getIdStr());
        // 设置发布时间
        String publishTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        this.setPublishTime(publishTime);

    }

}
