package com.exporum.client.auth.service;

import com.exporum.client.auth.mapper.EmailVerificationMapper;
import com.exporum.client.auth.model.EmailRequest;
import com.exporum.client.auth.model.EmailVerification;
import com.exporum.client.auth.model.VerifyEmail;
import com.exporum.client.auth.model.VerifyResponse;
import com.exporum.client.domain.exhibit.model.Exhibition;
import com.exporum.client.domain.exhibit.service.ExhibitionService;
import com.exporum.client.exception.VerifyEmailException;
import com.exporum.core.domain.mail.service.NCPMailer;
import com.exporum.core.domain.mail.template.EmailVerificationTemplate;
import com.exporum.core.domain.question.service.QuestionService;
import com.exporum.core.domain.user.model.User;
import com.exporum.core.domain.user.service.UserService;
import com.exporum.core.exception.OperationFailException;
import com.exporum.core.helper.MessageHelper;
import com.exporum.core.helper.VerificationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmailVerificationMapper  emailVerificationMapper;

    private final ExhibitionService exhibitionService;
    
    private final UserService userService;
    
    private final QuestionService questionService;

    private final MessageHelper messageHelper;

    @Value("${verification.configuration.mail.expire}")
    private long expire;

    @Transactional
    public String insertEmailVerification(String email) throws OperationFailException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String code = VerificationHelper.getCode();

        if(!(emailVerificationMapper.insertEmailVerification(EmailVerification.builder()
                 .code(code)
                 .email(email)
                 .expiredAt(now.plusMinutes(expire).format(formatter))
                 .build()) > 0)){
            throw new OperationFailException();
        }
        return code;
    }

    @Transactional
    public void sendEmailVerificationCode(final EmailRequest emailRequest) throws OperationFailException {
        Exhibition exhibition = exhibitionService.getCurrentExhibition();

        String code = this.insertEmailVerification(emailRequest.getEmail());

        EmailVerificationTemplate emailVerificationTemplate = EmailVerificationTemplate.builder()
                .code(code)
                .city(exhibition.getCity())
                .receiver(emailRequest.getEmail())
                .build();

        NCPMailer.getInstance().enqueueMail(emailVerificationTemplate);
    }

    public VerifyResponse verifyEmail(VerifyEmail verifyEmail) throws VerifyEmailException {
        EmailVerification verification = Optional.ofNullable(emailVerificationMapper.getEmailVerification(verifyEmail))
                .orElseThrow(()-> new VerifyEmailException(messageHelper.getMessage("email.verification.failure")));

        // 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 만료 시간 확인
        LocalDateTime expiredAt = LocalDateTime.parse(verification.getExpiredAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (now.isAfter(expiredAt)) {
            throw new VerifyEmailException(messageHelper.getMessage("email.verification.failure"));
        }

        Optional<User> optionalUser = Optional.ofNullable(userService.getUserByEmail(verifyEmail.getEmail()));

        VerifyResponse verifyResponse;

        if(optionalUser.isPresent()){
            User user = optionalUser.get();

            verifyResponse = VerifyResponse.builder()
                    .alreadyRegistered(true)
                    .user(user)
                    .demographic(questionService.getRecentQuestion(user.getId()))
                    .build();
        }else {
            verifyResponse = VerifyResponse.builder()
                    .alreadyRegistered(false)
                    .build();
        }

        return verifyResponse;
    }


}
