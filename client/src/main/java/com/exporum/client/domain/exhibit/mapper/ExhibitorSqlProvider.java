package com.exporum.client.domain.exhibit.mapper;

import com.exporum.client.domain.exhibit.model.ExhibitorDTO;
import com.exporum.client.domain.exhibit.model.ExhibitorInquiryDTO;
import com.exporum.client.domain.exhibit.model.ExhibitorManagerDTO;
import com.exporum.client.domain.exhibit.model.SearchExhibitor;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */
public class ExhibitorSqlProvider {

    public String getExhibitorList(SearchExhibitor searchExhibitor, String storageUrl) {
        return new SQL(){
            {

                SELECT("""
                        ce.id as id, ex.year as year, com.company_name as company_name,
                        ce.brand_name as brand_name, concat(#{storageUrl},f.file_path) as path, ce.booth_number,
                        ce.facebook as facebook, ce.instagram as instagram, ce.twitter as twitter, ce.etc_sns as etc_sns,
                        ce.introduction, com.homepage
                        """);
                FROM("exhibitor as ce");
                JOIN("files as f ON ce.file_id = f.id");
                JOIN("exhibition ex on ce.exhibition_id = ex.id");
                JOIN("company com on ce.company_id = com.id");
                WHERE("ex.year = #{searchExhibitor.year}");
                WHERE("ce.is_apply = 1");
                WHERE("ce.is_cancelled = 0");
                if (StringUtils.hasText(searchExhibitor.getSearchText())) {
                    WHERE("ce.brand_name LIKE CONCAT('%', #{searchExhibitor.searchText}, '%')");
                }
                ORDER_BY("ce.created_at DESC");
                LIMIT(searchExhibitor.getPageSize());
                OFFSET(searchExhibitor.getOffset());
            }
        }.toString();
    }

    public String getExhibitorCount(SearchExhibitor searchExhibitor) {
        return new SQL(){
            {
                SELECT("count(*) as count");
                FROM("exhibitor as ce");
                JOIN("exhibition ex on ce.exhibition_id = ex.id");
                WHERE("ex.year = #{searchExhibitor.year}");
                WHERE("ce.is_apply = 1");
                WHERE("ce.is_cancelled = 0");
                if (StringUtils.hasText(searchExhibitor.getSearchText())) {
                    WHERE("ce.brand_name LIKE CONCAT('%', #{searchExhibitor.searchText}, '%')");
                }
            }
        }.toString();
    }


    public String getExhibitor(long id, String storageUrl) {
        return new SQL(){
            {

                SELECT("""
                        ce.id as id, ex.year as year, com.company_name as company_name,
                        ce.brand_name as brand_name, concat(#{storageUrl},f.file_path) as path, ce.booth_number,
                        ce.facebook as facebook, ce.instagram as instagram, ce.twitter as twitter, ce.etc_sns as etc_sns,
                        ce.introduction, com.homepage
                        """);
                FROM("exhibitor as ce");
                JOIN("files as f ON ce.file_id = f.id");
                JOIN("exhibition ex on ce.exhibition_id = ex.id");
                JOIN("company com on ce.company_id = com.id");
                WHERE("ce.id = #{id}");
            }
        }.toString();
    }



    public String insertExhibitor(ExhibitorDTO exhibitor) {
        return new SQL(){
            {
                INSERT_INTO("exhibitor");
                VALUES("company_id", "#{exhibitor.companyId}");
                VALUES("exhibition_id", "#{exhibitor.exhibitionId}");
                VALUES("file_id", "#{exhibitor.fileId}");
                VALUES("brand_name", "#{exhibitor.brandName}");
                VALUES("facebook", "#{exhibitor.facebook}");
                VALUES("instagram", "#{exhibitor.instagram}");
                VALUES("twitter", "#{exhibitor.twitter}");
                VALUES("etc_sns", "#{exhibitor.etcSns}");
                VALUES("introduction", "#{exhibitor.introduction}");
                VALUES("created_at", "sysdate()");
            }
        }.toString();
    }


    public String insertExhibitorManager(ExhibitorManagerDTO manager) {
        return new SQL(){
            {
                INSERT_INTO("exhibitor_manager");
                VALUES("exhibitor_id", "#{manager.exhibitorId}");
                VALUES("manager_name", "#{manager.managerName}");
                VALUES("department", "#{manager.department}");
                VALUES("job_title", "#{manager.jobTitle}");
                VALUES("email", "#{manager.email}");
                VALUES("country_calling_number", "#{manager.callingNumber}");
                VALUES("mobile_number", "#{manager.mobileNumber}");
                VALUES("created_at", "sysdate()");
            }
        }.toString();
    }



    public String insertExhibitorInquiry(ExhibitorInquiryDTO inquiry) {
        return new SQL(){
            {
                INSERT_INTO("exhibitor_inquiry");
                VALUES("country_id", "#{inquiry.countryId}");
                VALUES("exhibition_id", "#{inquiry.exhibitionId}");
                VALUES("industry_code", "#{inquiry.industryCode}");
                VALUES("company", "#{inquiry.companyName}");
                VALUES("brand", "#{inquiry.brandName}");
                VALUES("contact_person", "#{inquiry.managerName}");
                VALUES("job_title", "#{inquiry.jobTitle}");
                VALUES("email", "#{inquiry.email}");
                VALUES("second_email", "#{inquiry.secondEmail}");
                VALUES("office_calling_code", "#{inquiry.officeCallingNumber}");
                VALUES("office_number", "#{inquiry.officeNumber}");
                VALUES("mobile_calling_code", "#{inquiry.mobileCallingNumber}");
                VALUES("mobile_number", "#{inquiry.mobileNumber}");
                VALUES("address", "#{inquiry.companyAddress}");
                VALUES("homepage", "#{inquiry.homepage}");
                VALUES("facebook", "#{inquiry.facebook}");
                VALUES("instagram", "#{inquiry.instagram}");
                VALUES("etc_sns", "#{inquiry.etcSns}");
                VALUES("introduction", "#{inquiry.introduction}");
                VALUES("created_at", "sysdate()");
            }
        }.toString();
    }
}
