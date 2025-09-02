package com.exporum.admin.domain.exhibition.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 20.
 * @description :
 */

@Getter
@Setter
public class Ticket {

    private long id;
    private long exhibitionId;
    private String exhibitionName;
    private boolean buying;
    private String startDate;
    private String localStartDate;
    private String endDate;
    private String localEndDate;
    private String typeCode;
    private String typeName;
    private String ticketName;
    private String ticketInfo;

    private List<Price> prices;
    private List<TicketTotalAmount> amounts;
}
