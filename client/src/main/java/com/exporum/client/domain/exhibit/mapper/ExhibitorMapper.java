package com.exporum.client.domain.exhibit.mapper;

import com.exporum.client.domain.exhibit.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */

@Mapper
public interface ExhibitorMapper {


    @SelectProvider(type = ExhibitorSqlProvider.class, method = "getExhibitorList")
    List<ExhibitorDetail> getExhibitorList(@Param("searchExhibitor") SearchExhibitor searchExhibitor, @Param("storageUrl") final String storageUrl);

    @SelectProvider(type = ExhibitorSqlProvider.class, method = "getExhibitorCount")
    long getExhibitorCount(@Param("searchExhibitor") SearchExhibitor searchExhibitor);

    @SelectProvider(type = ExhibitorSqlProvider.class, method = "getExhibitor")
    ExhibitorDetail getExhibitor(@Param("id") long id, @Param("storageUrl") final String storageUrl);

    @InsertProvider(type= ExhibitorSqlProvider.class, method = "insertExhibitor")
    @Options(useGeneratedKeys = true, keyProperty = "exhibitor.id", keyColumn = "id")
    int insertExhibitor(@Param("exhibitor") ExhibitorDTO exhibitor);

    @InsertProvider(type= ExhibitorSqlProvider.class, method = "insertExhibitorManager")
    int insertExhibitorManager(@Param("manager") ExhibitorManagerDTO manager);

    @InsertProvider(type= ExhibitorSqlProvider.class, method = "insertExhibitorInquiry")
    int insertExhibitorInquiry(@Param("inquiry") ExhibitorInquiryDTO inquiry);
}
