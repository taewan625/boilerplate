package com.exporum.core.domain.code.controller;

import com.exporum.core.domain.code.model.CodeList;
import com.exporum.core.domain.code.service.CodeService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class CodeRestController {

    private final CodeService codeService;


    @Operation(summary = "폼에 사용할 코드 리스트 API", description = """
            입력 폼에 사용할 코드 리스트를 불러오는 API
            
            setcode (*):
            
            
                user-register : 회원 가입 코드
                exhibitor-register : 참가기업 등록 코드
                contactus-register : Contactus 등록 코드
            
                -> 코드셋
   
                   ex ) 회원가입폼 : ?setcode=user-register
                   
                   
           
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

    // ⚠️ 운영 서버의 React homepage에 영향이 가지 않도록 임시 분기 처리한 엔드포인트
    // 👉 React homepage가 제거된 후에는 /code-set 내용을 아래 /code-set-new의 구현 내용으로 대체할 예정
    @GetMapping("/code-set")
    public ResponseEntity<OperationResponse> getCode(@RequestParam String setcode) {
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, codeService.findByCodeSet(setcode)));
    }

    // ✅ 최종 적용될 예정인 새로운 코드셋 조회 로직
    // React homepage 제거 이후, 기존 /code-set 엔드포인트를 이 내용으로 교체할 계획
    @GetMapping("/code-set-new")
    public ResponseEntity<OperationResponse> getCodeNew(@RequestParam String setcode) {
        return ResponseEntity.ok(codeService.findByCodeSet(setcode));

    }


}
