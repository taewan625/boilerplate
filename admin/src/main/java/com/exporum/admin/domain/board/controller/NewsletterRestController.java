package com.exporum.admin.domain.board.controller;

import com.exporum.admin.domain.board.model.*;
import com.exporum.admin.domain.board.service.NewsletterService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 8. 13.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class NewsletterRestController {

    private final NewsletterService newsletterService;

    @PostMapping("/newsletters")
    public PageableNewsletter getPageableNewsletter(PageableNewsletter search) {
        newsletterService.getPageableNewsletter(search);
        return search;
    }

    @GetMapping("/newsletters/{id}")
    public ResponseEntity<OperationResponse> getNewsletter(@PathVariable long id) {
        Newsletters result = newsletterService.getNewsletter(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, result));
    }

    @PostMapping("/newsletters/insert")
    public ResponseEntity<OperationResponse> createNewsletter(@Valid @ModelAttribute NewsletterDTO newsletter) {
        newsletterService.insertNewsletterProcess(newsletter);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PutMapping("/newsletters/{id}")
    public ResponseEntity<OperationResponse> updateNewsletter(@PathVariable long id,  @Valid @ModelAttribute NewsletterDTO newsletter) {
        newsletterService.updateNewsletterProcess(id, newsletter);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PutMapping("/newsletters/{id}/enabled")
    public ResponseEntity<OperationResponse> updateNewsletterEnabled(@PathVariable long id,  @Valid @RequestBody Newsletter newsletter) {
        newsletterService.updateNewsletterEnabled(id, newsletter);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @DeleteMapping("/newsletters/{id}")
    public ResponseEntity<OperationResponse> deleteNewsletter(@PathVariable long id) {
        newsletterService.deleteNewsletterProcess(id);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }
}
