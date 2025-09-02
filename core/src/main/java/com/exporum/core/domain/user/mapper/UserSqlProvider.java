package com.exporum.core.domain.user.mapper;

import com.exporum.core.domain.user.model.UserDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */
public class UserSqlProvider {

    public String insertUser(UserDTO user) {
        return new SQL(){
            {
                INSERT_INTO("user");
                VALUES("country_id", "#{user.countryId}");
                VALUES("prefix_code", "#{user.prefixCode}");
                VALUES("email", "#{user.email}");
                VALUES("password", "#{user.password}");
                VALUES("first_name", "#{user.firstName}");
                VALUES("last_name", "#{user.lastName}");
                VALUES("country_calling_number", "#{user.countryCallingNumber}");
                VALUES("mobile_number", "#{user.mobileNumber}");
                VALUES("is_policy_agree", "#{user.policyAgree}");
                VALUES("is_privacy_agree", "#{user.privacyAgree}");
                VALUES("city", "#{user.city}");
                VALUES("company", "#{user.company}");
                VALUES("job_title", "#{user.jobTitle}");
            }
        }.toString();
    }

    public String updateUser(UserDTO user) {
        return new SQL(){
            {
                UPDATE("user");
                SET("country_id = #{user.countryId}");
                SET("country_id = #{user.countryId}");
                SET("prefix_code = #{user.prefixCode}");
                SET("password = #{user.password}");
                SET("first_name = #{user.firstName}");
                SET("last_name = #{user.lastName}");
                SET("country_calling_number = #{user.countryCallingNumber}");
                SET("mobile_number = #{user.mobileNumber}");
                SET("is_policy_agree = #{user.policyAgree}");
                SET("is_privacy_agree = #{user.privacyAgree}");
                SET("city = #{user.city}");
                SET("company = #{user.company}");
                SET("job_title = #{user.jobTitle}");
                WHERE("email = #{user.email}");
            }
        }.toString();
    }





    public String getUserByEmail(String email){
        return new SQL(){
            {
                SELECT("""
                        u.id, u.country_id, country.alpha_2 as country_code, country.country_name as country, u.prefix_code, code.code_name as prefix, u.email, u.first_name, u.last_name,
                        u.country_calling_number, u.mobile_number, u.is_policy_agree as policy_agree,
                        u.is_privacy_agree as privacy_agree, u.city, u.company, u.job_title
                        """);
                FROM("user as u");
                JOIN("country as country ON u.country_id = country.id");
                LEFT_OUTER_JOIN("code as code ON u.prefix_code = code.code");
                WHERE("u.email = #{email}");
            }
        }.toString();
    }

    public String getUserById(long id){
        return new SQL(){
            {
                SELECT("""
                        u.id, u.country_id, country.alpha_2 as country_code, country.country_name as country, u.prefix_code, code.code_name as prefix, u.email, u.first_name, u.last_name,
                        u.country_calling_number, u.mobile_number, u.is_policy_agree as policy_agree,
                        u.is_privacy_agree as privacy_agree, u.city, u.company, u.job_title
                        """);
                FROM("user as u");
                JOIN("country as country ON u.country_id = country.id");
                LEFT_OUTER_JOIN("code as code ON u.prefix_code = code.code");
                WHERE("u.id = #{id}");
            }
        }.toString();
    }
}
