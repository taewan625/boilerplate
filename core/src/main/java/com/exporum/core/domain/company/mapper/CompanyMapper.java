package com.exporum.core.domain.company.mapper;

import com.exporum.core.domain.company.model.CompanyDTO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 17.
 * @description :
 */

@Mapper
public interface CompanyMapper {

    @InsertProvider(type=CompanySqlProvider.class, method = "insertCompany")
    @Options(useGeneratedKeys = true, keyProperty = "company.id", keyColumn = "id")
    int insertCompany(@Param("company") CompanyDTO company);
}
