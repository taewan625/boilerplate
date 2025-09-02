package com.exporum.core.domain.question.mapper;

import com.exporum.core.domain.question.model.Question;
import com.exporum.core.domain.question.model.RecentQuestion;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 13.
 * @description :
 */

@Mapper
public interface QuestionMapper {

    @InsertProvider(type = QuestionSqlProvider.class, method = "insertQuestion")
    int insertQuestion(@Param("question") Question question);

    @SelectProvider(type = QuestionSqlProvider.class, method = "getRecentQuestion")
    RecentQuestion getRecentQuestion(@Param("userId") long userId);


}
