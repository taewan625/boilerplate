package com.exporum.core.domain.question.mapper;

import com.exporum.core.domain.question.model.Question;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 13.
 * @description :
 */
public class QuestionSqlProvider {

    public String insertQuestion(Question question) {
        return new SQL(){
            {
                INSERT_INTO("questionnaire");
                VALUES("order_id", "#{question.orderId}");
                VALUES("user_id", "#{question.userId}");
                VALUES("email", "#{question.email}");
                VALUES("occupation", "#{question.occupation}");
                VALUES("industry", "#{question.industry}");
                VALUES("experience", "#{question.experience}");
                VALUES("first_time_attending", "#{question.firstTimeAttending}");
                VALUES("authority", "#{question.authority}");
                VALUES("objective", "#{question.objective}");
                VALUES("age_group", "#{question.ageGroup}");
                VALUES("interest", "#{question.interest}");
            }
        }.toString();
    }

    public String getRecentQuestion(long userId){
        return new SQL(){
            {
                SELECT("""
                        occupation, industry, experience, first_time_attending,
                        authority, objective, age_group, interest
                        """);
                FROM("questionnaire");
                WHERE("user_id = #{userId}");
                ORDER_BY("created_at DESC");
                LIMIT(1);
            }
        }.toString();
    }

}
