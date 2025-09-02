package com.exporum.core.domain.mail.worker;

import com.exporum.core.domain.mail.template.MailTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */

@Slf4j
public class MailEventQueue {

    private final Queue<MailTemplate> queue;

    private final int queueSize;

    private MailEventQueue(int queueSize) {
        this.queueSize = queueSize;
        this.queue = new LinkedBlockingQueue<>(queueSize);
    }

    public static MailEventQueue of(int queueSize) { return new MailEventQueue(queueSize); }

    public boolean offer(MailTemplate mailTemplate) {
        this.audit();
        return queue.offer(mailTemplate);
    }

    public MailTemplate poll() {
        this.audit();
        return queue.poll();
    }

    public int size() {return queue.size();}

    public int maxSize() {return queueSize;}

    public boolean isEmpty() {return queue.isEmpty();}

    private void audit(){
        log.info("Mail event queue =====> {} / {}", this.size(), this.maxSize());
    }


}
