package tech.abc.platform.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.abc.platform.boot.PlatformBootApplication;
import tech.abc.platform.support.service.SerialNoService;


/**
 * @author wqliu
 * @date 2023-05-25
 */
@SpringBootTest(classes = PlatformBootApplication.class)
@Slf4j
class SerialNoServiceTest {
    @Autowired
    private SerialNoService serialNoService;

    @Test
    void generateSingleNo() {

        String result = serialNoService.generateSingle("contract");
        log.info(result);

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