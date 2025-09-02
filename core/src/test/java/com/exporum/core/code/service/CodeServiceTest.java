package com.exporum.core.code.service;

import com.exporum.core.domain.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */


@SpringBootTest(classes = {OrderService.class} )
class CodeServiceTest {
//
//    @Autowired
//    CodeService codeService;


    @Autowired
    OrderService   orderService;

//
//    @Test
//    void findByCode() {
//        String code =  "CONTACT-US-CODE";
//
//        List<CodeList> codeList = codeService.findByCode(code);
//
//        assert !codeList.isEmpty();
//    }
//
//


    @Test
    void orderNumber(){
    //    System.out.println(orderService.getOrderNumber());
    }

}