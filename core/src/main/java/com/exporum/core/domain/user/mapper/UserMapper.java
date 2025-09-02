package com.exporum.core.domain.user.mapper;

import com.exporum.core.domain.user.model.User;
import com.exporum.core.domain.user.model.UserDTO;
import org.apache.ibatis.annotations.*;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */

@Mapper
public interface UserMapper {

    @InsertProvider(type = UserSqlProvider.class, method = "insertUser")
    @Options(useGeneratedKeys = true, keyProperty = "user.userId", keyColumn = "id")
    int insertUser(@Param("user") UserDTO user);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateUser")
    int updateUser(@Param("user") UserDTO user);


    @SelectProvider(type = UserSqlProvider.class, method = "getUserByEmail")
    User getUserByEmail(@Param("email") String email);

    @SelectProvider(type = UserSqlProvider.class, method = "getUserById")
    User getUserById(@Param("id") long id);

}
