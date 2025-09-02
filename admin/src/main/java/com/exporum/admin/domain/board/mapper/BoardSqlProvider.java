package com.exporum.admin.domain.board.mapper;

import com.exporum.admin.domain.board.model.BoardDTO;
import com.exporum.admin.domain.board.model.PageableBoard;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */
public class BoardSqlProvider {

    public String updateBoardDisable(long id, BoardDTO board){
        return new SQL(){
            {
                UPDATE("bulletin_board_system");
                SET("is_disabled = #{board.disabled}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{board.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String deleteBoard(long id, long adminId){
        return new SQL(){
            {
                UPDATE("bulletin_board_system");
                SET("is_deleted = 1");
                SET("deleted_at = sysdate()");
                SET("deleted_by = #{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }


    public String updateBoard(long id, BoardDTO board){
        return new SQL(){
            {
                UPDATE("bulletin_board_system");
                SET("title = #{board.title}");
                SET("content = #{board.content}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{board.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String insertBoard(BoardDTO board){
        return new SQL(){
            {
                INSERT_INTO("bulletin_board_system");
                VALUES("bbs_code", "#{board.bbsCode}");
                VALUES("exhibition_id", "#{board.exhibitionId}");
                VALUES("title", "#{board.title}");
                VALUES("content", "#{board.content}");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{board.adminId}");
            }
        }.toString();
    }



    public String getBoard(long id){
        return new SQL(){
            {
                SELECT("""
                        bbs.id, bbs.title, bbs.content, bbs.is_disabled as disabled,
                        ca.admin_name as created_by, nvl(ua.admin_name, '-') as updated_by, bbs.created_at, nvl(bbs.updated_at, '-') as updated_at,
                        case when baf.file_id is not null then 1 else 0 end as attached,
                        f.origin_file_name as filename, f.uuid, f.file_size, f.id as file_id
                        """);
                FROM("bulletin_board_system as bbs");
                LEFT_OUTER_JOIN("bbs_attachment_file as baf on bbs.id = baf.bbs_id and baf.is_deleted = 0");
                LEFT_OUTER_JOIN("files as f on baf.file_id = f.id");
                LEFT_OUTER_JOIN("admin as ca on ca.id = bbs.created_by");
                LEFT_OUTER_JOIN("admin as ua on ua.id = bbs.updated_by");
                WHERE("bbs.id = #{id}");
            }
        }.toString();
    }

    public String getBoardList(PageableBoard search){
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY bbs.created_at) AS no, bbs.id,
                        bbs.title, bbs.is_disabled as disabled, ca.admin_name as created_by, bbs.created_at,
                        case when baf.file_id is not null then 1 else 0 end as attached
                        """);
                FROM("bulletin_board_system as bbs");
                LEFT_OUTER_JOIN("bbs_attachment_file as baf on bbs.id = baf.bbs_id and baf.is_deleted = 0");
                LEFT_OUTER_JOIN("admin as ca on ca.id = bbs.created_by");
                WHERE("bbs.bbs_code = #{search.boardType}");
                WHERE("bbs.is_deleted = 0");
                if("E".equals(search.getStatus()) ){
                    WHERE("bbs.is_disabled= 0");
                }
                if("D".equals(search.getStatus()) ){
                    WHERE("bbs.is_disabled= 1");
                }
                if("T".equals(search.getType())){
                    WHERE("bbs.title like concat('%',#{search.searchText},'%')");
                }
                if("C".equals(search.getType())){
                    WHERE("bbs.content like concat('%',#{search.searchText},'%')");
                }
                ORDER_BY("bbs.created_at DESC");
                LIMIT(search.getLength());
                OFFSET(search.getStart());
            }
        }.toString();

    }

    public String getBordCount(PageableBoard search){
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("bulletin_board_system as bbs");
                LEFT_OUTER_JOIN("bbs_attachment_file as baf on bbs.id = baf.bbs_id and baf.is_deleted = 0");
                LEFT_OUTER_JOIN("admin as ca on ca.id = bbs.created_by");
                WHERE("bbs.bbs_code = #{search.boardType}");
                WHERE("bbs.is_deleted = 0");
                if("E".equals(search.getStatus()) ){
                    WHERE("bbs.is_disabled= 0");
                }
                if("D".equals(search.getStatus()) ){
                    WHERE("bbs.is_disabled= 1");
                }
                if("T".equals(search.getType())){
                    WHERE("bbs.title like concat('%',#{search.searchText},'%')");
                }
                if("C".equals(search.getType())){
                    WHERE("bbs.content like concat('%',#{search.searchText},'%')");
                }
            }
        }.toString();
    }
}
