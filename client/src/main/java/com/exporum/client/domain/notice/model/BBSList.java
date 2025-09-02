package com.exporum.client.domain.notice.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 2.
 * @description :
 */


@Getter
@Setter
public class BBSList {

    private long no;
    private long id;
    private String title;
    private String createdAt;
    private boolean isFixed;

}
