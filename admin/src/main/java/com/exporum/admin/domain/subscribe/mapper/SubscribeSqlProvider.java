package com.exporum.admin.domain.subscribe.mapper;

import com.exporum.admin.domain.subscribe.model.PageableSubscribe;
import com.exporum.admin.domain.subscribe.model.SubscribeDTO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */
public class SubscribeSqlProvider {

    public String updateSubscribe(long id, SubscribeDTO subscribe) {
        return new SQL(){
            {
                UPDATE("subscribe");
                SET("is_subscribe = #{subscribe.subscribe}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{subscribe.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();

    }

    public String getSubscribeExcel(){
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY created_at DESC) AS no,
                        email, first_name, last_name,
                        case when is_subscribe = 1 then 'SUBSCRIBE' else 'UNSUBSCRIBE' end as status,
                        created_at as subscribe_date
                        """);
                FROM("subscribe");
            }
        }.toString();
    }




    public String getSubscribeList(PageableSubscribe subscribe) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY created_at asc) AS no,
                        id, email, first_name, last_name, is_subscribe as subscribe, created_at as subscribe_date,
                        updated_at
                        """);
                FROM("subscribe");
                if("S".equals(subscribe.getStatus())){
                    WHERE("is_subscribe = 1");
                }
                if("U".equals(subscribe.getStatus())){
                    WHERE("is_subscribe = 0");
                }
                if(StringUtils.hasText(subscribe.getSearchText())){
                    if("N".equals(subscribe.getSearchType())){
                        WHERE("concat(first_name, ' ', last_name) like concat('%',#{subscribe.searchText},'%')");
                    }
                    if("E".equals(subscribe.getSearchType())){
                        WHERE("email like concat('%',#{subscribe.searchText},'%')");
                    }
                }
                ORDER_BY("created_at DESC");
                LIMIT(subscribe.getLength());
                OFFSET(subscribe.getStart());
            }
        }.toString();


    }

    public String getSubscribeCount(PageableSubscribe subscribe) {
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("subscribe");
                if("S".equals(subscribe.getStatus())){
                    WHERE("is_subscribe = 1");
                }
                if("U".equals(subscribe.getStatus())){
                    WHERE("is_subscribe = 0");
                }
                if(StringUtils.hasText(subscribe.getSearchText())){
                    if("N".equals(subscribe.getSearchType())){
                        WHERE("concat(first_name, ' ', last_name) like concat('%',#{subscribe.searchText},'%')");
                    }
                    if("E".equals(subscribe.getSearchType())){
                        WHERE("email like concat('%',#{subscribe.searchText},'%')");
                    }
                }
            }
        }.toString();


    }
}
