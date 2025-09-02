package com.exporum.admin.domain.board.service;

import com.exporum.admin.domain.board.mapper.ContactusMapper;
import com.exporum.admin.domain.board.model.Contactus;
import com.exporum.admin.domain.board.model.PageableContactus;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class ContactusService {

    private final ContactusMapper contactusMapper;


    public Contactus getContactus(long id){
        return Optional.ofNullable(contactusMapper.getContactus(id)).orElseThrow(DataNotFoundException::new);
    }

    public void updatedReplied(long id){
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        if(!(contactusMapper.updatedReplied(id,adminId)>0)){
            throw new OperationFailException();
        }
    }

    public void getPageableContactus(PageableContactus pageable) {

        long recordsTotal = contactusMapper.getContactusCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(contactusMapper.getContactusList(pageable));

    }

}
