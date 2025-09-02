package com.exporum.admin.domain.booth.mapper;

import com.exporum.admin.domain.booth.model.BoothRequest;
import com.exporum.admin.domain.booth.model.BoothRequestExcel;
import com.exporum.admin.domain.booth.model.PageableBoothRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 25. 5. 13.
 * @description :
 */

@Mapper
public interface BoothRequestMapper {

    @SelectProvider(type = BoothRequestSqlProvider.class, method = "getBoothRequest")
    BoothRequest getBoothRequest(@Param("id") long id);

    @SelectProvider(type = BoothRequestSqlProvider.class, method = "getBoothRequestList")
    List<BoothRequest> getBoothRequestList(@Param("search") PageableBoothRequest search);

    @SelectProvider(type = BoothRequestSqlProvider.class, method = "getBoothRequestCount")
    long getBoothRequestCount(@Param("search") PageableBoothRequest search);

    @UpdateProvider(type = BoothRequestSqlProvider.class, method = "updateCheck")
    int updateCheck(@Param("id") long id, @Param("adminId") long adminId);

    @SelectProvider(type = BoothRequestSqlProvider.class, method = "boothRequestExcel")
    List<BoothRequestExcel> boothRequestExcel(@Param("exhibitionId") long exhibitionId);

}
