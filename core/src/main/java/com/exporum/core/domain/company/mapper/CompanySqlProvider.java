package com.exporum.core.domain.company.mapper;

import com.exporum.core.domain.company.model.CompanyDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 17.
 * @description :
 */


public class CompanySqlProvider {

    public String insertCompany(CompanyDTO company){
        return new SQL(){
            {
                INSERT_INTO("company");
                VALUES("country_id", "#{company.countryId}");
                VALUES("industry_code", "#{company.industryCode}");
                VALUES("company_name", "#{company.companyName}");
                VALUES("email", "#{company.email}");
                VALUES("country_calling_num", "#{company.countryCallingNumber}");
                VALUES("office_number", "#{company.officeNumber}");
                VALUES("company_name_en", "#{company.companyNameEn}");
                VALUES("company_address", "#{company.companyAddress}");
                VALUES("homepage", "#{company.homepage}");
                VALUES("etc_industry", "#{company.etcIndustry}");
                VALUES("created_at", "sysdate()");

            }
        }.toString();
    }
}
