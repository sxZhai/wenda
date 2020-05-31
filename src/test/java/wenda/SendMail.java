package wenda;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import wenda.service.SendMailService;

import javax.annotation.Resource;

//@SpringBootTest
public class SendMail {
    @Resource
    private SendMailService mailService;

    @Resource
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("2621519656@qq.com","这是一封自动发送的邮件","爱你哦=-=");
    }

}
