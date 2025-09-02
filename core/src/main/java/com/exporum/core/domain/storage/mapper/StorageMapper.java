package com.exporum.core.domain.storage.mapper;

import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.model.FileInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */

@Mapper
public interface StorageMapper {

    @InsertProvider(type= StorageSqlProvider.class, method = "insertFiles")
    @Options(useGeneratedKeys = true, keyProperty = "file.id", keyColumn = "id")
    int insertFiles(@Param("file") FileDTO file);


    @SelectProvider(type = StorageSqlProvider.class, method = "getFileByUUID")
    FileInfo getFileByUUID(@Param("uuid") String uuid);
}
