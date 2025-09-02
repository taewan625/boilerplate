package com.exporum.client.domain.notice.controller;

import com.exporum.client.domain.notice.model.BulletinBoardSystem;
import com.exporum.client.domain.notice.model.Post;
import com.exporum.client.domain.notice.model.SearchBBS;
import com.exporum.client.domain.notice.service.BBSService;
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
import org.springframework.web.bind.annotation.*;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 2.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class BBSRestController {

    private final BBSService bbsService;


    @Operation(summary = "게시판 API", description = """
            공지사항, 보도자료 리스트를 가져온다.
           
            bbsType (*):
            
                -> NOTICE : 공지사항
                
                -> PRESS : 보도 자료
                
            pageSize (*) default (10):
            
                -> 페이지 row 갯수
            
            page (*) default(1) :
            
                ->현재 페이지
                
            searchText : 
            
                -> 검색어가 필요하면
            
            ex : ?bbsType=NOTICE&pageSize=10&page=1&searchText=
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
    @GetMapping("/bbs")
    public ResponseEntity<OperationResponse> getBBS( @RequestParam(defaultValue = "1") final int page,
                                                     @RequestParam(defaultValue = "10") final int pageSize,
                                                     @RequestParam final String bbsType,
                                                     @RequestParam(required = false) final String searchText) {

        BulletinBoardSystem result = bbsService.getBBS(SearchBBS.builder()
                .page(page)
                .pageSize(pageSize)
                .bbsType(bbsType)
                .searchText(searchText)
                .build());

        return ResponseEntity.ok(new ContentsResponse<>(
                OperationStatus.SUCCESS,
                result)
        );
    }


    @Operation(summary = "게시글 조회", description = """
            게시글을 조회한다.
                /api/v1/bbs/2
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

    @GetMapping("/bbs/{id}")
    public ResponseEntity<OperationResponse> getBBS(@PathVariable final long id) {
        Post result = bbsService.getPost(id);

        return ResponseEntity.ok(new ContentsResponse<>(
                OperationStatus.SUCCESS,
                result)
        );
    }

}
