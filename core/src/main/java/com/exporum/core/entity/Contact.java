package com.exporum.core.entity;

import com.exporum.core.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Getter
@Setter
public class Contact extends BaseEntity {
    private String contactorCode;
    private String messageAboutCode;
    private String firstName;
    private String lastName;
    private String email;
    private String content;
    private String title;
    private String companyName;
    private String isAnswer;
}
