package com.exporum.admin.domain.exhibitor.mapper;

import com.exporum.admin.domain.exhibitor.model.ExhibitorDTO;
import com.exporum.admin.domain.exhibitor.model.PageableExhibitor;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 14.
 * @description :
 */
public class ExhibitorManageSqlProvider {

    public String getExhibitorExcel(long exhibitionId){
        return new SQL(){
            {
                SELECT("""
                        ec.id,
                        ROW_NUMBER() OVER (ORDER BY ec.id asc) AS no,
                        ac.code_name as type, ec.description, c.country_name as country,
                        c.is_mutual_tax_exemption as mutual_tax_exemption,
                        ec.is_scai_sales as scai_sales, sc.code_name as sponsor,
                        ec.application_date, ec.company_name as company, ec.brand_name as brand,
                        ec.booth_number, ec.booth_count, ec.is_sca_membership as sca_membership,
                        ic.code_name as industry, ec.contact_name, ec.job_title,
                        concat(ec.calling_number, ' ', ec.phone_number) as phone_number,
                        ec.email, ec.address, ec.homepage, ec.instagram, ec.facebook, ec.etc_sns
                        """);
                FROM("exhibitor_company as ec");
                JOIN("country as c on ec.country_id = c.id");
                JOIN("code as ac on ec.application_type = ac.code");
                LEFT_OUTER_JOIN("code as ic on ec.industry_code = ic.code");
                LEFT_OUTER_JOIN("code as sc on ec.sponsor_code = sc.code");
                LEFT_OUTER_JOIN("code as eac on ec.early_application_code = eac.code");
                WHERE("ec.exhibition_id = #{exhibitionId}");
                WHERE("ec.is_deleted = 0");
            }
        }.toString();
    }

    public String getExhibitor(long id){
        return new SQL(){
            {
                SELECT("""
                        ec.id, ec.exhibition_id, ec.is_scai_sales as scai_sales, ec.is_sca_membership as sca_membership,
                        ec.company_name as company, ec.brand_name as brand,
                        ec.industry_code, nvl(ic.code_name, '-') as industry,
                        c.id as country_id, c.country_name_en as country,
                        ec.sponsor_code, nvl(sc.code_name, '-') as sponsor, ec.early_application_code,
                        nvl(eac.code_name, '-') as early_application, ec.application_type as application_type_code,
                        ac.code_name as application_type, ec.application_date,
                        ec.homepage, ec.instagram, ec.facebook, ec.etc_sns,
                        ec.contact_name, nvl(ec.job_title, '-') as job_title, ec.email, ec.calling_number as calling_code,
                        ec.phone_number, ec.address, ec.booth_number, ec.booth_count, ec.description, c.is_mutual_tax_exemption as mutual_tax_exemption
                        """);
                FROM("exhibitor_company as ec");
                JOIN("country as c on ec.country_id = c.id");
                JOIN("code as ac on ec.application_type = ac.code");
                LEFT_OUTER_JOIN("code as ic on ec.industry_code = ic.code");
                LEFT_OUTER_JOIN("code as sc on ec.sponsor_code = sc.code");
                LEFT_OUTER_JOIN("code as eac on ec.early_application_code = eac.code");
                WHERE("ec.id = #{id}");
            }
        }.toString();
    }


