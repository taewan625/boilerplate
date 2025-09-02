package com.exporum.admin.domain.booth.service;

import com.exporum.admin.domain.booth.mapper.BoothRequestMapper;
import com.exporum.admin.domain.booth.model.BoothRequest;
import com.exporum.admin.domain.booth.model.BoothRequestExcel;
import com.exporum.admin.domain.booth.model.PageableBoothRequest;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.domain.excel.ExcelGenerator;
import com.exporum.core.domain.excel.OneSheetExcelGenerator;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 25. 5. 13.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class BoothService {


    private final BoothRequestMapper boothRequestMapper;

    public BoothRequest getBoothRequest(long id){
        return Optional.ofNullable(boothRequestMapper.getBoothRequest(id)).orElseThrow(()->new DataNotFoundException("Booth Request Not Found")) ;
    }

    public void getBoothRequestList(PageableBoothRequest pageable){
        long recordsTotal = boothRequestMapper.getBoothRequestCount(pageable);
        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(boothRequestMapper.getBoothRequestList(pageable));
    }

    public void updateCheck(long id){
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        if(!(boothRequestMapper.updateCheck(id,adminId)>0)){
            throw new OperationFailException("Booth Update Failed");
        }
    }

    /*
     * @Description: 파일 다운로드
     * */
    public ResponseEntity<StreamingResponseBody> boothRequestExcelDownload(long exhibitionId, HttpServletResponse response) throws IOException {
        List<BoothRequestExcel> data = boothRequestMapper.boothRequestExcel(exhibitionId);

        ExcelGenerator<BoothRequestExcel> excelFile = new OneSheetExcelGenerator<>(data, BoothRequestExcel.class);
        StreamingResponseBody responseBody = outputStream -> excelFile.write(response.getOutputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + "boothRequest.xlsx")
                .body(responseBody);
    }
}
