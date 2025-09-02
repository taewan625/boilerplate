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
public class RemindInvitationTemplate implements MailTemplate {
    private String mailType;

    private String barcodePath;
    private String barcode;
    private String email;
    private String name;
    private String company;
    private String jobTitle;
    private String country;
    private String city;
    private String phoneNumber;
    private String receiver;
    private String type;
    private boolean individual;
    private boolean advertising;


    @Builder
    public RemindInvitationTemplate(String barcodePath, String barcode, String email, String name, String company, String jobTitle,
                                    String country, String city, String phoneNumber, String receiver, String mailType) {


        this.mailType = mailType;

        this.barcodePath = barcodePath;
        this.barcode = barcode;

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
            return STR."[World of Coffee Jakarta 2025] \{company} Exhibitor Badge \{this.barcode} (Remind)";
        }

        return STR."Invitation to World of Coffee Jakarta 2025–\{this.name}-\{this.barcode} (Remind)";
    }

    @Override
    public String getTemplatePath() {
        if(mailType.equals(InvitationType.BADGE.getType())){
            return "mail/RemindCompanyBadge";
        }
        return "mail/RemindInvitation";
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
