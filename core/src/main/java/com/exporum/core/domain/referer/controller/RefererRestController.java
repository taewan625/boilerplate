package com.exporum.core.domain.referer.controller;

import com.exporum.core.domain.referer.model.ConnectionLog;
import com.exporum.core.domain.referer.service.RefererService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 6.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
@Slf4j
public class RefererRestController {

    private final RefererService refererService;

    @GetMapping("/referer")
    public void getAllHeaders(
            @RequestHeader(value = "X-Forwarded-For", required = false) String ip,
            @RequestHeader(value = "Referer", required = false) String referer,
            @RequestHeader(value = "User-Agent", required = false) String userAgent,
            @RequestHeader(value = "Host", required = false) String host
            ) {
        // Referer가 비어있지 않고, 우리 도메인이 아닌 경우만 기록
        log.info("Referer is null or empty {} ==> {} ==> userAgent:{}", ip , referer, userAgent);
        if (referer != null && !referer.isBlank() && !referer.startsWith("https://asia.worldofcoffee.org")) {
            refererService.insertReferer(ConnectionLog.builder()
                    .referer(referer)
                    .userAgent(userAgent)
                    .ip(ip)
                    .build());
        } else {
            log.info("Referer is null or empty {} ==> {} ==> userAgent:{}", ip , referer, userAgent);
        }
    }
}
