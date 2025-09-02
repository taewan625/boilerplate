package com.exporum.admin.domain.board.model;

import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Getter
@Service
public class Contactus {

    private long id;
    private String inquiry;
    private String createdAt;
    private String contactor;
    private String company;
    private String name;
    private String email;
    private String title;
    private String message;

    private String adminName;
    private boolean answer;
    private String updatedAt;

}
