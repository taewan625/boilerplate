package com.exporum.admin.domain.exhibition.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Getter
@Setter
public class TicketDTO {

    private long id;
    private long exhibitionId;

    private String startDate;
    private String endDate;
    private String typeCode;
    private String ticketName;
    private String ticketInfo;

    private boolean buying;

    private List<Price> prices;

    private long adminId;
}
