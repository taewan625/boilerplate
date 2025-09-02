package com.exporum.admin.domain.subscribe.service;

import com.exporum.admin.domain.subscribe.mapper.SubscribeMapper;
import com.exporum.admin.domain.subscribe.model.PageableSubscribe;
import com.exporum.admin.domain.subscribe.model.SubscribeDTO;
import com.exporum.admin.domain.subscribe.model.SubscribeExcel;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.domain.excel.ExcelGenerator;
import com.exporum.core.domain.excel.OneSheetExcelGenerator;
import com.exporum.core.exception.OperationFailException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeMapper subscribeMapper;


    @Transactional
    public void updateSubscribe(long id, SubscribeDTO subscribe) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        if (!(subscribeMapper.updateSubscribe(id, subscribe) > 0)) {
            throw new OperationFailException("Update subscribe failed");
        }
    }


    public void getSubscribeList(PageableSubscribe pageable){
        long recordsTotal = subscribeMapper.getSubscribeCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(subscribeMapper.getSubscribeList(pageable));
    }

    public ResponseEntity<StreamingResponseBody> excelDownload(HttpServletResponse response) throws IOException {
        List<SubscribeExcel> data = subscribeMapper.getSubscribeExcel();

        ExcelGenerator<SubscribeExcel> excelFile = new OneSheetExcelGenerator<>(data, SubscribeExcel.class);
        StreamingResponseBody responseBody = outputStream -> excelFile.write(response.getOutputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + "subscribe.xlsx")
                .body(responseBody);
    }
}
