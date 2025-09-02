package com.exporum.client.domain.exhibit.controller;

import com.exporum.client.domain.exhibit.model.ExhibitionYears;
import com.exporum.client.domain.exhibit.service.ExhibitionService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class ExhibitionRestController {

    private final ExhibitionService exhibitionService;

    @GetMapping("/exhibition/years")
    public ResponseEntity<OperationResponse> getExhibitionYears() {
        //TODO 개선: desc 내용도 같이 담아서 exhibition-list 뿌려주기 필요
        ExhibitionYears exhibitionYears = new ExhibitionYears(exhibitionService.getExhibitionYears());
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, exhibitionYears));
    }
}
