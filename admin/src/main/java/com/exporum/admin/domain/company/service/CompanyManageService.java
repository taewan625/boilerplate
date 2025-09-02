package com.exporum.admin.domain.company.service;

import com.exporum.admin.domain.company.mapper.CompanyManageMapper;
import com.exporum.admin.domain.brand.model.BrandDTO;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 14.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class CompanyManageService {

    private final CompanyManageMapper companyManageMapper;

    public void updateCompany(BrandDTO brandDTO) {
        if(!(companyManageMapper.updateCompany(brandDTO) > 0) ){
            throw new OperationFailException();
        }
    }
}
