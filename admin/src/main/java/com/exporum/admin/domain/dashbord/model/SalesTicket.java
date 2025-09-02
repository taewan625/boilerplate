package com.exporum.admin.domain.dashbord.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 25.
 * @description :
 */

@Getter
@Setter
public class SalesTicket {

    private String saleDate;
    private long ticketId;
    private String ticketName;
    private String ticketType;
    private int totalQuantity;
    private int totalSales;

}
