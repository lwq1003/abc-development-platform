package tech.abc.platform.mail.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.mail.config.MailConfig;
import tech.abc.platform.mail.entity.Mail;
import tech.abc.platform.mail.exception.MailExceptionEnum;
import tech.abc.platform.mail.service.MailService;

import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.List;


/**
 * 使用spring mail组件发送邮件的服务实现类
 *
 * @author wqliu
 * @date 2023-05-25
 */
@Service
public class SpringMailServiceImpl implements MailService {

    private static final String COMMA = ",";
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public void sendTextMail(String to, String subject, String content) {
        Mail mail = new Mail();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(content);
        sendTextMail(mail);
    }

    @Override
    public void sendTextMail(Mail mail) {
        try {
            // 设置对象
            SimpleMailMessage message = new SimpleMailMessage();
            BeanUtils.copyProperties(mail, message);
            // 发件人由后端指定
            message.setFrom(mailConfig.getSenderAddress());
            // 处理收件人
            String[] to = getArray(mail.getTo());
            if (to != null) {
                message.setTo(to);
            }
            // 处理抄送人
            String cc = mail.getCc();
            if (cc != null) {
                message.setCc(cc);
            }
            // 处理密送人
            String bcc = mail.getBcc();
            if (bcc != null) {
                message.setBcc(bcc);
            }
            // 发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            throw new CustomException(MailExceptionEnum.SEND_ERROR, e.getMessage());
        }
    }


    @Override
    public void sendHtmlMail(String to, String subject, String content) {

        Mail mail = new Mail();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(content);
        sendHtmlMail(mail);
    }

    @Override
    public void sendHtmlMail(Mail mail) {

        try {
            // 设置对象
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            // 发件人由后端指定
            messageHelper.setFrom(mailConfig.getSenderAddress());
            // 处理收件人
            String[] to = getArray(mail.getTo());
            if (to != null) {
                messageHelper.setTo(to);
            }
            // 处理抄送人
            String cc = mail.getCc();
            if (cc != null) {
                messageHelper.setCc(cc);
            }
            // 处理密送人
            String bcc = mail.getBcc();
            if (bcc != null) {
                messageHelper.setBcc(bcc);
            }
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getText(), true);

            // 附件处理
            List<String> attachmentList = mail.getAttachmentList();
            if (CollectionUtils.isNotEmpty(attachmentList)) {
                for (int i = 0; i < attachmentList.size(); i++) {
                    String filePath = attachmentList.get(i);
                    String filename = FilenameUtils.getName(filePath);
                    messageHelper.addAttachment(MimeUtility.encodeWord(filename), new FileDataSource(filePath));
                }
            }
            // 发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            throw new CustomException(MailExceptionEnum.SEND_ERROR, e.getMessage());
        }
    }


    /**
     * 将字符串转换为数组
     *
     * @param address
     * @return
     */
    private String[] getArray(String address) {
        if (StringUtils.isNotBlank(address)) {
            if (StringUtils.contains(address, COMMA)) {
                return address.split(COMMA);
            } else {
                return new String[]{address};
            }

        }
        return null;
    }
}
