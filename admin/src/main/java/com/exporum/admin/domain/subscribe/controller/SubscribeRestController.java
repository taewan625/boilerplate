package com.exporum.admin.domain.subscribe.controller;

import com.exporum.admin.domain.subscribe.model.PageableSubscribe;
import com.exporum.admin.domain.subscribe.model.SubscribeDTO;
import com.exporum.admin.domain.subscribe.service.SubscribeService;
import com.exporum.core.enums.OperationStatus;
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
 * @date : 2025. 2. 17.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class SubscribeRestController {

    private final SubscribeService subscribeService;

    @PostMapping("/subscribe")
    public PageableSubscribe getPageableSubscribe(PageableSubscribe search) {
        subscribeService.getSubscribeList(search);
        return search;
    }

    @PutMapping("/subscribe/{id}")
    public ResponseEntity<OperationResponse> updateSubscribe(@PathVariable long id, @RequestBody SubscribeDTO subscribe) {
        subscribeService.updateSubscribe(id, subscribe);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @GetMapping("/subscribe/excel")
    public Callable<ResponseEntity<StreamingResponseBody>> excelDownloadSubscribe(HttpServletResponse response) throws IOException {
        return () -> subscribeService.excelDownload(response);
    }
}
