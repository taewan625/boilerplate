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
public class Price {
    private long id;
    private long ticketId;
    private String currency;
    private int price;
}
