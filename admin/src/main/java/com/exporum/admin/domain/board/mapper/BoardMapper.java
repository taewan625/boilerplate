package com.exporum.admin.domain.board.mapper;

import com.exporum.admin.domain.board.model.Board;
import com.exporum.admin.domain.board.model.BoardDTO;
import com.exporum.admin.domain.board.model.BoardList;
import com.exporum.admin.domain.board.model.PageableBoard;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */

@Mapper
public interface BoardMapper {

    @SelectProvider(type = BoardSqlProvider.class, method = "getBoard")
    Board getBoard(@Param("id") long id);

    @SelectProvider(type = BoardSqlProvider.class, method = "getBoardList")
    List<BoardList> getBoardList(@Param("search")PageableBoard search);

    @SelectProvider(type = BoardSqlProvider.class, method = "getBordCount")
    long getBordCount(@Param("search")PageableBoard search);

    @InsertProvider(type = BoardAttachmentFileSqlProvider.class, method = "insertAttachmentFile")
    int insertAttachmentFile(@Param("id") long id, @Param("board")BoardDTO board);

    @UpdateProvider(type = BoardAttachmentFileSqlProvider.class, method = "deleteAttachmentFile")
    int deleteAttachmentFile(@Param("id") long id, @Param("board")BoardDTO board);

    @InsertProvider(type = BoardSqlProvider.class, method = "insertBoard")
    @Options(useGeneratedKeys = true, keyProperty = "board.id", keyColumn = "id")
    int insertBoard(@Param("board") BoardDTO board);

    @UpdateProvider(type = BoardSqlProvider.class, method = "updateBoard")
    int updateBoard(@Param("id") long id, @Param("board") BoardDTO board);

    @UpdateProvider(type = BoardSqlProvider.class, method = "updateBoardDisable")
    int updateBoardDisable(@Param("id") long id, @Param("board") BoardDTO board);

    @UpdateProvider(type = BoardSqlProvider.class, method = "deleteBoard")
    int deleteBoard(@Param("id") long id, @Param("adminId") long adminId);

}
