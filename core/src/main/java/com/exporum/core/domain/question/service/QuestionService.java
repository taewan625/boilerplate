package com.exporum.core.domain.question.service;

import com.exporum.core.domain.question.mapper.QuestionMapper;
import com.exporum.core.domain.question.model.Question;
import com.exporum.core.domain.question.model.RecentQuestion;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 13.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionMapper questionMapper;

    @Transactional
    public void insertQuestion(Question question) {
        if(!(questionMapper.insertQuestion(question) > 0)){
            throw new OperationFailException();
        }
    }


    public RecentQuestion getRecentQuestion(long userId) {
        return questionMapper.getRecentQuestion(userId);
    }

}