    public String getExhibitorList(PageableExhibitor search) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY (CASE WHEN ec.id = '262' THEN 0 ELSE 1 END), ec.id asc) AS no,
                        ec.id, ec.exhibition_id, ec.company_name as company, ec.brand_name as brand, ec.booth_count,
                        c.country_name_en as country, ic.code_name as industry, ac.code_name as application_type,
                        sc.code_name as sponsor, ec.application_date
                        """);
                FROM("exhibitor_company as ec");
                JOIN("country as c on ec.country_id = c.id");
                JOIN("code as ac on ec.application_type = ac.code");
                LEFT_OUTER_JOIN("code as ic on ec.industry_code = ic.code");
                LEFT_OUTER_JOIN("code as sc on ec.sponsor_code = sc.code");
                WHERE("ec.exhibition_id = #{search.exhibitionId}");
                WHERE("ec.is_deleted = 0");
                if(StringUtils.hasText(search.getApplicationType())) {
                    WHERE("ec.application_type = #{search.applicationType}");
                }
                if(StringUtils.hasText(search.getIndustry())) {
                    WHERE("ec.industry_code = #{search.industry}");
                }
                if(StringUtils.hasText(search.getSponsor())) {
                    WHERE("ec.sponsor_code = #{search.sponsor}");
                }
                if("C".equals(search.getType())){
                    WHERE("ec.company_name like concat('%',#{search.searchText},'%')");
                }
                if("B".equals(search.getType())){
                    WHERE("ec.brand_name like concat('%',#{search.searchText},'%')");
                }

                if(search.getStart() == 0){
                    ORDER_BY("(CASE WHEN ec.id = '262' THEN 0 ELSE 1 END), ec.id DESC");
                }else{
                    ORDER_BY("ec.id DESC");
                }

                LIMIT(search.getLength());
                OFFSET(search.getStart());

            }
        }.toString();
    }

    public String getExhibitorCount(PageableExhibitor search) {
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("exhibitor_company as ec");
                JOIN("country as c on ec.country_id = c.id");
                JOIN("code as ac on ec.application_type = ac.code");
                LEFT_OUTER_JOIN("code as ic on ec.industry_code = ic.code");
                LEFT_OUTER_JOIN("code as sc on ec.sponsor_code = sc.code");
                WHERE("ec.exhibition_id = #{search.exhibitionId}");
                WHERE("ec.is_deleted = 0");
                if(StringUtils.hasText(search.getApplicationType())) {
                    WHERE("ec.application_type = #{search.applicationType}");
                }
                if(StringUtils.hasText(search.getIndustry())) {
                    WHERE("ec.industry_code = #{search.industry}");
                }
                if(StringUtils.hasText(search.getSponsor())) {
                    WHERE("ec.sponsor_code = #{search.sponsor}");
                }
                if("C".equals(search.getType())){
                    WHERE("ec.company_name like concat('%',#{search.searchText},'%')");
                }
                if("B".equals(search.getType())){
                    WHERE("ec.brand_name like concat('%',#{search.searchText},'%')");
                }

            }
        }.toString();
    }



    public String createExhibitor(ExhibitorDTO exhibitor) {
        return new SQL(){
            {
                INSERT_INTO("exhibitor_company");
                VALUES("country_id", "#{exhibitor.countryId}");
                VALUES("exhibition_id", "#{exhibitor.exhibitionId}");
                VALUES("application_type", "#{exhibitor.applicationType}");
                if(StringUtils.hasText(exhibitor.getIndustry())) {
                    VALUES("industry_code", "#{exhibitor.industry}");
                }
                if(StringUtils.hasText(exhibitor.getSponsorCode())) {
                    VALUES("sponsor_code", "#{exhibitor.sponsorCode}");
                }
                if(StringUtils.hasText(exhibitor.getEarlyApplicationCode())) {
                    VALUES("early_application_code", "#{exhibitor.earlyApplicationCode}");
                }
                VALUES("company_name", "#{exhibitor.companyName}");
                if(StringUtils.hasText(exhibitor.getBrandName())) {
                    VALUES("brand_name", "#{exhibitor.brandName}");
                }
                if(StringUtils.hasText(exhibitor.getHomepage())) {
                    VALUES("homepage", "#{exhibitor.homepage}");
                }
                if(StringUtils.hasText(exhibitor.getInstagram())) {
                    VALUES("instagram", "#{exhibitor.instagram}");
                }
                if(StringUtils.hasText(exhibitor.getFacebook())) {
                    VALUES("facebook", "#{exhibitor.facebook}");
                }
                if(StringUtils.hasText(exhibitor.getEtcSns())) {
                    VALUES("etc_sns", "#{exhibitor.etcSns}");
                }
                if(StringUtils.hasText(exhibitor.getJobTitle())) {
                    VALUES("job_title", "#{exhibitor.jobTitle}");
                }
                if(StringUtils.hasText(exhibitor.getAddress())) {
                    VALUES("address", "#{exhibitor.address}");
                }
                if(StringUtils.hasText(exhibitor.getDescription())) {
                    VALUES("description", "#{exhibitor.description}");
                }
                VALUES("contact_name", "#{exhibitor.contactName}");
                VALUES("calling_number", "#{exhibitor.callingCode}");
                VALUES("phone_number", "#{exhibitor.phoneNumber}");
                VALUES("email", "#{exhibitor.email}");
                VALUES("is_scai_sales", "#{exhibitor.scaiSales}");
                VALUES("is_sca_membership", "#{exhibitor.scaMembership}");
                VALUES("application_date", "#{exhibitor.applicationDate}");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{exhibitor.adminId}");
            }
        }.toString();
    }


    public String updateExhibitor(long id, ExhibitorDTO exhibitor) {
        return new SQL(){
            {
                UPDATE("exhibitor_company");
                SET("country_id = #{exhibitor.countryId}");
                SET("application_type = #{exhibitor.applicationType}");

                SET("industry_code = #{exhibitor.industry}");
                SET("sponsor_code = #{exhibitor.sponsorCode}");
                SET("early_application_code = #{exhibitor.earlyApplicationCode}");
                SET("company_name = #{exhibitor.companyName}");
                SET("brand_name = #{exhibitor.brandName}");
                SET("homepage = #{exhibitor.homepage}");
                SET("instagram = #{exhibitor.instagram}");
                SET("facebook = #{exhibitor.facebook}");
                SET("etc_sns = #{exhibitor.etcSns}");
                SET("job_title = #{exhibitor.jobTitle}");
                SET("address = #{exhibitor.address}");
                SET("description = #{exhibitor.description}");

                SET("contact_name = #{exhibitor.contactName}");
                SET("calling_number = #{exhibitor.callingCode}");
                SET("phone_number = #{exhibitor.phoneNumber}");
                SET("email = #{exhibitor.email}");
                SET("is_scai_sales = #{exhibitor.scaiSales}");
                SET("is_sca_membership= #{exhibitor.scaMembership}");
                SET("application_date = #{exhibitor.applicationDate}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{exhibitor.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String deleteExhibitor(long id, long adminId) {
        return new SQL(){
            {
                UPDATE("exhibitor_company");
                SET("is_deleted = 1");
                SET("deleted_at = sysdate()");
                SET("deleted_by = #{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();

    }

    public String updateBooth(long id, ExhibitorDTO exhibitor) {
        return new SQL(){
            {
                UPDATE("exhibitor_company");
                SET("booth_count = #{exhibitor.boothCount}");
                SET("booth_number = #{exhibitor.boothNumber}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{exhibitor.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();

    }
}
