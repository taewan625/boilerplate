package com.exporum.client.domain.about.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 20.
 * @description :
 */

@Getter
@Setter
public class ContactDTO {

    @NotBlank(message = "Please select an option.")
    @Size(max = 50, message = "Must be 50 characters or fewer.")
    private String contactorCode;

    @NotBlank(message = "Please select an option.")
    @Size(max = 50, message = "Must be 50 characters or fewer.")
    private String messageAboutCode;

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

    @NotBlank(message = "is a required field.")
    @Size(max = 5000, message = "Must be 5000 characters or fewer.")
    private String content;

    private String title;

    private String companyName;

}
