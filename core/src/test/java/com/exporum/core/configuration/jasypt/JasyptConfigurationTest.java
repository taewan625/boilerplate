package com.exporum.core.configuration.jasypt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@SpringBootTest(classes = JasyptConfiguration.class)
class JasyptConfigurationTest {

    @Autowired
    JasyptConfiguration jasyptConfiguration;

    @Test
    void jasyptStringEncryptor() {
        String url = jasyptConfiguration.jasyptStringEncryptor().encrypt("jdbc:mariadb://localhost:3306/woc");
        String user = jasyptConfiguration.jasyptStringEncryptor().encrypt("root");
        String password = jasyptConfiguration.jasyptStringEncryptor().encrypt("2233");
        System.out.println("encrypted url :::::: " + url );
        System.out.println("encrypted name :::::: " + user );
        System.out.println("encrypted pass :::::: " + password );


        //VxZVNo08hmCYl1N6W+PkqMyWix0XNezF+1GO7SsiPkRkk3MdWkZHf3Qo6KKPhITT
    }

    @Test
    void generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256, new SecureRandom());  // 256비트 길이의 키 생성
        SecretKey secretKey = keyGen.generateKey();
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));  // Base64 인코딩된 문자열 반환
    }

}