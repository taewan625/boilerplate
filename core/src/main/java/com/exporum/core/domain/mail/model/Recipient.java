package com.exporum.core.domain.mail.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 9.
 * @description :
 */

@Getter
@Setter
public class Recipient {
    //수신자 메일
    private String address;
    //수신자 명
    private String name;
    //수신자 유형 / 기본(R), 참조(C), 숨은참조(B)
    private String type;
    //치환 파라미터
    private Object parameters;

    @Builder
    public Recipient(String address, String name, String type, Object parameters) {
        this.address = address;
        this.name = name;
        this.type = type;
        this.parameters = parameters;
    }

}
