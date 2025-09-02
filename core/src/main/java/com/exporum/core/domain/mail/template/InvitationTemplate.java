package com.exporum.core.domain.mail.template;

import com.exporum.core.enums.InvitationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 15.
 * @description :
 */

@Getter
@Setter
public class InvitationTemplate implements MailTemplate {
    private String mailType;

    private String barcodePath;
    private String email;
    private String name;
    private String company;
    private String jobTitle;
    private String country;
    private String city;
    private String phoneNumber;

    private String count;

    private String receiver;
    private String type;
    private boolean individual;
    private boolean advertising;




    @Builder
    public InvitationTemplate(String count, String barcodePath, String email, String name, String company, String jobTitle,
                              String country, String city, String phoneNumber, String receiver, String mailType) {

        this.count = count;
        this.mailType = mailType;

        this.barcodePath = barcodePath;
        this.email = email;
        this.name = name;
        this.company = company;
        this.jobTitle = jobTitle;
        this.country = country;
        this.city = city;
        this.phoneNumber = phoneNumber;


        this.receiver = receiver;
        this.type = "R";
        this.individual = true;
        this.advertising = false;
    }


    @Override
    public Context getContext() {
        Context context = new Context();

        context.setVariable("barcodePath", this.barcodePath);
        context.setVariable("email", this.email);
        context.setVariable("name", this.name);
        context.setVariable("company", StringUtils.hasText(this.company) ? this.company : "-");
        context.setVariable("jobTitle", this.jobTitle);
        context.setVariable("country", this.country);
        context.setVariable("city", this.city);
        context.setVariable("phone", this.phoneNumber);


        return context;
    }

    @Override
    public String getTitle() {

        if(mailType.equals(InvitationType.BADGE.getType())){
            return STR."[World of Coffee Jakarta 2025] \{company} Exhibitor Badge \{this.count}";
        }

        return STR."Invitation to World of Coffee Jakarta 2025 – \{this.name}";
    }

    @Override
    public String getTemplatePath() {
        if(mailType.equals(InvitationType.BADGE.getType())){
            return "mail/CompanyBadge";
        }
        return "mail/Invitation";
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
