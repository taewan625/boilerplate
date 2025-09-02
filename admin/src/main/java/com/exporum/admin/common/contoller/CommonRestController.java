package com.exporum.admin.common.contoller;

import com.exporum.admin.common.service.CommonService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 27.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class CommonRestController {

    private final CommonService commonService;


    @GetMapping("/select-code/{parentCode}")
    public ResponseEntity<OperationResponse>getSelectOption(@PathVariable String parentCode){
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, commonService.getSelectOption(parentCode)));
    }

    @GetMapping("/select-ticket/{exhibitionId}")
    public ResponseEntity<OperationResponse> getSelectOption(@PathVariable long exhibitionId){
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, commonService.getTicketSelectOption(exhibitionId)));
    }

}
