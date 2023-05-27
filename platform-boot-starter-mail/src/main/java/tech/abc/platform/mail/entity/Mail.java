package tech.abc.platform.mail.entity;

import lombok.Data;

import java.util.List;

/**
 * 邮件 实体对象
 *
 * @author wqliu
 * @date 2023-05-25
 */
@Data
public class Mail {

    /**
     * 发件人
     */
    private String from;
    /**
     * 收件人
     */
    private String to;
    /**
     * 抄送人
     */
    private String cc;
    /**
     * 密送人
     */
    private String bcc;

    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String text;

    /**
     * 附件路径
     */
    private List<String> attachmentList;


}
