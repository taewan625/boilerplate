package com.exporum.admin.domain.board.controller;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

import com.exporum.admin.domain.board.model.Contactus;
import com.exporum.admin.domain.board.model.PageableContactus;
import com.exporum.admin.domain.board.service.ContactusService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class ContactusRestController {

    private final ContactusService contactusService;

    @PostMapping("/contactus")
    public PageableContactus getPageableContactus(PageableContactus search) {
        contactusService.getPageableContactus(search);
        return search;
    }

    @GetMapping("/contactus/{id}")
    public ResponseEntity<OperationResponse> getContactus(@PathVariable long id) {
        Contactus contactus = contactusService.getContactus(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, contactus));
    }

    @PutMapping("/contactus/{id}")
    public ResponseEntity<OperationResponse> updateReplied(@PathVariable long id) {
        contactusService.updatedReplied(id);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }
}
