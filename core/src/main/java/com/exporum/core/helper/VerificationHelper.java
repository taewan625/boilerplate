package com.exporum.core.helper;

import java.util.Random;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */
public class VerificationHelper {

    public static String getCode(){
        Random random = new Random();
        return String.valueOf(random.nextInt(900000) + 100000); // 6자리 랜덤 숫자
    }
}
