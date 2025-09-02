package com.exporum.admin.domain.dashbord.mapper;

import com.exporum.admin.domain.dashbord.model.DashboardDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 25.
 * @description :
 */
public class DashboardSqlProvider {

    public String weekSalesTicket(long exhibitionId, DashboardDTO dashboard) {
        return """
                WITH RECURSIVE date_range AS (
                    SELECT #{dashboard.startDate} AS sale_date
                    UNION ALL
                    SELECT sale_date + INTERVAL 1 DAY FROM date_range
                    WHERE sale_date < #{dashboard.endDate}
                ),
                               ticket_ids AS (
                                   SELECT t.id AS ticket_id,
                                          t.ticket_name,
                                          t.ticket_type_code as code_name
                                   FROM ticket AS t
                                   WHERE t.exhibition_id = #{exhibitionId}
                                     AND t.is_buying = 1
                                     AND t.is_deleted = 0
                               )
                SELECT
                    d.sale_date,
                    ti.ticket_id,
                    ti.ticket_name,
                    ti.code_name as ticket_type,
                    COALESCE(SUM(o.quantity), 0) AS total_quantity,
                    COALESCE(SUM(o.amount), 0) AS total_sales
                FROM date_range d
                         CROSS JOIN ticket_ids ti
                         LEFT JOIN ticket_order o
                                   ON o.ticket_id = ti.ticket_id
                                       AND o.order_status_code = 'Payment Completed'
                                       AND DATE(o.created_at) = d.sale_date
                GROUP BY d.sale_date, ti.ticket_id, ti.ticket_name, ti.code_name
                ORDER BY d.sale_date ASC, ti.ticket_id
                """;
    }


    public String totalSalesTicket(long exhibitionId){
        return """
                WITH ticket_ids AS (
                    SELECT id as ticket_id
                    from ticket as t
                    where exhibition_id = #{exhibitionId}
                      and t.is_buying = 1
                      and t.is_deleted = 0
                )
                SELECT
                    CURDATE() AS sale_date,
                    ti.ticket_id,
                    t.ticket_name,
                    t.ticket_type_code as ticket_type,
                    COALESCE(SUM(o.quantity), 0) AS total_quantity,
                    COALESCE(SUM(o.amount), 0) AS total_sales
                FROM ticket_ids ti
                         LEFT JOIN ticket AS t ON t.id = ti.ticket_id
                         LEFT JOIN ticket_order AS o ON o.ticket_id = ti.ticket_id
                    AND o.order_status_code = 'Payment Completed'
                GROUP BY ti.ticket_id, t.ticket_name
                ORDER BY ti.ticket_id
                """;
    }


    public String dateRangeSalesTicket(long exhibitionId, DashboardDTO dashboard){
        return """
                WITH ticket_ids AS (
                    SELECT id AS ticket_id
                    FROM ticket AS t
                    WHERE exhibition_id = #{exhibitionId}
                      AND t.is_buying = 1
                      AND t.is_deleted = 0
                      AND (
                              t.end_date >= DATE(#{dashboard.startDate})
                                  AND t.start_date <= DATE(#{dashboard.endDate})
                        )
                )
                SELECT
                    DATE(o.created_at) AS sale_date,
                    ti.ticket_id,
                    t.ticket_name,
                    t.ticket_type_code AS ticket_type,
                    COALESCE(SUM(o.quantity), 0) AS total_quantity,
                    COALESCE(SUM(o.amount), 0) AS total_sales
                FROM ticket_ids ti
                         LEFT JOIN ticket AS t ON t.id = ti.ticket_id
                         LEFT JOIN ticket_order AS o
                                   ON o.ticket_id = ti.ticket_id
                                       AND o.order_status_code = 'Payment Completed'
                                       AND DATE(o.created_at) BETWEEN  #{dashboard.startDate} AND #{dashboard.endDate}
                GROUP BY sale_date, ti.ticket_id, t.ticket_name
                ORDER BY sale_date ASC, ti.ticket_id
                """;
    }


    public String todaySalesTicket(long exhibitionId){
        return """
                WITH ticket_ids AS (
                    SELECT id as ticket_id
                    from ticket as t
                    where exhibition_id = #{exhibitionId}
                    and t.is_buying = 1
                    and t.is_deleted = 0
                    and CURDATE() between t.start_date and t.end_date
                )
                SELECT
                    CURDATE() AS sale_date,
                    ti.ticket_id,
                    t.ticket_name,
                    t.ticket_type_code as ticket_type,
                    COALESCE(SUM(o.quantity), 0) AS total_quantity,
                    COALESCE(SUM(o.amount), 0) AS total_sales
                FROM ticket_ids ti
                         LEFT JOIN ticket AS t ON t.id = ti.ticket_id
                         LEFT JOIN ticket_order AS o ON o.ticket_id = ti.ticket_id
                    AND o.order_status_code = 'Payment Completed'
                    AND DATE(o.created_at) = CURDATE()
                GROUP BY ti.ticket_id, t.ticket_name
                ORDER BY ti.ticket_id
                """;
    }

