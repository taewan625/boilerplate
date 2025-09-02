package com.exporum.core.domain.company.service;

import com.exporum.core.domain.company.mapper.CompanyMapper;
import com.exporum.core.domain.company.model.CompanyDTO;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 17.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyMapper companyMapper;


    public long insertCompany(CompanyDTO company) {

        try {

            if(!(companyMapper.insertCompany(company) > 0)) {
                throw new OperationFailException("Insert company failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return company.getId();
    }
}
