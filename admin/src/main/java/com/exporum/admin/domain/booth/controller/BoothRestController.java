package com.exporum.admin.domain.booth.controller;

import com.exporum.admin.domain.booth.model.BoothRequest;
import com.exporum.admin.domain.booth.model.PageableBoothRequest;
import com.exporum.admin.domain.booth.service.BoothService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author: Lee Hyunseung
 * @date : 25. 5. 13.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class BoothRestController {
    private final BoothService boothService;

    @PostMapping("/booth-request")
    public PageableBoothRequest getPageableSubscribe(PageableBoothRequest search) {
        boothService.getBoothRequestList(search);
        return search;
    }

    @GetMapping("/booth-request/{id}")
    public ResponseEntity<OperationResponse> getBoothRequest(@PathVariable long id) {
        BoothRequest boothRequest = boothService.getBoothRequest(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, boothRequest));
    }

    /*
     * @Description: booth 상세 업데이트
     * */
    @PutMapping("/booth-request/{id}")
    public ResponseEntity<OperationResponse> updateBoothRequest(@PathVariable long id) {
        boothService.updateCheck(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS));
    }

    /*
    * @Description: 파일 다운로드
    * */
    @GetMapping("/booth-request/excel/{exhibitionId}")
    public Callable<ResponseEntity<StreamingResponseBody>> boothRequestExcelDownload(@PathVariable long exhibitionId, HttpServletResponse response) throws IOException {
        return () -> boothService.boothRequestExcelDownload(exhibitionId, response);
    }
}

