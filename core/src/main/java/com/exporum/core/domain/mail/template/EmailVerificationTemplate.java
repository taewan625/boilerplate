package com.exporum.core.domain.mail.template;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 7.
 * @description :
 */

@Getter
@Setter
public class EmailVerificationTemplate implements MailTemplate{

    private String code;

    private String city;

    private String receiver;

    private String type;

    private boolean individual;

    private boolean advertising;

    @Builder
    public EmailVerificationTemplate(String code, String city, String receiver) {
        this.code = code;
        this.city = city;
        this.receiver = receiver;
        this.type = "R";
        this.individual = true;
        this.advertising = false;
    }

    @Override
    public Context getContext() {
        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("city", city);
        return context;
    }

    @Override
    public String getTitle() {
        return String.format("[World of Coffee %s] Email Verification Confirmation", this.city);
    }

    @Override
    public String getTemplatePath() {
        return "mail/EmailVerification";
    }

    @Override
    public String getReceiver() {
        return this.receiver;
    }

    @Override
    public boolean getIndividual() {
        return this.individual;
    }

    @Override
    public boolean getAdvertising() {
        return this.advertising;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public MultipartFile getMultipartFile() {
        return null;
    }
}
