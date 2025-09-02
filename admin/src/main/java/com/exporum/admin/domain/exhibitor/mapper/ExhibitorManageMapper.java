package com.exporum.admin.domain.exhibitor.mapper;

import com.exporum.admin.domain.exhibitor.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 14.
 * @description :
 */

@Mapper
public interface ExhibitorManageMapper {

    @SelectProvider(type = ExhibitorManageSqlProvider.class, method = "getExhibitor")
    Exhibitor getExhibitor(@Param("id") long id);

    @SelectProvider(type = ExhibitorManageSqlProvider.class, method = "getExhibitorList")
    List<ExhibitorList> getExhibitorList(@Param("search") PageableExhibitor search);

    @SelectProvider(type = ExhibitorManageSqlProvider.class, method = "getExhibitorCount")
    long getExhibitorCount(@Param("search") PageableExhibitor search);

    @UpdateProvider(type = ExhibitorManageSqlProvider.class, method = "updateBooth")
    int updateBooth(@Param("id") long id, @Param("exhibitor") ExhibitorDTO exhibitor );

    @InsertProvider(type = ExhibitorManageSqlProvider.class, method = "createExhibitor")
    int createExhibitor(@Param("exhibitor") ExhibitorDTO exhibitor);

    @UpdateProvider(type = ExhibitorManageSqlProvider.class, method = "updateExhibitor")
    int updateExhibitor(@Param("id") long id, @Param("exhibitor") ExhibitorDTO exhibitor);

    @UpdateProvider(type = ExhibitorManageSqlProvider.class, method = "deleteExhibitor")
    int deleteExhibitor(@Param("id") long id, @Param("adminId") long adminId);


    @SelectProvider(type = ExhibitorManageSqlProvider.class, method = "getExhibitorExcel")
    List<ExhibitorExcel> getExhibitorExcel(@Param("exhibitionId") long exhibitionId);

}
