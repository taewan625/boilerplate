package com.exporum.core.domain.user.service;

import com.exporum.core.domain.user.mapper.UserMapper;
import com.exporum.core.domain.user.model.User;
import com.exporum.core.domain.user.model.UserDTO;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public long insertUserIfEmailNotExist(UserDTO user) throws OperationFailException {
        Optional<User> optionalUser = Optional.ofNullable(userMapper.getUserByEmail(user.getEmail()));
        optionalUser.ifPresent(u -> this.updateUser(user));
        return optionalUser.map(User::getId).orElseGet(() -> this.insertUser(user));
    }

    public void updateUser(UserDTO user) {
        if(!(userMapper.updateUser(user) > 0)){
            throw new OperationFailException();
        }
    }

    public long insertUser(UserDTO user) {
        if(!(userMapper.insertUser(user) > 0)){
           throw new OperationFailException();
        }
        return  user.getUserId();
    }

    public User getUserByEmail(String email){
        return userMapper.getUserByEmail(email);
    }

    public User getUserById(long id){
        return userMapper.getUserById(id);
    }

}
