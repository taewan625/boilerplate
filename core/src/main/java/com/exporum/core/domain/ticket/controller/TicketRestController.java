package com.exporum.core.domain.ticket.controller;

import com.exporum.core.domain.ticket.model.Ticket;
import com.exporum.core.domain.ticket.service.TicketService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 13.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class TicketRestController {

    private final TicketService ticketService;

    @Operation(summary = "판매중인 티켓 리스트 API", description = """
            현재 판매중인 티켓 리스트
           
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(description = "저장 수행 상태 정보", oneOf = ContentsResponse.class)
            )),
            @ApiResponse(responseCode = "404"), // Not Found
    })
    @GetMapping("/sales-tickets")
    public ResponseEntity<OperationResponse> getTicketsForSale() {
        List<Ticket> result = ticketService.getTicketsForSale();
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS
                        ,result));
    }

}
