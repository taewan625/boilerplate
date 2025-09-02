package com.exporum.client.auth.controller;

import com.exporum.client.auth.model.EmailRequest;
import com.exporum.client.auth.model.VerifyEmail;
import com.exporum.client.auth.model.VerifyResponse;
import com.exporum.client.auth.service.AuthService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class AuthRestController {

    private final AuthService authService;

    @Operation(summary = "이메일 인증번호 생성 및 메일 발송", description = """
           
           이메일 인증번호를 생성하고 인증번호가 메일로 발송 된다.
           
           RequestBody:
           
                {
                    "email": 이메일 (test@test.com)
                }
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(
                    schema = @Schema(description = "인증번호 생성 및 메일 발송 완료", oneOf = OperationResponse.class)
            )),
            @ApiResponse(responseCode = "400",
                    content = @Content(
                            schema = @Schema(description = "이메일 유효성 검사 상태", oneOf = ValidatedResponse.class)
                    )
            ), // vaildate error
            @ApiResponse(responseCode = "404"), // Not Found
    })
    @PostMapping("/email-verification-code")
    public ResponseEntity<OperationResponse> sandVerificationMail(@RequestBody @Valid EmailRequest emailRequest) {
        authService.sendEmailVerificationCode(emailRequest);
        return new ResponseEntity<>(new OperationResponse(OperationStatus.SUCCESS), HttpStatus.CREATED);
    }


    @Operation(summary = "이메일 인증", description = """
           이메일 인증
           
           RequestBody:
           
                {
                    "email": 이메일 (test@test.com)
                    "code" : 인증코드 (123456)
                }
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(description = "인증 완료", oneOf = OperationResponse.class)
            )),
            @ApiResponse(responseCode = "401",
                    content = @Content(
                            schema = @Schema(description = "이메일 인증 실패", oneOf = OperationResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404"), // Not Found
    })
    @PostMapping("/verify-email")
    public ResponseEntity<OperationResponse> verifyEmail(@RequestBody VerifyEmail verifyEmail) {
        VerifyResponse result = authService.verifyEmail(verifyEmail);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, result));
    }



}
