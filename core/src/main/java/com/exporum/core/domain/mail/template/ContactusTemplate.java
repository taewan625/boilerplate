package com.exporum.core.domain.mail.template;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 5.
 * @description :
 */

@Getter
@Setter
public class ContactusTemplate implements MailTemplate {
    private String companyName;
    private String email;
    private String inquiryType;
    private String massageAbout;
    private String subject;
    private String message;
    private String firstName;
    private String lastName;


    private String receiver;
    private String type;
    private boolean individual;
    private boolean advertising;


    @Builder
    public ContactusTemplate(String companyName, String email, String inquiryType, String massageAbout, String subject, String firstName, String lastName, String message, String receiver){
        this.companyName = companyName;
        this.email = email;
        this.inquiryType = inquiryType;
        this.massageAbout = massageAbout;
        this.subject = subject;
        this.message = message;
        this.firstName = firstName;
        this.lastName = lastName;

        this.receiver = receiver;
        this.type = "R";
        this.individual = true;
        this.advertising = false;
    }

    @Override
    public Context getContext() {
        Context context = new Context();
        context.setVariable("companyName", this.companyName);
        context.setVariable("email", this.email);
        context.setVariable("inquiryType", this.inquiryType);
        context.setVariable("massageAbout", this.massageAbout);
        context.setVariable("subject", this.subject);
        context.setVariable("message", this.message);
        context.setVariable("firstName", this.firstName);
        context.setVariable("lastName", this.lastName);
        return context;
    }

    @Override
    public String getTitle() {
        return STR."[World of Coffee Jakarta][CS] \{this.subject}";
    }

    @Override
    public String getTemplatePath() {
        return "mail/Contactus";
    }

    @Override
    public String getReceiver() {
        return this.receiver;
    }

    @Override
    public String getType() {
        return this.type;
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
    public MultipartFile getMultipartFile() {
        return null;
    }
}
