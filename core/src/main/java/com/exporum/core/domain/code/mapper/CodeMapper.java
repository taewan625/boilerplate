package com.exporum.core.domain.code.mapper;

import com.exporum.core.domain.code.model.CodeDetail;
import com.exporum.core.domain.code.model.CodeList;
import com.exporum.core.entity.CodeSet;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Mapper
public interface CodeMapper {

    @SelectProvider(type = CodeSqlProvider.class, method = "getCode")
    CodeDetail getCode(@Param("code") String code);


    @SelectProvider(type = CodeSqlProvider.class, method = "findByCode")
    @Results(value = {
     @Result(column = "code", property = "code"),
     @Result(column = "code", property = "children", many = @Many(select = "findChildrenCodeByCode"))
    })
    List<CodeList> findByCode(@Param("code") String code);


    @SelectProvider(type = CodeSqlProvider.class, method = "findByCodeIn")
    @Results(value = {
            @Result(column = "code", property = "code"),
            @Result(column = "code", property = "children", many = @Many(select = "findChildrenCodeByCode"))
    })
    List<CodeList> findByCodeIn(@Param("codes") String codes);


    @SelectProvider(type = CodeSqlProvider.class, method = "findChildrenCodeByCode")
    @Results(value = {
            @Result(column = "code", property = "code"),
            @Result(column = "code", property = "children", many = @Many(select = "findChildrenCodeByCode"))
    })
    List<CodeList> findChildrenCodeByCode(@Param("code") String code);


    @SelectProvider(type = CodeSqlProvider.class, method = "findCodeSetBySetCode")
    CodeSet findCodeSetBySetCode(@Param("setCode") String setCode);

}


