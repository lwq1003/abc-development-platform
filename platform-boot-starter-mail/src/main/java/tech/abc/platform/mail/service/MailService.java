package tech.abc.platform.mail.service;

import tech.abc.platform.mail.entity.Mail;


/**
 * 邮件服务
 *
 * @author wqliu
 * @date 2023-05-25
 */
public interface MailService {

    /**
     * 发送纯文本邮件，传入参数
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendTextMail(String to, String subject, String content);

    /**
     * 发送纯文本邮件，传入对象,不可携带附件
     *
     * @param mail
     */
    void sendTextMail(Mail mail);


    /**
     * 发送Html邮件，传入参数
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);


    /**
     * 发送Html邮件，传入对象,可携带附件
     *
     * @param mail
     */
    void sendHtmlMail(Mail mail);
}
