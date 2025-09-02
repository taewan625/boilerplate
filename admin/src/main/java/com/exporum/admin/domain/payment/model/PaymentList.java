package com.exporum.admin.domain.payment.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Getter
@Setter
public class PaymentList {

    private long no;
    private long id;
    private String name;
    private String email;
    private String merchantUid;
    private String impUid;
    private String status;
    private String orderName;
    private String method;
    private int amount;
    private String currency;
    private String statusApprovalDate;
}
