package com.exporum.core.domain.mail.worker;

import com.exporum.core.domain.mail.template.MailTemplate;
import com.exporum.core.domain.mail.service.NCPMailer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueWorker {

    private final MailEventQueue mailEventQueue;

    private final NCPMailer ncpMailer;

    @Value("${application.mail.interval}")
    private long interval;

    private Instant lastTime = Instant.now();

    private static final int MAX_PARALLEL_MESSAGES = 5;
    private static final long RATE_LIMIT_INTERVAL_MS = 2000L;
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(MAX_PARALLEL_MESSAGES);

    @Async("MailAsyncTask")
    @Scheduled(fixedRate = 1000)
    public void mailer() {
        if(mailEventQueue.isEmpty()) return;

        Instant now = Instant.now();
        long intervalTime = Duration.between(lastTime, now).toMillis();

        if (intervalTime < interval) return;

        for (int i = 0; i < MAX_PARALLEL_MESSAGES; i++) {
            MailTemplate template = mailEventQueue.poll();

            if(template != null) {
                EXECUTOR.submit(() -> {
                    try {
                        ncpMailer.sendMail(template);  // 메일 전송
                    } catch (Exception e) {
                        log.error("메일 전송 실패: {}", e.getMessage(), e);
                    }
                });
            }
        }
    }


}
