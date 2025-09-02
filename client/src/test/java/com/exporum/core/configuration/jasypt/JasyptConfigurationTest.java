//package com.exporum.core.configuration.jasypt;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.security.SecureRandom;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Base64;
//import java.util.Date;
//import java.util.Locale;
//
///**
// * @author: Lee Hyunseung
// * @date : 2024. 12. 19.
// * @description :
// */
//
//@SpringBootTest(classes = JasyptConfiguration.class)
//class JasyptConfigurationTest {
//
//    @Autowired
//    JasyptConfiguration jasyptConfiguration;
//
//    @Test
//    void jasyptStringEncryptor() {
//        String url = jasyptConfiguration.jasyptStringEncryptor().encrypt("jdbc:mariadb://localhost:3306/woc");
//        String user = jasyptConfiguration.jasyptStringEncryptor().encrypt("root");
//        String password = jasyptConfiguration.jasyptStringEncryptor().encrypt("pordu5-dirtux-jyzNex");
//
//
//        String resource = jasyptConfiguration.jasyptStringEncryptor().encrypt("https://kr.object.ncloudstorage.com/bucket-exporum-prod/");
//
//        String domain = jasyptConfiguration.jasyptStringEncryptor().encrypt("https://mail.apigw.ntruss.com");
//        String api =jasyptConfiguration.jasyptStringEncryptor().encrypt("/api/v1/mails");
//        String secret=jasyptConfiguration.jasyptStringEncryptor().encrypt("rdTxu6k5OUtNTnQxuX9AOg9Fe6ALtM2MsXLpgz0e");
//        String accessKey=jasyptConfiguration.jasyptStringEncryptor().encrypt("5gLJZ7WuP4YXkpBOt8HZ");
//        String sender=jasyptConfiguration.jasyptStringEncryptor().encrypt("info@worldofcoffee.asia");
//        String serdername=jasyptConfiguration.jasyptStringEncryptor().encrypt("worldofcoffee.asia");
//        String webhook=jasyptConfiguration.jasyptStringEncryptor().encrypt("https://api.worldofcoffee.asia/api/v1/woc/payment/webhook");
//
//        String key=jasyptConfiguration.jasyptStringEncryptor().encrypt("2740420220524777");
//        String keysec = jasyptConfiguration.jasyptStringEncryptor().encrypt("gByV7SwM7dttnilenDxvzEO2I1zMldCJ17T3sELJxof2VeLOJCD2ElwJIrYqABwXXBlFKgVPYCjQss95");
//
//
//
//
//        System.out.println("encrypted webhook==="+String.format("ENC(%s)",webhook));
////        System.out.println("encrypted user==="+String.format("ENC(%s)",user));
////        System.out.println("encrypted password==="+String.format("ENC(%s)",password));
//
//
//        //VxZVNo08hmCYl1N6W+PkqMyWix0XNezF+1GO7SsiPkRkk3MdWkZHf3Qo6KKPhITT
//    }
//
//    @Test
//    void dateTest(){
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDateTime = Instant.ofEpochSecond(0).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
//
//        System.out.println(formattedDateTime);
//    }
//
//
//    @Test
//    void localeTest(){
//        System.out.println("Locale kr :::::" + Locale.KOREA.getLanguage());
//        System.out.println("Locale en :::::" + Locale.US.toString());
//
//
//    }
//
//
//    @Test
//    void orderNumberTest(){
//
//        int year = 2024;
//        String country = "KR";
//        String type = "GNRL";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String date = formatter.format(new Date());
//        String encode = Base64.getEncoder().encodeToString(date.getBytes()).replace("=","");
//        //System.out.println(String.format("WOC-%s-%s",date,));
//    }
//
//
//    @Test
//    void generateSecretKey() throws Exception {
//        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//        keyGen.init(256, new SecureRandom());  // 256비트 길이의 키 생성
//        SecretKey secretKey = keyGen.generateKey();
//        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));  // Base64 인코딩된 문자열 반환
//    }
//
//
//    @Test
//    void dateFormatTest(){
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//
//
//        System.out.println(formatter.format(now));
//    }
//
//}