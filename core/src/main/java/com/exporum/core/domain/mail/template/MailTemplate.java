package com.exporum.core.domain.mail.template;

import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 7.
 * @description :
 */
public interface MailTemplate {

    Context getContext();
    String getTitle();
    String getTemplatePath();
    String getReceiver();
    String getType();
    boolean getIndividual();
    boolean getAdvertising();
    MultipartFile getMultipartFile();

}
