package com.exporum.client.domain.notice.controller;

import com.exporum.client.domain.notice.model.NewsletterDetail;
import com.exporum.client.domain.notice.model.Newsletters;
import com.exporum.client.domain.notice.model.SearchNewsletter;
import com.exporum.client.domain.notice.model.SubscribeDTO;
import com.exporum.client.domain.notice.service.NewsletterService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import com.exporum.core.response.ValidatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 3.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class NewsletterRestController {

    private final NewsletterService newsletterService;

    @Operation(summary = "Newsletter subscribe 저장", description = """
            뉴스레터 구독자 저장
            
            Request Body:
            
                {
                    "firstName" : "lee",
                    "lastName" : "hyunseung",
                    "email" : "hslee@exporum.com",
                    "accepted" : true
                }
            
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(description = "저장 수행 상태 정보", oneOf = OperationResponse.class)
            )),
            @ApiResponse(responseCode = "400",
                    content = @Content(
                            schema = @Schema(description = "저장 정보 유효성 검사 상태 정보", oneOf = ValidatedResponse.class)
                    )
            ), // vaildate error

            @ApiResponse(responseCode = "422",
                    content = @Content(
                            schema = @Schema(description = "호출은 되었으나 저장 실패 ", oneOf = OperationResponse.class)
                    )
            ), // insert Error
            @ApiResponse(responseCode = "404"), // Not Found
    })
    @PostMapping("/subscribe")
    public ResponseEntity<OperationResponse> insertSubscribe(@Valid @RequestBody SubscribeDTO subscribe) {
        newsletterService.insertSubscribe(subscribe);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    //뉴스레터 목록
    @GetMapping("/newsletter")
    public ResponseEntity<OperationResponse> getNewsletters(@RequestParam(defaultValue = "1") final int page,
                                                       @RequestParam(defaultValue = "10") final int pageSize,
                                                       @RequestParam(required = false) final String searchText) {
        Newsletters result = newsletterService.getNewsletters(SearchNewsletter.builder()
                .page(page)
                .pageSize(pageSize)
                .searchText(searchText)
                .build());

        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, result)
        );
    }

    //뉴스레터 상세 정보
    @GetMapping("/newsletter/{id}")
    public ResponseEntity<OperationResponse> getNewsletter(@PathVariable final long id) {
        NewsletterDetail newsletter = newsletterService.getNewsletter(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, newsletter));
    }


}
