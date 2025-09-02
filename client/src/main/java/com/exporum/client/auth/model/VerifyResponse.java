package com.exporum.client.auth.model;

import com.exporum.core.domain.question.model.RecentQuestion;
import com.exporum.core.domain.user.model.User;
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
public class VerifyResponse {


    private boolean alreadyRegistered;
    private User user;
    private RecentQuestion demographic;

    @Builder
    public VerifyResponse(boolean alreadyRegistered, User user, RecentQuestion demographic) {

        this.alreadyRegistered = alreadyRegistered;
        this.user = user;
        this.demographic = demographic;

    }

}
