package com.exporum.admin.domain.board.service;

import com.exporum.admin.domain.board.mapper.BoardMapper;
import com.exporum.admin.domain.board.model.Board;
import com.exporum.admin.domain.board.model.BoardDTO;
import com.exporum.admin.domain.board.model.PageableBoard;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.service.StorageService;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    private final StorageService storageService;


    @Value("${ncp.object-storage.path.notice}")
    private String noticeObjectStoragePath;

    @Value("${ncp.object-storage.path.press}")
    private String pressObjectStoragePath;

    public Board getBoard(long id){
        return Optional.ofNullable(boardMapper.getBoard(id)).orElseThrow(DataNotFoundException::new);
    }

    public void getPageableBoard(PageableBoard pageable) {

        long recordsTotal = boardMapper.getBordCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(boardMapper.getBoardList(pageable));

    }


    public void updateBoardDisable(long id, BoardDTO board) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        board.setAdminId(adminId);

        if(!(boardMapper.updateBoardDisable(id, board) > 0)) {
            throw new OperationFailException();
        }

    }


    public void deleteBoard(long id){
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        if(!(boardMapper.deleteBoard(id, adminId) > 0)) {
            throw new OperationFailException();
        }
    }

    @Transactional
    public void insertBoardProcess(BoardDTO board) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        board.setAdminId(adminId);

        this.insertBoard(board);

        if(board.getFile() != null){
            String storagePath = noticeObjectStoragePath;
            if("PRESS".equals(board.getBbsCode())) storagePath = pressObjectStoragePath;
            FileDTO file = storageService.ncpUpload(board.getFile(), storagePath);

            board.setFileId(file.getId());
            this.insertAttachmentFile(board.getId(), board);
        }

    }


    @Transactional
    public void updateBoardProcess(long id, BoardDTO board) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        board.setAdminId(adminId);

        if(board.getFile() != null){
            String storagePath = noticeObjectStoragePath;
            if("PRESS".equals(board.getBbsCode())) storagePath = pressObjectStoragePath;
            FileDTO file = storageService.ncpUpload(board.getFile(), storagePath);

            if(board.getFileId() != 0){
                this.deleteAttachmentFile(id, board);
            }

            board.setFileId(file.getId());
            this.insertAttachmentFile(id, board);

        }
        this.updateBoard(id, board);
    }



    private void insertAttachmentFile(long id, BoardDTO board) {
        if(!(boardMapper.insertAttachmentFile(id, board) > 0)) {
            throw new OperationFailException();
        }
    }

    private void deleteAttachmentFile(long id, BoardDTO board) {
        if(!(boardMapper.deleteAttachmentFile(id, board) > 0)) {
            throw new OperationFailException();
        }
    }


    private void insertBoard(BoardDTO board) {
        if(!(boardMapper.insertBoard(board) > 0)) {
            throw new OperationFailException();
        }
    }

    private void updateBoard(long id, BoardDTO board) {
        if(!(boardMapper.updateBoard(id, board) > 0)) {
            throw new OperationFailException();
        }
    }
}
