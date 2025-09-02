package com.exporum.admin.domain.board.mapper;

import com.exporum.admin.domain.board.model.BoardDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */
public class BoardAttachmentFileSqlProvider {
    public String insertAttachmentFile(long id, BoardDTO board) {
        return new SQL() {
            {
                INSERT_INTO("bbs_attachment_file");
                VALUES("bbs_id", "#{id}");
                VALUES("file_id", "#{board.fileId}");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{board.adminId}");
            }
        }.toString();
    }

    public String deleteAttachmentFile(long id, BoardDTO board) {
        return new SQL(){
            {
                UPDATE("bbs_attachment_file");
                SET("is_deleted = 1");
                SET("updated_at = sysdate()");
                SET("updated_by = #{board.adminId}");
                WHERE("bbs_id = #{id}");
                WHERE("file_id = #{board.fileId}");
            }
        }.toString();
    }

}
