package com.exporum.admin.domain.payment.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 10.
 * @description :
 */

@Getter
@Setter
public class Payment {
    private long id;
    private String merchantUid;
    private String impUid;
    private String cardName;
    private String paidAmount;
    private String paidAt;
    private String startedAt;
    private String failedAt;
    private String failedReason;
    private String canceledAt;
    private String status;
    private String device;
}
