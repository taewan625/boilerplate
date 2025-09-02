package com.exporum.admin.domain.brand.mapper;

import com.exporum.admin.domain.brand.model.BrandDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 14.
 * @description :
 */
public class BrandManagerSqlProvider {

    public String updateBrandManager(long exhibitorId, BrandDTO brand) {
        return new SQL(){
            {

                UPDATE("exhibitor_manager");
                SET("manager_name = #{brand.managerName}");
                SET("job_title = #{brand.jobTitle}");
                SET("email = #{brand.managerEmail}");
                SET("country_calling_number = #{brand.managerCallingCode}");
                SET("mobile_number = #{brand.mobileNumber}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{brand.adminId}");
                WHERE("exhibitor_id = #{exhibitorId}");

            }
        }.toString();
    }
}
