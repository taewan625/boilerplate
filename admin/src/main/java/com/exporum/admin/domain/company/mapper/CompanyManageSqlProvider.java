package com.exporum.admin.domain.company.mapper;

import com.exporum.admin.domain.brand.model.BrandDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 14.
 * @description :
 */
public class CompanyManageSqlProvider {

    public String updateCompany(BrandDTO brand) {
        return new SQL(){
            {
                UPDATE("company");
                SET("country_id = #{brand.countryId}");
                SET("industry_code = #{brand.industryCode}");
                SET("company_name = #{brand.companyName}");
                SET("company_name_en = #{brand.companyNameEn}");
                SET("email = #{brand.companyEmail}");
                SET("country_calling_num = #{brand.officeCallingCode}");
                SET("office_number = #{brand.officeNumber}");
                SET("company_address = #{brand.address}");
                SET("homepage = #{brand.homepage}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{brand.adminId}");
                WHERE("id = #{brand.companyId}");
            }
        }.toString();
    }
}
