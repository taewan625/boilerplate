//package com.exporum.core.domain.mail.service;
//
//import com.exporum.core.configuration.thymeleaf.ThymeleafConfiguration;
//import com.exporum.core.domain.mail.template.EmailVerificationTemplate;
//import com.exporum.core.domain.mail.worker.MailEventQueueConfiguration;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//
///**
// * @author: Lee Hyunseung
// * @date : 2025. 1. 7.
// * @description :
// */
//
//
//@SpringBootTest(classes = NCPMailer.class)
//@ContextConfiguration(classes = { ThymeleafConfiguration.class, MailEventQueueConfiguration.class  })
//public class NCPMailerTest {
//
//    @Autowired
//    private NCPMailer NCPMailer;
//
//
//
//    @Test
//    void sendMail() {
//
//
//
//        EmailVerificationTemplate emailVerificationTemplate =         EmailVerificationTemplate.builder()
//                .city("Jakarta")
//                .code("312555").build();
//
//
//
//
//        System.out.println(emailVerificationTemplate.getContext().getVariable("city"));
//
//
////        System.out.println(mailService.getMailTemplate(emailVerificationTemplate));
//
//
//    }
//
//
//}
