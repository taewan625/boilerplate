package com.exporum.client.domain.about.mapper;

import com.exporum.client.domain.about.model.ContactDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */


public class ContactSqlProvider {

    public String insertContact(ContactDTO contact) {
        return new SQL(){
            {
                INSERT_INTO("contact");
                VALUES("contactor_code", "#{contact.contactorCode}");
                VALUES("message_about_code", "#{contact.messageAboutCode}");
                VALUES("first_name", "#{contact.firstName}");
                VALUES("last_name", "#{contact.lastName}");
                VALUES("email", "#{contact.email}");
                VALUES("content", "#{contact.content}");
                VALUES("title", "#{contact.title}");
                VALUES("company_name", "#{contact.companyName}");
                VALUES("created_at", "sysdate()");
            }
        }.toString();
    }

}