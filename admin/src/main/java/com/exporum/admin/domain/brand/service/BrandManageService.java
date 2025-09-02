package com.exporum.admin.domain.brand.service;

import com.exporum.admin.domain.brand.model.*;
import com.exporum.admin.domain.company.service.CompanyManageService;
import com.exporum.admin.domain.brand.mapper.BrandManageMapper;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.domain.excel.ExcelGenerator;
import com.exporum.core.domain.excel.OneSheetExcelGenerator;
import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.service.StorageService;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 12.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class BrandManageService {

    private final BrandManageMapper brandManageMapper;

    private final CompanyManageService companyManageService;

    private final StorageService storageService;

    @Value("${resource.storage.url}")
    private String storageUrl;

    @Value("${ncp.object-storage.path.exhibitor}")
    private String objectStoragePath;

    public void getPageableExhibitor(PageableBrand pageable) {

        long recordsTotal = brandManageMapper.getBrandCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(brandManageMapper.getBrandList(pageable));
    }


    public void getLogoDownLoad(){
        List<BrandLogo> logos = brandManageMapper.getBrandLogoList();

        logos.forEach(logo->{
            try {
                storageService.downloadFile(logo.getUrl(), logo.getFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public Brand getExhibitor(long id) {
        return Optional.ofNullable(brandManageMapper.getBrand(id, storageUrl)).orElseThrow(DataNotFoundException::new);
    }

    public ResponseEntity<StreamingResponseBody> excelDownload(long exhibitionId, HttpServletResponse response) throws IOException {

        List<BrandExcel> data = brandManageMapper.getBrandExcel(exhibitionId);

        ExcelGenerator<BrandExcel> excelFile = new OneSheetExcelGenerator<>(data, BrandExcel.class);
        StreamingResponseBody responseBody = outputStream -> excelFile.write(response.getOutputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + "brand.xlsx")
                .body(responseBody);
    }


    @Transactional
    public void updateApprove(long id, BrandDTO exhibitor) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        exhibitor.setAdminId(adminId);
        if(!(brandManageMapper.updateApprove(id, exhibitor) > 0)) {
            throw new OperationFailException();
        }
    }

    @Transactional
    public void updateExhibitorProcess(long id, BrandDTO exhibitor) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        exhibitor.setAdminId(adminId);

        if (exhibitor.getFile() != null) {
            FileDTO file = storageService.ncpUpload(exhibitor.getFile(), objectStoragePath);
            exhibitor.setFileId(file.getId());
        }

        companyManageService.updateCompany(exhibitor);
        this.updateExhibitor(id, exhibitor);
        this.updateExhibitorManager(id, exhibitor);

    }

    private void updateExhibitor(long id, BrandDTO exhibitor) {
        if(!(brandManageMapper.updateBrand(id, exhibitor)>0)){
            throw new OperationFailException();
        }
    }

    private void updateExhibitorManager(long id, BrandDTO exhibitor) {
        if(!(brandManageMapper.updateBrandManager(id, exhibitor)>0)){
            throw new OperationFailException();
        }
    }
}
