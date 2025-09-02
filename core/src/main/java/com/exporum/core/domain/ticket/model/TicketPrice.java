package com.exporum.core.domain.ticket.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 17.
 * @description :
 */

@Getter
@Setter
public class TicketPrice {

    private long id;
    private String currency;
    private int price;

}
