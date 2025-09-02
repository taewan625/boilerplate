package com.exporum.core.domain.question.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 13.
 * @description :
 */

@Getter
@Setter
public class Question {

    private long id;
    private String orderId;
    private long userId;
    private String email;
    private String occupation;
    private String industry;
    private String experience;
    private String firstTimeAttending;
    private String authority;
    private String objective;
    private String ageGroup;
    private String interest;

    @Builder
    public Question(long id, String orderId, long userId, String email, String occupation, String industry,
                    String experience, String firstTimeAttending, String authority, String objective,
                    String ageGroup, String interest) {
        this.orderId = orderId;
        this.userId = userId;
        this.email = email;
        this.occupation = occupation;
        this.industry = industry;
        this.experience = experience;
        this.firstTimeAttending = firstTimeAttending;
        this.authority = authority;
        this.objective = objective;
        this.ageGroup = ageGroup;
        this.interest = interest;
    }
}
