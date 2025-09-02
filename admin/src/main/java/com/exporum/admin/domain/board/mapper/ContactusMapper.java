package com.exporum.admin.domain.board.mapper;

import com.exporum.admin.domain.board.model.Contactus;
import com.exporum.admin.domain.board.model.ContactusList;
import com.exporum.admin.domain.board.model.PageableContactus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Mapper
public interface ContactusMapper {

    @SelectProvider(type = ContactusSqlProvider.class, method = "getContactusList")
    List<ContactusList> getContactusList(@Param("search") PageableContactus search);

    @SelectProvider(type = ContactusSqlProvider.class, method = "getContactusCount")
    long getContactusCount(@Param("search")PageableContactus search);

    @SelectProvider(type = ContactusSqlProvider.class, method = "getContactus")
    Contactus getContactus(@Param("id")long id);

    @UpdateProvider(type = ContactusSqlProvider.class, method = "updatedReplied")
    int updatedReplied(@Param("id")long id, @Param("adminId")long adminId);
}
