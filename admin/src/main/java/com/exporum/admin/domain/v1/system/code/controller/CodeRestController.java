package com.exporum.admin.domain.v1.system.code.controller;

import com.exporum.admin.core.pagination.SearchRequest;
import com.exporum.admin.core.pagination.SearchResponse;
import com.exporum.admin.domain.v1.system.code.model.CodeDetail;
import com.exporum.admin.domain.v1.system.code.service.CodeService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * CodeRestController.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since 2025. 9. 8. 최초 작성
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/system")
public class CodeRestController {
    private final CodeService codeService;

    @PostMapping("/code/parents")
    public ResponseEntity<OperationResponse> selectParentCodeList( @RequestBody SearchRequest<String> searchRequest) {
        //페이징 정보 카운팅
        SearchResponse<CodeDetail> result = codeService.selectParentCodeList(searchRequest);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, result));
    }

}
