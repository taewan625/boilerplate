package com.exporum.client.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */


@Getter
@Setter
public class EmailRequest {

    @NotBlank(message = "is a required field.")
    @Email(message = "Invalid email format.")
    private String email;
}
