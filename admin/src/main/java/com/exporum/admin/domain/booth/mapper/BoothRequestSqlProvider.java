package com.exporum.admin.domain.booth.mapper;

import com.exporum.admin.domain.booth.model.PageableBoothRequest;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: Lee Hyunseung
 * @date : 25. 5. 13.
 * @description :
 */
public class BoothRequestSqlProvider {

    public String updateCheck(long id, long adminId){
        return new SQL(){
            {
                UPDATE("exhibitor_inquiry");
                SET("is_check=1");
                SET("updated_at = sysdate()");
                SET("updated_by = #{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String getBoothRequest(long id){
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY ei.created_at asc) AS no,
                        ei.id, ei.country_id, ei.industry_code, c.country_name_en as country,
                        ex.exhibition_name as exhibition, ei.industry_code, ic.code_name as industry,
                        ei.company, ei.brand as brandName, ei.contact_person, ei.job_title, ei.email, ei.second_email, ei.office_calling_code, ei.office_number,
                        ei.mobile_calling_code, ei. mobile_number, ei.address, ei.homepage, ei.instagram, ei.facebook,
                        ei.etc_sns, ei.introduction, ei.is_check as `check`, ei.created_at, ei.updated_at, a.admin_name as updated_by
                        """);
                FROM("exhibitor_inquiry as ei");
                JOIN("country as c on ei.country_id = c.id");
                JOIN("code as ic on ei.industry_code = ic.code");
                LEFT_OUTER_JOIN("admin as a on ei.updated_by = a.id");
                LEFT_OUTER_JOIN("exhibition as ex on ei.exhibition_id = ex.id");
                WHERE("ei.id = #{id}");
            }
        }.toString();
    }


    public String getBoothRequestList(PageableBoothRequest search){
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY ei.created_at asc) AS no,
                        ei.id, ei.country_id, ei.industry_code, c.country_name_en as country,
                        ex.exhibition_name as exhibition, ei.industry_code, ic.code_name as industry,
                        ei.company, ei.contact_person, ei.job_title, ei.email, ei.office_calling_code, ei.office_number,
                        ei.mobile_calling_code, ei. mobile_number, ei.address, ei.homepage, ei.instagram, ei.facebook,
                        ei.etc_sns, ei.introduction, ei.is_check as `check`, ei.created_at, ei.updated_at, a.admin_name as updated_by
                        """);
                FROM("exhibitor_inquiry as ei");
                JOIN("country as c on ei.country_id = c.id");
                JOIN("code as ic on ei.industry_code = ic.code");
                LEFT_OUTER_JOIN("admin as a on ei.updated_by = a.id");
                LEFT_OUTER_JOIN("exhibition as ex on ei.exhibition_id = ex.id");
                if("C".equals(search.getSearchType())){
                    WHERE("ei.is_check = 1 ");
                }
                if("U".equals(search.getSearchType())){
                    WHERE("ei.is_check = 0 ");
                }
                if(StringUtils.hasText(search.getSearchText())){
                    WHERE("""
                            ei.company like concat('%',#{search.searchText},'%')
                            """);
                }
                ORDER_BY("ei.created_at desc");
                LIMIT(search.getLength());
                OFFSET(search.getStart());
            }
        }.toString();
    }


    public String getBoothRequestCount(PageableBoothRequest search){
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("exhibitor_inquiry as ei");
                JOIN("country as c on ei.country_id = c.id");
                JOIN("code as ic on ei.industry_code = ic.code");
                LEFT_OUTER_JOIN("admin as a on ei.updated_by = a.id");
                LEFT_OUTER_JOIN("exhibition as ex on ei.exhibition_id = ex.id");
                if("C".equals(search.getSearchType())){
                    WHERE("ei.is_check = 1 ");
                }
                if("U".equals(search.getSearchType())){
                    WHERE("ei.is_check = 0 ");
                }
                if(StringUtils.hasText(search.getSearchText())){
                    WHERE("""
                            ei.company like concat('%',#{search.searchText},'%')
                            """);
                }
            }
        }.toString();
    }

    public String boothRequestExcel(long exhibitionId){
        return new SQL(){
            {
                SELECT("""
                    ROW_NUMBER() OVER (ORDER BY ei.created_at ASC) AS no
                    , IFNULL(c.country_name_en, '-') AS country
                    , IFNULL(ic.code_name, '-') AS industry
                    , IFNULL(ei.company, '-') AS company
                    , IFNULL(ei.brand, '-') AS brand
                    , IFNULL(ei.contact_person, '-') AS contactPerson
                    , IFNULL(ei.job_title, '-') AS jobTitle
                    , IFNULL(ei.email, '-') AS email
                    , IFNULL(ei.second_email, '-') AS secondEmail
                    , CASE
                         WHEN ei.office_calling_code IS NULL OR ei.office_number IS NULL
                         THEN '-'
                         ELSE CONCAT(ei.office_calling_code, ' ', ei.office_number)
                      END AS officeNumber
                    , CASE
                        WHEN ei.mobile_calling_code IS NULL OR ei.mobile_number IS NULL
                        THEN '-'
                        ELSE CONCAT(ei.mobile_calling_code, ' ', ei.mobile_number)
                      END AS mobileNumber
                    , IFNULL(ei.address, '-') AS address
                    , IFNULL(ei.homepage, '-') AS homepage
                    , IFNULL(ei.instagram, '-') AS instagram
                    , IFNULL(ei.facebook, '-') AS facebook
                    , IFNULL(ei.etc_sns, '-') AS etcSns
                    , IFNULL(ei.created_at, '-') AS createdAt
                    """);
                FROM("exhibitor_inquiry AS ei");
                JOIN("country AS c ON ei.country_id = c.id");
                JOIN("code AS ic ON ei.industry_code = ic.code");
                WHERE("ei.exhibition_id = 3");
                ORDER_BY("ei.created_at DESC");
            }
        }.toString();
    }


}
