package com.exporum.admin.domain.board.mapper;

import com.exporum.admin.domain.board.model.PageableContactus;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */
public class ContactusSqlProvider {

    public String getContactus(long id){
        return new SQL(){
            {
                SELECT("""
                        c.id, tc.code_name as contactor, ic.code_name as inquiry,
                        c.company_name as company, concat(c.last_name, ' ', c.first_name) as name,
                        c.email, nvl(c.title, '-') as title, c.content as message, c.created_at, 
                        nvl(ua.admin_name, '-') as admin_name,
                        nvl(c.updated_at, '-') as updated_at, 
                        c.is_answer as answer
                        """);
                FROM("contact as c");
                JOIN("code as tc on c.contactor_code = tc.code");
                JOIN("code as ic on c.message_about_code = ic.code");
                LEFT_OUTER_JOIN("admin as ua on ua.id = c.updated_by");
                WHERE("c.id = #{id}");
            }
        }.toString();
    }

    public String updatedReplied(long id, long adminId){
        return new SQL(){
            {
                UPDATE("contact");
                SET("is_answer = 1");
                SET("updated_at = sysdate()");
                SET("updated_by = #{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();

    }


    public String getContactusList(PageableContactus search) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY c.id asc) AS no,
                        c.id,
                        tc.code_name as contactor, ic.code_name as inquiry,
                        c.title, c.is_answer as answer, c.created_at
                        """);
                FROM("contact as c");
                JOIN("code as tc on c.contactor_code = tc.code");
                JOIN("code as ic on c.message_about_code = ic.code");
                if("W".equals(search.getStatus())){
                    WHERE("is_answer = 0");
                }
                if("R".equals(search.getStatus())){
                    WHERE("is_answer = 1");
                }
                ORDER_BY("c.id DESC");
                LIMIT(search.getLength());
                OFFSET(search.getStart());

            }
        }.toString();

    }

    public String getContactusCount(PageableContactus search) {
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("contact as c");
                JOIN("code as tc on c.contactor_code = tc.code");
                JOIN("code as ic on c.message_about_code = ic.code");
                if("W".equals(search.getStatus())){
                    WHERE("is_answer = 0");
                }
                if("R".equals(search.getStatus())){
                    WHERE("is_answer = 1");
                }
            }
        }.toString();

    }

}