    public String visitTotals(DashboardDTO dashboard){
        return """
                WITH RECURSIVE date_range AS (
                    SELECT #{dashboard.startDate} AS visit_date
                    UNION ALL
                    SELECT visit_date + INTERVAL 1 DAY FROM date_range
                    WHERE visit_date < #{dashboard.endDate}
                ),
                               daily_visits AS (
                                   SELECT
                                       d.visit_date,
                                       SUBSTRING_INDEX(c.referer, '/', 3) AS referer,
                                       COUNT(c.referer) AS daily_visit_count
                                   FROM date_range d
                                            LEFT JOIN connection_log c ON DATE(c.created_at) = d.visit_date
                                   GROUP BY d.visit_date, referer
                               ),
                               referer_totals AS (
                                   SELECT
                                       referer,
                                       SUM(daily_visit_count) AS total_visit_count
                                   FROM daily_visits
                                   GROUP BY referer
                               ),
                               top_10_referers AS (
                                   SELECT referer
                                   FROM referer_totals
                                   ORDER BY total_visit_count DESC
                                   LIMIT 10
                               )
                SELECT
                    CASE
                        WHEN rt.referer IN (SELECT referer FROM top_10_referers) THEN rt.referer
                        ELSE 'etc'
                        END AS grouped_referer,
                    SUM(rt.total_visit_count) AS total_visit_count
                FROM referer_totals rt
                GROUP BY grouped_referer
                ORDER BY
                    CASE WHEN grouped_referer = 'etc' THEN 1 ELSE 0 END ASC,
                    total_visit_count DESC
                """;
    }



    public String visitPlatformCount(){
        return """
                WITH RECURSIVE date_range AS (
                    SELECT CURDATE() AS visit_date
                    UNION ALL
                    SELECT visit_date - INTERVAL 1 DAY FROM date_range
                    WHERE visit_date > DATE_SUB(CURDATE(), INTERVAL 6 DAY)
                ),
                               platforms AS (
                                   -- 플랫폼 목록 및 고정된 정렬 순서 추가
                                   SELECT 'Google' AS platform, 1 AS platform_order UNION ALL
                                   SELECT 'FaceBook', 2 UNION ALL
                                   SELECT 'Instagram', 3 UNION ALL
                                   SELECT 'LinkTree', 4 UNION ALL
                                   SELECT 'SCA', 5 UNION ALL
                                   SELECT 'NCA', 6 UNION ALL
                                   SELECT 'WCC', 7 UNION ALL
                                   SELECT 'World Of Coffee Geneva', 8 UNION ALL
                                   SELECT 'Naver', 9 UNION ALL
                                   SELECT 'Daum', 10 UNION ALL
                                   SELECT 'etc', 11
                               )
                SELECT
                    d.visit_date,
                    p.platform,
                    COALESCE(COUNT(c.referer), 0) AS visit_count
                FROM date_range d
                         CROSS JOIN platforms p
                         LEFT JOIN connection_log c
                                   ON DATE(c.created_at) = d.visit_date
                                       AND (
                                          CASE
                                              WHEN c.referer LIKE '%google%' THEN 'Google'
                                              WHEN c.referer LIKE '%sca.coffee%' THEN 'SCA'
                                              WHEN c.referer LIKE '%wcc.coffee%' THEN 'WCC'
                                              WHEN c.referer LIKE '%worldofcoffee.org%' THEN 'World Of Coffee Geneva'
                                              WHEN c.referer LIKE '%facebook%' THEN 'FaceBook'
                                              WHEN c.referer LIKE '%instagram%' THEN 'Instagram'
                                              WHEN c.referer LIKE '%linktr%' THEN 'LinkTree'
                                              WHEN c.referer LIKE '%ncausa%' THEN 'NCA'
                                              WHEN c.referer LIKE '%naver%' THEN 'Naver'
                                              WHEN c.referer LIKE '%daum%' THEN 'Daum'
                                              ELSE 'etc'
                                              END = p.platform
                                          )
                GROUP BY d.visit_date, p.platform, p.platform_order
                ORDER BY d.visit_date DESC, p.platform_order ASC
                """;
    }
}
