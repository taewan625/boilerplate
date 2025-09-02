package com.exporum.client.domain.exhibit.service;

import com.exporum.client.domain.exhibit.mapper.ExhibitorMapper;
import com.exporum.client.domain.exhibit.model.*;
import com.exporum.core.domain.company.model.CompanyDTO;
import com.exporum.core.domain.company.service.CompanyService;
import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.service.StorageService;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */


@Service
@RequiredArgsConstructor
public class ExhibitorService {

    private final ExhibitorMapper exhibitorMapper;

    private final ExhibitionService exhibitionService;

    private final CompanyService companyService;

    private final StorageService storageService;


    @Value("${resource.storage.url}")
    private String storageUrl;

    @Value("${ncp.object-storage.path.exhibitor}")
    private String objectStoragePath;


    @Transactional
    public void createExhibitor(ExhibitorDTO exhibitor) {

       long companyId = companyService.insertCompany(CompanyDTO.builder()
               .countryId(exhibitor.getCountryId())
               .industryCode(exhibitor.getIndustryCode())
               .companyName(exhibitor.getCompanyName())
               .email(exhibitor.getEmail())
               .countryCallingNumber(exhibitor.getOfficeCallingNumber())
               .officeNumber(exhibitor.getOfficeNumber())
               .companyNameEn(exhibitor.getCompanyName())
               .companyAddress(exhibitor.getCompanyAddress())
               .homepage(exhibitor.getHomepage())
               .etcIndustry(exhibitor.getEtcIndustry())
               .build());

       exhibitor.setCompanyId(companyId);

        FileDTO file = storageService.ncpUpload(exhibitor.getFile(), objectStoragePath);

        exhibitor.setFileId(file.getId());
        long exhibitorId = this.insertExhibitor(exhibitor);

        this.insertExhibitorManager(ExhibitorManagerDTO.builder()
                        .exhibitorId(exhibitorId)
                        .managerName(exhibitor.getManagerName())
                        .department(exhibitor.getDepartment())
                        .email(exhibitor.getEmail())
                        .jobTitle(exhibitor.getJobTitle())
                        .callingNumber(exhibitor.getManagerCallingNumber())
                        .mobileNumber(exhibitor.getMobileNumber())
                .build());

    }

    public void insertExhibitorInquiry(ExhibitorInquiryDTO exhibitorInquiry) {
        if(!(exhibitorMapper.insertExhibitorInquiry(exhibitorInquiry) > 0)) {
            throw new OperationFailException();
        }
    }


    public Exhibitor getExhibitors(SearchExhibitor searchExhibitor) {
        searchExhibitor.setPages(exhibitorMapper.getExhibitorCount(searchExhibitor));

        return Exhibitor.builder()
                .exhibitionYears(exhibitionService.getExhibitionYears())
                .pageable(searchExhibitor)
                .exhibitors(exhibitorMapper.getExhibitorList(searchExhibitor, storageUrl))
                .build();
    }

    public ExhibitorDetail getExhibitor(long id) {
        return Optional.ofNullable(exhibitorMapper.getExhibitor(id, storageUrl)).orElseThrow(() -> new DataNotFoundException("Exhibitor not found"));
    }


    private long insertExhibitor(ExhibitorDTO exhibitor) {
        if(!(exhibitorMapper.insertExhibitor(exhibitor) > 0)) {
            throw new OperationFailException();
        }

        return exhibitor.getId();
    }

    private void insertExhibitorManager(ExhibitorManagerDTO manager) {
        if(!(exhibitorMapper.insertExhibitorManager(manager) > 0)) {
            throw new OperationFailException();
        }
    }


}
