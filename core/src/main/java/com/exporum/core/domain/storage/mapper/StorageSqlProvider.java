package com.exporum.core.domain.storage.mapper;

import com.exporum.core.domain.storage.model.FileDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */
public class StorageSqlProvider {

    public String insertFiles(FileDTO file){
        return new SQL(){
            {
                INSERT_INTO("files");
                VALUES("mime_type_code", "#{file.mimeType}");
                VALUES("uuid", "#{file.uuid}");
                VALUES("file_name", "#{file.fileName}");
                VALUES("origin_file_name", "#{file.originFileName}");
                VALUES("file_size", "#{file.fileSize}");
                VALUES("ext", "#{file.ext}");
                VALUES("file_path", "#{file.filePath}");
                VALUES("created_at", "sysdate()");

            }
        }.toString();
    }


    public String getFileByUUID(String uuid){
        return new SQL(){
            {
                SELECT("""
                        id, mime_type_code as mime_type,
                        uuid, file_name,origin_file_name, file_size, ext,
                        file_path, is_use as `use`, is_deleted as deleted
                        """);
                FROM("files");
                WHERE("uuid = #{uuid}");

            }
        }.toString();
    }




    public String insertDownloadHistory(String uuid){
        return null;

    }
}
