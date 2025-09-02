package com.exporum.admin.domain.company.mapper;

import com.exporum.admin.domain.brand.model.BrandDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 14.
 * @description :
 */

@Mapper
public interface CompanyManageMapper {

    @UpdateProvider(type = CompanyManageSqlProvider.class, method = "updateCompany")
    int updateCompany(@Param("brand") BrandDTO exhibitor);
}
