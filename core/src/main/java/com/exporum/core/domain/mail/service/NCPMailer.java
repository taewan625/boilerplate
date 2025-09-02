package com.exporum.core.domain.mail.service;

import com.exporum.core.domain.mail.model.NCPSendMail;
import com.exporum.core.domain.mail.model.Recipient;
import com.exporum.core.domain.mail.template.MailTemplate;
import com.exporum.core.domain.mail.worker.MailEventQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 7.
 * @description :
 */

@Slf4j
@Service
public class NCPMailer {

    @Getter
    private static NCPMailer instance;

    private final MailEventQueue mailEventQueue;

    private final TemplateEngine templateEngine;

    private final RestTemplate restTemplate;

    @Value("${ncp.access-key}")
    private String accessKey;

    @Value("${ncp.secret-key}")
    private String secretKey;

    @Value("${ncp.mail.sender-email}")
    private String sender;

    @Value("${ncp.mail.sender-name}")
    private String senderName;

    @Value("${ncp.mail.domain}")
    private String domain;

    @Value("${ncp.mail.url}")
    private String url;

    @Autowired
    protected NCPMailer(
            MailEventQueue mailEventQueue,
            TemplateEngine templateEngine,
            RestTemplate restTemplate
    ){
        NCPMailer.instance = this;
        this.mailEventQueue = mailEventQueue;
        this.templateEngine = templateEngine;
        this.restTemplate = restTemplate;
    }

    public void enqueueMail(MailTemplate mailTemplate) {
        mailEventQueue.offer(mailTemplate);
    }

    //@Transactional(readOnly = true)
    public void sendMail(MailTemplate mailTemplate) {

        try{
            String mailSender = this.sender;
            if(sender.equals(mailTemplate.getReceiver())){
                mailSender = "no-reply@exporum.com";
            }

            NCPSendMail mail = NCPSendMail.builder()
                    .senderAddress(mailSender)
                    .title(mailTemplate.getTitle())
                    .body(getMailTemplate(mailTemplate))
                    .recipients(List.of(
                            Recipient.builder()
                                    .address(mailTemplate.getReceiver())
                                    .type(mailTemplate.getType())
                                    .build()
                    ))
                    .individual(mailTemplate.getIndividual())
                    .advertising(mailTemplate.getAdvertising())
                    .build();

            HttpEntity<NCPSendMail> responseEntity = new HttpEntity<>(mail, this.getHeaders());


            String response = restTemplate.postForEntity(domain+url, responseEntity, String.class).getBody();
            log.info("mailer ===========> {}",mailTemplate.getTitle());
            log.info("Response ===========> {}",response);

        }catch (Exception e){
            log.error("Mail Send Error !!! ===========> {}", e.getMessage());
        }
    }


    private HttpHeaders getHeaders() throws NoSuchAlgorithmException, InvalidKeyException {

        String timestamp = String.valueOf(System.currentTimeMillis());


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
        headers.add("x-ncp-apigw-timestamp", timestamp);
        headers.add("x-ncp-iam-access-key", this.accessKey);
        headers.add("x-ncp-apigw-signature-v2", this.makeSignature(timestamp));
        headers.add("x-ncp-lang", Locale.US.toString());
        return headers;
    }

    private String makeSignature(String timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        // Define constants for space and newline
        final String SPACE = " ";
        final String NEW_LINE = "\n";
        final String METHOD = "POST";

        // Build the message to sign
        String message = METHOD + SPACE + url + NEW_LINE + timestamp + NEW_LINE + accessKey;

        // Create signing key
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        // Initialize HMAC with the signing key
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        // Generate HMAC signature
        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        // Encode the HMAC as Base64 and return
        return Base64.getEncoder().encodeToString(rawHmac);

    }



    private String getMailTemplate(MailTemplate mailTemplate) {
        return templateEngine.process(mailTemplate.getTemplatePath(), mailTemplate.getContext());
    }


}
