package com.exporum.core.domain.mail.worker;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */
@Component
public class MailEventQueueConfiguration {

    @Bean
    public MailEventQueue getMailEventQueue() {
        return MailEventQueue.of(10000);
    }

}
