package com.exporum.client.domain.about.controller;

import com.exporum.client.domain.about.model.ContactDTO;
import com.exporum.client.domain.about.service.ContactService;
import com.exporum.core.enums.OperationStatus;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description : 문의 관련 컨트롤러
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class ContactRestController {

    private final ContactService contactService;

    @Operation(summary = "Contact us 문의 내용 저장", description = """
            Contact us 문의내용을 저장한다. 
            
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
            @ApiResponse(responseCode = "404"), // Not Found
    })
    @PostMapping("/contact-us")
    public ResponseEntity<OperationResponse> insertContact(@Valid @RequestBody ContactDTO contactDTO) {
        contactService.insertContact(contactDTO);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }


}
