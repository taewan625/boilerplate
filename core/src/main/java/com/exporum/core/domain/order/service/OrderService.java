package com.exporum.core.domain.order.service;

import com.exporum.core.domain.order.enums.OrderStatus;
import com.exporum.core.domain.order.mapper.OrderMapper;
import com.exporum.core.domain.order.model.Order;
import com.exporum.core.domain.order.model.OrderInfo;
import com.exporum.core.domain.order.model.PreparePaymentInfo;
import com.exporum.core.domain.ticket.model.Ticket;
import com.exporum.core.domain.ticket.service.TicketService;
import com.exporum.core.exception.OperationFailException;
import com.exporum.core.helper.VerificationHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 6.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    private final HttpServletRequest request;

    private final TicketService ticketService;

    public Order getOrder(String merchantUid){
        return orderMapper.getOrder(merchantUid);
    }

    @Transactional
    public void updateOrderStatus(String merchantUid, OrderStatus orderStatus) {
        if(!(orderMapper.updateOrderStatus(merchantUid, orderStatus.getStatus()) > 0)){
            throw new OperationFailException();
        }
    }

    @Transactional
    public PreparePaymentInfo insertOrder(OrderInfo orderInfo) {

        Ticket ticket = ticketService.getTicket(orderInfo.getTicketId());

        Order order = Order.builder()
                .merchantUid(this.getOrderNumber(ticket))
                .userId(orderInfo.getUserId())
                .ticketId(ticket.getId())
                .currencyCode(orderInfo.getCurrency())
                .verifyCode(VerificationHelper.getCode())
                .quantity(orderInfo.getTicketQuantity())
                .amount(ticket.getPriceByCurrency(orderInfo.getCurrency())*orderInfo.getTicketQuantity())
                //.amount(ticket.getPrice()*orderInfo.getTicketQuantity())
                .orderStatus(OrderStatus.PAYMENT_PENDING.getStatus())
                .ip(request.getRemoteAddr())
                .referer(request.getHeader("Referer"))
                .userAgent(request.getHeader("User-Agent"))
                .firstName(orderInfo.getFirstName())
                .lastName(orderInfo.getLastName())
                .company(orderInfo.getCompany())
                .jobTitle(orderInfo.getJobTitle())
                .countryId(orderInfo.getCountryId())
                .callingCode(orderInfo.getCallingCode())
                .mobileNumber(orderInfo.getMobileNumber())
                .build();


        if (!(orderMapper.insertOrder(order) > 0)) {
            throw new OperationFailException();
        }

        return PreparePaymentInfo.builder()
                .merchantId(order.getMerchantUid())
                .ticketName(ticket.getTicketInfo())
                .ticketQuantity(order.getQuantity())
                .amount(order.getAmount())
                .currency(order.getCurrencyCode())
                .build();
    }

    //주문번호 생성
    private String getOrderNumber(Ticket ticket) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = formatter.format(new Date());

        return String.format("WOC-%s-%s",date,this.generateRandomUppercase(5));
    }


    private String generateRandomUppercase(int length) {
        StringBuilder result = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // Generate a random uppercase letter (ASCII 'A' to 'Z')
            char randomChar = (char) ('A' + random.nextInt(26));
            result.append(randomChar);
        }
        return result.toString();
    }

}
