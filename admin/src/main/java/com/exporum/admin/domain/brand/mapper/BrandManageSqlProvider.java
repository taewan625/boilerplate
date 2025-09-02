package com.exporum.admin.domain.brand.mapper;

import com.exporum.admin.domain.brand.model.BrandDTO;
import com.exporum.admin.domain.brand.model.PageableBrand;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 12.
 * @description :
 */
public class BrandManageSqlProvider {

    public String getBrandLogoList(){

        return new SQL(){
            {
                SELECT("""
                        CONCAT(
                            ROW_NUMBER() OVER (
                              ORDER BY CONCAT(
                                REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(e.brand_name, ',', ''), '.', ''), '/', ''), '&', ''), '+', ''),
                                '.',
                                f.ext
                              ) ASC
                            ),
                            '-',
                            CONCAT(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(e.brand_name, ',', ''), '.', ''), '/', ''), '&', ''), '+', ''),'.',f.ext)
                          ) AS filename
                        , concat('https://kr.object.ncloudstorage.com/bucket-exporum-prod/',f.file_path) as url
                        """);
                FROM("exhibitor as e");
                JOIN("files as f on e.file_id = f.id");
                WHERE("e.exhibition_id = 2");
                WHERE("is_apply = 1");
                ORDER_BY("concat(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(e.brand_name, ',', ''), '.', ''), '/' ,''),'&',''),'+',''),'.',f.ext) ASC");
            }
        }.toString();
    }



    public String getBrandExcel(long exhibitionId){
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY e.id desc) AS no,
                        ex.exhibition_name as exhibit, c.company_name_en as company,
                        e.brand_name as brand, ic.code_name as industry, cou.country_name_en as country,
                        concat(c.country_calling_num, ' ', c.office_number) as office_number, em.manager_name,
                        em.job_title, c.email, concat(em.country_calling_number, ' ', em.mobile_number) as mobile_number,
                        case when e.is_apply = 0 then 'Wait'  when e.is_apply = 1 then 'Approve' else 'Reject' end as status,
                        e.introduction, e.booth_number as booth
                        """);
                FROM("exhibitor as e");
                JOIN("company as c on c.id = e.company_id");
                JOIN("exhibitor_manager as em on em.exhibitor_id = e.id");
                JOIN("exhibition as ex on ex.id = e.exhibition_id");
                JOIN("code as ic on ic.code = c.industry_code");
                JOIN("country as cou on cou.id = c.country_id");
                WHERE("e.exhibition_id = #{exhibitionId}");
                ORDER_BY("e.id desc");

            }
        }.toString();

    }

    public String updateBrand(long id, BrandDTO brand){
        return new SQL(){
            {
                UPDATE("exhibitor");
                SET("file_id = #{brand.fileId}");
                SET("brand_name = #{brand.brandName}");
                SET("booth_number = #{brand.booth}");
                SET("instagram = #{brand.instagram}");
                SET("facebook = #{brand.facebook}");
                SET("etc_sns = #{brand.etcSns}");
                SET("introduction = #{brand.introduction}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{brand.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();

    }

    public String updateApprove(long id, BrandDTO exhibitor){
        return new SQL(){
            {
                UPDATE("exhibitor");
                SET("is_apply = #{exhibitor.approve}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{exhibitor.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String getBrand(long id, String storageUrl){
        return new SQL(){
            {
                SELECT("""
                        e.id, e.company_id, e.brand_name as brand, com.company_name as company,
                        ic.code_name as industry, com.industry_code,
                        con.country_name_en as country, com.country_id, com.company_address as address,
                        em.manager_name, em.job_title,em.email, com.country_calling_num as office_calling, com.office_number,
                        em.country_calling_number as mobile_calling, em.mobile_number,
                        e.file_id, concat(#{storageUrl}, f.file_path) as file_path, e.introduction, com.homepage, 
                        e.facebook, e.instagram, e.etc_sns,
                        e.is_apply as approve, e.exhibition_id, ex.exhibition_name, e.booth_number as booth
                        """);
                FROM("exhibitor as e");
                JOIN("exhibitor_manager as em on e.id = em.exhibitor_id");
                JOIN("exhibition as ex on e.exhibition_id = ex.id");
                JOIN("company as com on e.company_id = com.id");
                JOIN("country as con on con.id = com.country_id");
                JOIN("code as ic on ic.code = com.industry_code");
                JOIN("files as f on e.file_id = f.id");
                WHERE("e.id = #{id}");
            }
        }.toString();
    }



    public String getBrandList(PageableBrand search){
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY e.id asc) AS no,
                        e.id, e.exhibition_id, con.country_name_en as country, e.brand_name as brand,
                        com.company_name as company, ic.code_name as industry, e.is_apply as approve, e.is_cancelled as cancelled,
                        e.created_at, e.updated_at, e.booth_number as booth
                        """);
                FROM("exhibitor as e");
                JOIN("company as com on e.company_id = com.id");
                JOIN("country as con on con.id = com.country_id");
                JOIN("code as ic on ic.code = com.industry_code");
                WHERE("e.exhibition_id = #{search.exhibitionId}");
                if("C".equals(search.getApprove())){
                    WHERE("is_apply = 1");
                }
                if("W".equals(search.getApprove())){
                    WHERE("is_apply = 0");
                }
                if("R".equals(search.getApprove())){
                    WHERE("is_apply = -1");
                }
                if("C".equals(search.getType())){
                    WHERE("com.company_name like concat('%',#{search.searchText},'%')");
                }
                if("B".equals(search.getType())){
                    WHERE("e.brand_name like concat('%',#{search.searchText},'%')");
                }
                ORDER_BY("e.id DESC, e.updated_at DESC");
                LIMIT(search.getLength());
                OFFSET(search.getStart());

            }
        }.toString();
    }

    public String getBrandCount(PageableBrand search){
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("exhibitor as e");
                JOIN("company as com on e.company_id = com.id");
                JOIN("country as con on con.id = com.country_id");
                JOIN("code as ic on ic.code = com.industry_code");
                WHERE("e.exhibition_id = #{search.exhibitionId}");
                if("C".equals(search.getApprove())){
                    WHERE("is_apply = 1");
                }
                if("W".equals(search.getApprove())){
                    WHERE("is_apply = 0");
                }
                if("R".equals(search.getApprove())){
                    WHERE("is_apply = -1");
                }
                if("C".equals(search.getType())){
                    WHERE("com.company_name like concat('%',#{search.searchText},'%')");
                }
                if("B".equals(search.getType())){
                    WHERE("e.brand_name like concat('%',#{search.searchText},'%')");
                }

            }
        }.toString();
    }
}
