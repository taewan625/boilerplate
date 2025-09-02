package com.exporum.core.domain.mail.template;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 15.
 * @description :
 */

@Getter
@Setter
public class BadgeTemplate implements MailTemplate {

    private String orderNumber;
    private String badgeName;
    private String firstName;
    private String lastName;
    private String company;
    private String country;
    private String city;
    private String callingCode;
    private String mobileNumber;
    private String email;
    private String paidAt;
    private String currency;
    private int amount;
    private String jobTitle;
    private String exhibitionCity;
    private String exhibitionVenue;
    private String issueNumber;
    private String barcodePath;

    private String receiver;
    private String type;
    private boolean individual;
    private boolean advertising;


    @Builder
    public BadgeTemplate(String orderNumber, String badgeName, String firstName, String lastName, String company,
                         String country, String city, String callingCode, String mobileNumber, String email, String receiver, String paidAt,
                         String currency, int amount,String jobTitle, String exhibitionCity, String exhibitionVenue, String issueNumber, String barcodePath) {
        this.orderNumber = orderNumber;
        this.badgeName = badgeName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.country = country;
        this.city = city;
        this.callingCode = callingCode;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.paidAt = paidAt;
        this.currency = currency;
        this.amount = amount;
        this.jobTitle = jobTitle;
        this.exhibitionCity = exhibitionCity;
        this.exhibitionVenue = exhibitionVenue;
        this.issueNumber = issueNumber;
        this.barcodePath = barcodePath;

        this.receiver = receiver;
        this.type = "R";
        this.individual = true;
        this.advertising = false;
    }


    @Override
    public Context getContext() {
        Context context = new Context();
        context.setVariable("orderNumber", this.orderNumber);
        context.setVariable("badgeName", this.badgeName);
        context.setVariable("firstName", this.firstName);
        context.setVariable("lastName", this.lastName);
        context.setVariable("company", this.company);
        context.setVariable("country", this.country);
        context.setVariable("city", this.city);
        context.setVariable("callingCode", this.callingCode);
        context.setVariable("mobileNumber", this.mobileNumber);
        context.setVariable("email", this.email);
        context.setVariable("paidAt", this.paidAt);
        context.setVariable("currency", this.currency);
        context.setVariable("amount", this.amount);
        context.setVariable("barcode", this.issueNumber);
        context.setVariable("barcodePath", this.barcodePath);
        context.setVariable("jobTitle", this.jobTitle);


        return context;
    }

    @Override
    public String getTitle() {
        return STR."[World of Coffee \{this.exhibitionCity}] Badge Information for \{this.firstName + " "+ this.lastName}";
    }

    @Override
    public String getTemplatePath() {
        return "mail/Badge";
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
