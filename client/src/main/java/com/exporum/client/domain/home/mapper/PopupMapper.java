package com.exporum.client.domain.home.mapper;

import com.exporum.client.domain.home.model.Popup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 10.
 * @description :
 */

@Mapper
public interface PopupMapper {


    @SelectProvider(type = PopupSqlProvider.class, method = "getPopup")
    List<Popup> getPopup(@Param("exhibitionId") long exhibitionId, @Param("path") String path);

}
