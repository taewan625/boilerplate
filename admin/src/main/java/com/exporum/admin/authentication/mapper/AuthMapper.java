package com.exporum.admin.authentication.mapper;

import com.exporum.admin.authentication.model.AuthUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */

@Mapper
public interface AuthMapper {
    AuthUser getUser(String id);
}
