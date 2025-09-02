package com.exporum.client.domain.about.service;

import com.exporum.client.domain.about.mapper.ContactMapper;
import com.exporum.client.domain.about.model.ContactDTO;
import com.exporum.core.domain.code.service.CodeService;
import com.exporum.core.domain.mail.service.NCPMailer;
import com.exporum.core.domain.mail.template.ContactusTemplate;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactMapper contactMapper;

    private final CodeService codeService;

    @Value("${ncp.mail.sender-email}")
    private String sender;

    public void insertContact(ContactDTO contact) throws OperationFailException {
        if(!(contactMapper.insertContact(contact) > 0)){
            throw new OperationFailException("Insert contact failed");
        }

        NCPMailer.getInstance().enqueueMail(ContactusTemplate.builder()
                .companyName(contact.getCompanyName())
                .inquiryType(codeService.getCode(contact.getContactorCode()).getCodeName())
                .massageAbout(codeService.getCode(contact.getMessageAboutCode()).getCodeName())
                .email(contact.getEmail())
                .subject(contact.getTitle())
                .message(contact.getContent())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .receiver(this.sender)
                .build());

    }
}
