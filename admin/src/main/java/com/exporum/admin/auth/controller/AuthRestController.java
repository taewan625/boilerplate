package com.exporum.admin.auth.controller;

import com.exporum.admin.auth.model.ChangePassword;
import com.exporum.admin.auth.service.UserDetailsServiceImpl;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.OperationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class AuthRestController {

    private final UserDetailsServiceImpl userDetailsService;


    @PostMapping("/password")
    public ResponseEntity<OperationResponse> changePassword(@RequestBody ChangePassword changePassword, HttpServletRequest request) {
        userDetailsService.changePassword(changePassword, request);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }
}
