package com.exporum.admin.auth.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 27.
 * @description :
 */

@Getter
@Setter
public class ChangePassword {

    private String password;
    private String newPassword;

}
