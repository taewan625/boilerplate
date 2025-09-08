package com.exporum.admin.domain.v1.system.code.controller;

import com.exporum.admin.core.pagination.SearchRequest;
import com.exporum.admin.core.pagination.SearchResponse;
import com.exporum.admin.core.response.ContentsResponse;
import com.exporum.admin.domain.v1.system.code.model.CodeDetail;
import com.exporum.admin.domain.v1.system.code.service.CodeService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/system")
public class CodeRestController {
    private final MessageSource messageSource;
    private final CodeService codeService;

    @PostMapping("/code/parents")
    public ResponseEntity<ContentsResponse<SearchResponse<CodeDetail>>> selectParentCodeList( @RequestBody SearchRequest<String> searchRequest) {
        //페이징 정보 카운팅
        SearchResponse<CodeDetail> result = codeService.selectParentCodeList(searchRequest);
        return ResponseEntity.ok(ContentsResponse.success(messageSource.getMessage("read.success", null, LocaleContextHolder.getLocale()), result));
    }

}
