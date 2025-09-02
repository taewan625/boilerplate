package com.exporum.client.domain.about.mapper;

import com.exporum.client.domain.about.model.ContactDTO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Mapper
public interface ContactMapper {

    @InsertProvider(type = ContactSqlProvider.class, method = "insertContact")
    int insertContact(@Param("contact") ContactDTO contact);

}
