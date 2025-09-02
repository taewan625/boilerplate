package com.exporum.client.domain.exhibit.controller;

import com.exporum.client.domain.exhibit.model.EventSchedule;
import com.exporum.client.domain.exhibit.service.EventService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class EventRestController {

    private final EventService eventService;

    @Operation(summary = "전시 스케쥴 API", description = """
            전시 스케쥴 리스트를 가져온다.
           
                year (*): 검색 전시 년도
            
            ex : ?year=2025
                   
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(description = "저장 수행 상태 정보", oneOf = ContentsResponse.class)
            )),
            @ApiResponse(responseCode = "400",
                    content = @Content(
                            schema = @Schema(description = "저장 정보 유효성 검사 상태 정보", oneOf = OperationResponse.class)
                    )
            ), // vaildate error
            @ApiResponse(responseCode = "404"), // Not Found
    })
    @GetMapping("/events")
    public ResponseEntity<OperationResponse> getEvents(@RequestParam final int year) {

        List<EventSchedule> result = eventService.getEvents(year);

        return ResponseEntity.ok(new ContentsResponse<>(
                OperationStatus.SUCCESS,
                result)
        );
    }


}
