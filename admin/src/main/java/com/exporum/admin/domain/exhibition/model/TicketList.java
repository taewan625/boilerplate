package com.exporum.admin.domain.exhibition.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 20.
 * @description :
 */

@Getter
@Setter
public class TicketList {
    private long no;
    private long id;
    private String typeCode;
    private String typeName;
    private String ticketName;
    private String ticketInfo;
    private String startDate;
    private String localStartDate;
    private String endDate;
    private String localEndDate;
    private String currency;
    private int price;
    private boolean buying;
    private String createdAt;
}
