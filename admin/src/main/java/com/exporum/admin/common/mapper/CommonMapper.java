package com.exporum.admin.common.mapper;

import com.exporum.admin.common.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 19.
 * @description :
 */
@Mapper
public interface CommonMapper {

    @SelectProvider(type = CommonSqlProvider.class, method = "getExhibitionSelect")
    List<ExhibitionSelectOption> getExhibitionSelect();

    @SelectProvider(type = CommonSqlProvider.class, method = "getSelectOption")
    List<SelectOption> getSelectOption(@Param("parentCode") String parentCode);


    @SelectProvider(type = CommonSqlProvider.class, method = "getTicketSelectOption")
    List<TicketSelectOption> getTicketSelectOption(@Param("exhibitionId") long exhibitionId);

    @SelectProvider(type = CommonSqlProvider.class, method = "getExhibitionTimezone")
    String getExhibitionTimezone(@Param("exhibitionId") long exhibitionId);

    @SelectProvider(type = CommonSqlProvider.class, method = "getCountryOption")
    List<CountryOption> getCountryOption();

    @SelectProvider(type = CommonSqlProvider.class, method = "getCallingCodeOption")
    List<CallingCodeOption> getCallingCodeOption();

}
