package com.exporum.admin.domain.subscribe.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */

@Getter
@Setter
public class SubscribeList {

    private long no;
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean subscribe;
    private String subscribeDate;
    private String updateAt;

}
