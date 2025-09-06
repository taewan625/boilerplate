package com.exporum.admin.domain.authentication.mapper;

import com.exporum.admin.domain.authentication.model.AuthUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */

@Mapper
public interface AuthMapper {
    AuthUser getUser(String id);
}
