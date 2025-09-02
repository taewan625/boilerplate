package com.exporum.client.domain.notice.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 3.
 * @description :
 */

@Getter
@Setter
public class SubscribeDTO {

    @NotBlank(message = "is a required field.")
    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String firstName;

    @NotBlank(message = "is a required field.")
    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String lastName;

    @NotBlank(message = "is a required field.")
    @Size(max = 50, message = "Must be 50 characters or fewer.")
    @Email(message = "Invalid email format.")
    private String email;

    @AssertTrue(message = "You must accept the terms and conditions.")
    private boolean accepted;

}
