package com.exporum.core.domain.mail.service;

import com.exporum.core.domain.mail.mapper.MailTemplateMapper;
import com.exporum.core.domain.mail.model.BadgeTemplateData;
import com.exporum.core.domain.mail.template.BadgeTemplate;
import com.exporum.core.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailTemplateMapper mailTemplateMapper;

    @Value("${resource.storage.url}")
    private String storageUrl;

    public BadgeTemplate getBadgeTemplate(String merchantUid) {
        BadgeTemplateData badgeTemplateData = Optional
                .ofNullable(mailTemplateMapper.getBadgeTemplateData(merchantUid))
                .orElseThrow(DataNotFoundException::new);

        return BadgeTemplate.builder()
                .orderNumber(badgeTemplateData.getOrderNumber())
                .badgeName(badgeTemplateData.getBadgeName())
                .firstName(badgeTemplateData.getFirstName())
                .lastName(badgeTemplateData.getLastName())
                .company(badgeTemplateData.getCompany())
                .country(badgeTemplateData.getCountry())
                .city(badgeTemplateData.getCity())
                .callingCode(badgeTemplateData.getCallingCode())
                .mobileNumber(badgeTemplateData.getMobileNumber())
                .email(badgeTemplateData.getEmail())
                .paidAt(badgeTemplateData.getPaidAt())
                .currency(badgeTemplateData.getCurrency())
                .amount(badgeTemplateData.getAmount())
                .jobTitle(badgeTemplateData.getJobTitle())
                .exhibitionVenue(badgeTemplateData.getExhibitionVenue())
                .exhibitionCity(badgeTemplateData.getExhibitionCity())
                .receiver(badgeTemplateData.getReceiver())
                .issueNumber(badgeTemplateData.getIssueNumber())
                .barcodePath(storageUrl+badgeTemplateData.getBarcodePath())
                .build();
    }
}
