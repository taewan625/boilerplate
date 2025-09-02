package com.exporum.core.domain.ticket.model;

import com.exporum.core.exception.DataNotFoundException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 9.
 * @description :
 */

@Getter
@Setter
public class Ticket {

    private long id;
    private long exhibitionId;
    private int year;
    private String countryCode;
    private String ticketType;
    private String ticketName;
    private String ticketInfo;
    private String startDate;
    private String endDate;
    private List<TicketPrice> prices;

    public int getPriceByCurrency(String currency) {
        if (prices == null || prices.isEmpty()) {
            throw new DataNotFoundException("Price not found");
        }

        // currency에 해당하는 TicketPrice 객체 찾기
        return prices.stream()
                .filter(ticketPrice -> ticketPrice.getCurrency().equalsIgnoreCase(currency))
                .map(TicketPrice::getPrice)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Price not found")); // 해당 currency가 없으면 null 반환
    }
}
