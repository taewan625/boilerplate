package com.exporum.client.domain.exhibit.controller;

import com.exporum.client.domain.exhibit.service.ExhibitorService;
import com.exporum.client.domain.exhibit.model.*;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
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
 * @date : 2024. 12. 30.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class ExhibitorRestController {

    private final ExhibitorService exhibitorService;

    @Operation(summary = "참가 기업 리스트 API", description = """
            참가 기업 리스트를 가져온다.
           
            year (*):
            
                -> 전시년도

            pageSize (*) default (10):
            
                -> 페이지 row 갯수
            
            page (*) default(1) :
            
                ->현재 페이지
                
            searchText : 
            
                -> 검색어가 필요하면
            
            ex : ?year=2025&pageSize=10&page=1&searchText=
            
            ex : ?year=2024&pageSize=10&page=1
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
    @GetMapping("/exhibitor")
    public ResponseEntity<OperationResponse> getEvents(@RequestParam(defaultValue = "1") final int page,
                                                       @RequestParam(defaultValue = "20") final int pageSize,
                                                       @RequestParam final int year,
                                                       @RequestParam(required = false) final String searchText) {

        Exhibitor result = exhibitorService.getExhibitors(SearchExhibitor.builder()
                .page(page)
                .pageSize(pageSize)
                .year(year)
                .searchText(searchText)
                .build());

        return ResponseEntity.ok(new ContentsResponse<>(
                OperationStatus.SUCCESS,
                result)
        );
    }

    //참가 기업 상세 정보
    @GetMapping("/exhibitor/{id}")
    public ResponseEntity<OperationResponse> getExhibitor(@PathVariable final long id) {
        ExhibitorDetail result = exhibitorService.getExhibitor(id);
        return ResponseEntity.ok(new ContentsResponse<>(
                OperationStatus.SUCCESS,
                result)
        );
    }

    //현재 미사용
    @PostMapping(value= "/exhibitor")
    public ResponseEntity<OperationResponse> createExhibitor(@ModelAttribute final ExhibitorDTO exhibitor) {
        exhibitorService.createExhibitor(exhibitor);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    //참가 설문
    @PostMapping(value= "/exhibitor/inquiry")
    public ResponseEntity<OperationResponse> createExhibitorInquiry(@Valid @RequestBody final ExhibitorInquiryDTO inquiry) {
        exhibitorService.insertExhibitorInquiry(inquiry);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }


}
