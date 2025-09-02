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
public class Admin extends BaseEntity {

    private String roleCode;
    private String departmentCode;
    private String email;
    private String password;
    private String adminName;
    private String phoneNumber;
    private boolean isInitialPassword;
    private String passwordExpireDate;
    private boolean isBlock;
    private boolean  isDeleted;
    private String lastLoginAt;

}
