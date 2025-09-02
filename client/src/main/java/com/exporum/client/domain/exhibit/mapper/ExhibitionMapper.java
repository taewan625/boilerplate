package com.exporum.client.domain.exhibit.mapper;

import com.exporum.client.domain.exhibit.model.Exhibition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */

@Mapper
public interface ExhibitionMapper {


    @SelectProvider(type = ExhibitionSqlProvider.class, method = "getExhibitionYearList")
    List<Integer> getExhibitionYearList();

    @SelectProvider(type = ExhibitionSqlProvider.class, method = "getCurrentExhibition")
    Exhibition getCurrentExhibition();

}
