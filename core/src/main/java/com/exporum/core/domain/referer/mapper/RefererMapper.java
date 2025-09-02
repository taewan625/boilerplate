package com.exporum.core.domain.referer.mapper;

import com.exporum.core.domain.referer.model.ConnectionLog;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 6.
 * @description :
 */

@Mapper
public interface RefererMapper {

    @InsertProvider(type = RefererSqlProvider.class, method = "insertConnectionLog")
    void insertConnectionLog(@Param("referer") ConnectionLog referer);
}
