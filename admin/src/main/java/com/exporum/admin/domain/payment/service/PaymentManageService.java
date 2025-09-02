package com.exporum.admin.domain.payment.service;

import com.exporum.admin.domain.attendee.model.PageableAttendee;
import com.exporum.admin.domain.exhibition.model.TicketPaymentList;
import com.exporum.admin.domain.payment.mapper.PaymentManageMapper;
import com.exporum.admin.domain.payment.model.PageablePayment;
import com.exporum.admin.domain.payment.model.PaymentExcel;
import com.exporum.core.domain.excel.ExcelGenerator;
import com.exporum.core.domain.excel.OneSheetExcelGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class PaymentManageService {

    private final PaymentManageMapper paymentManageMapper;

    public void getPageablePayment(PageablePayment pageable) {

        long recordsTotal = paymentManageMapper.getPaymentCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(paymentManageMapper.getPaymentList(pageable));

    }

    public ResponseEntity<StreamingResponseBody> excelDownload(long exhibitionId, HttpServletResponse response) throws IOException {


        List<PaymentExcel> data = paymentManageMapper.getPaymentExcel(exhibitionId);

        ExcelGenerator<PaymentExcel> excelFile = new OneSheetExcelGenerator<>(data, PaymentExcel.class);
        StreamingResponseBody responseBody = outputStream -> excelFile.write(response.getOutputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + "payment.xlsx")
                .body(responseBody);
    }
}
