package tech.abc.platform.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.abc.platform.boot.PlatformBootApplication;
import tech.abc.platform.mail.service.MailService;


/**
 * @author wqliu
 * @date 2023-05-25
 */
@SpringBootTest(classes = PlatformBootApplication.class)
class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Test
    void sendTextMail() {
        mailService.sendTextMail("sealy321@126.com", "测试", "测试");
    }

    @Test
    void testSendTextMail() {
    }

    @Test
    void sendHtmlMail() {
    }

    @Test
    void testSendHtmlMail() {
    }
}