package com.exporum.core.domain.mail.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 9.
 * @description :
 */
@Setter
@Getter
public class NCPSendMail {
    //발송자 메일
    private String senderAddress;
    //메일 타이틀
    private String title;
    //메일 내용
    private String body;
    //수신자 리스트
    private List<Recipient> recipients;
    //일반(false), 개인(true)
    private boolean individual;
    //광고 메일여부 / 광고 (true), 일반(false)
    private boolean advertising;

    @Builder
    public NCPSendMail(String senderAddress, String title, String body, List<Recipient> recipients, boolean individual, boolean advertising) {
        this.senderAddress = senderAddress;
        this.title = title;
        this.body = body;
        this.recipients = recipients;
        this.individual = individual;
        this.advertising = advertising;
    }


//    {
//        "senderAddress":"no_reply@company.com",
//        "title":"${customer_name}님 반갑습니다. ",
//        "body":"귀하의 등급이 ${BEFORE_GRADE}에서 ${AFTER_GRADE}로 변경되었습니다.",
//            "recipients":[
//                    {
//                        "address":"hongildong@naver_.com",
//                            "name":"홍길동",
//                            "type":"R",
//                            "parameters":{
//                                "customer_name":"홍길동",
//                                "BEFORE_GRADE":"SILVER",
//                                "AFTER_GRADE":"GOLD"
//                           }
//                    },
//                    {
//                        "address":"chulsoo@daum_.net",
//                        "name":null,
//                            "type":"R",
//                            "parameters":{
//                            "customer_name":"철수",
//                                "BEFORE_GRADE":"BRONZE"
//                                ,"AFTER_GRADE":"SILVER"}
//                    }
//        ],
//        "individual":true,
//        "advertising":false
//    }
}
