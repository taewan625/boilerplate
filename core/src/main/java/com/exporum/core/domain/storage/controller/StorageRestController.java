package com.exporum.core.domain.storage.controller;

import com.exporum.core.domain.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class StorageRestController {

    private final StorageService storageService;

    @GetMapping("/ncp/files/{uuid}")
    public Callable<ResponseEntity<StreamingResponseBody>> download(@PathVariable String uuid) throws IOException {
        return () -> storageService.ncpDownload(uuid);
    }

}
