package com.exporum.admin.domain.exhibition.mapper;

import com.exporum.core.entity.Exhibition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Mapper
public interface ExhibitionMapper {

    @SelectProvider(type = ExhibitionSqlProvider.class, method = "findAll")
    List<Exhibition> findAll();

}
