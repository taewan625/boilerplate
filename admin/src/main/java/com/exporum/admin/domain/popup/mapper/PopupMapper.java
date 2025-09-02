package com.exporum.admin.domain.popup.mapper;

import com.exporum.admin.domain.popup.model.PageablePopup;
import com.exporum.admin.domain.popup.model.PopupDTO;
import com.exporum.admin.domain.popup.model.PopupList;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 6.
 * @description :
 */

@Mapper
public interface PopupMapper {

    @InsertProvider(type = PopupSqlProvider.class, method = "insertPopup")
    int insertPopup(@Param("popup")PopupDTO popup);

    @UpdateProvider(type = PopupSqlProvider.class, method = "updatePopupOrder")
    int updatePopupOrder(@Param("popup")PopupDTO popup);

    @UpdateProvider(type = PopupSqlProvider.class, method = "updatePopup")
    int updatePopup(@Param("popup")PopupDTO popup);

    @SelectProvider(type = PopupSqlProvider.class, method = "getCurrentPublished")
    boolean getCurrentPublished(@Param("id") long id);

    @SelectProvider(type = PopupSqlProvider.class, method = "getPublishPopup")
    List<PopupList> getPublishPopup(@Param("search") PageablePopup search, @Param("path") String path);

    @SelectProvider(type = PopupSqlProvider.class, method = "getCurrentPublishPopup")
    List<PopupList> getCurrentPublishPopup(@Param("exhibitionId") long exhibitionId);

    @SelectProvider(type = PopupSqlProvider.class, method = "getPublishPopupCount")
    long getPublishPopupCount(@Param("search") PageablePopup search);
}
