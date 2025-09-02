package com.exporum.admin.domain.brand.mapper;

import com.exporum.admin.domain.brand.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 12.
 * @description :
 */

@Mapper
public interface BrandManageMapper {

    @SelectProvider(type = BrandManageSqlProvider.class, method = "getBrandList")
    List<BrandList> getBrandList(@Param("search") PageableBrand search);

    @SelectProvider(type = BrandManageSqlProvider.class, method = "getBrandCount")
    long getBrandCount(@Param("search") PageableBrand search);

    @SelectProvider(type = BrandManageSqlProvider.class, method = "getBrand")
    Brand getBrand(@Param("id") long id, @Param("storageUrl") String storageUrl);

    @UpdateProvider(type = BrandManageSqlProvider.class, method = "updateApprove")
    int updateApprove(@Param("id") long id, @Param("exhibitor") BrandDTO exhibitor);

    @UpdateProvider(type = BrandManageSqlProvider.class, method = "updateBrand")
    int updateBrand(@Param("id") long id, @Param("brand") BrandDTO brand);

    @UpdateProvider(type = BrandManagerSqlProvider.class, method = "updateBrandManager")
    int updateBrandManager(@Param("exhibitorId") long id, @Param("brand") BrandDTO brand);

    @SelectProvider(type = BrandManageSqlProvider.class, method = "getBrandExcel")
    List<BrandExcel> getBrandExcel(@Param("exhibitionId") long exhibitionId);


    @SelectProvider(type = BrandManageSqlProvider.class, method = "getBrandLogoList")
    List<BrandLogo> getBrandLogoList();
}

