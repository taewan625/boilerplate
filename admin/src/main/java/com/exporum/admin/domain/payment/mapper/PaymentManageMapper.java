package com.exporum.admin.domain.payment.mapper;

import com.exporum.admin.domain.payment.model.PageablePayment;
import com.exporum.admin.domain.payment.model.PaymentExcel;
import com.exporum.admin.domain.payment.model.PaymentList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Mapper
public interface PaymentManageMapper {

    @SelectProvider(type = PaymentManageSqlProvider.class, method = "getPaymentList")
    List<PaymentList> getPaymentList(@Param("search") PageablePayment search);

    @SelectProvider(type = PaymentManageSqlProvider.class, method = "getPaymentCount")
    long getPaymentCount(@Param("search") PageablePayment search);

    @SelectProvider(type = PaymentManageSqlProvider.class, method = "getPaymentExcel")
    List<PaymentExcel> getPaymentExcel(@Param("exhibitionId") long exhibitionId);

}
