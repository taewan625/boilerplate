package com.exporum.admin.domain.payment.mapper;

import com.exporum.admin.domain.payment.model.PageablePayment;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */
public class PaymentManageSqlProvider {

    public String getPaymentExcel(long exhibitionId) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY pay.id asc) AS no,
                        pay.merchant_uid, pay.imp_uid, pay.status,
                        pay.name, pay.pay_method, pay.card_name, pay.paid_amount, pay.currency,
                        pay.buyer_email, pay.buyer_name, pay.paid_at, pay.failed_at, pay.fail_reason, pay.cancelled_at, pay.cancel_reason
                        """);
                FROM("""
                        (
                             SELECT
                                 *,
                                 ROW_NUMBER() OVER (
                                     PARTITION BY merchant_uid
                                     ORDER BY
                                         CASE status
                                             WHEN 'failed' THEN 1  -- 취소된 데이터가 우선
                                             WHEN 'cancelled' THEN 2  -- 취소된 데이터가 우선
                                             WHEN 'paid' THEN 2       -- 결제된 데이터가 그다음
                                             ELSE 3
                                             END ASC,
                                         id DESC -- 같은 주문번호 내에서 가장 최신 데이터 우선
                                     ) AS rn
                             FROM payment
                        ) as pay
                        """);
                JOIN("ticket_order as o on pay.merchant_uid = o.merchant_uid");
                JOIN("ticket as t on o.ticket_id = t.id");
                WHERE("pay.rn = 1");
                WHERE("t.exhibition_id = #{exhibitionId}");
                ORDER_BY("pay.id asc");
            }
        }.toString();
    }


    public String getPaymentList(PageablePayment search) {
        return new SQL(){
            {
                SELECT("*");
                FROM(STR."""
                        ( \{new SQL() {
                        {
                            SELECT("""
                                        ROW_NUMBER() OVER (ORDER BY pay.id asc) AS no,
                                        pay.id,
                                        pay.merchant_uid, pay.imp_uid, pay.status,
                                        pay.name as order_name, pay.pay_method as method, pay.paid_amount as amount, pay.currency,
                                        pay.buyer_email as email, pay.buyer_name as name,
                                        case when pay.status = 'failed' then pay.failed_at
                                                when pay.status = 'cancelled' then pay.cancelled_at
                                        else pay.paid_at end as status_approval_date
                                    """);
                            FROM("""
                                    (
                                             SELECT
                                                 *,
                                                 ROW_NUMBER() OVER (
                                                     PARTITION BY merchant_uid
                                                     ORDER BY
                                                         CASE status
                                                             WHEN 'failed' THEN 1
                                                             WHEN 'cancelled' THEN 2
                                                             WHEN 'paid' THEN 3
                                                             ELSE 4
                                                             END ASC,
                                                         id DESC
                                                     ) AS rn
                                             FROM payment
                                         ) AS pay
                                    """);
                            JOIN("ticket_order as o on pay.merchant_uid = o.merchant_uid");
                            JOIN("ticket as t on o.ticket_id = t.id");
                            WHERE("pay.rn = 1");
                            WHERE("t.exhibition_id = #{search.exhibitionId}");
                            if ("P".equals(search.getStatus())) {
                                WHERE("pay.status = 'paid'");
                            }
                            if ("F".equals(search.getStatus())) {
                                WHERE("pay.status = 'failed'");
                            }
                            if ("C".equals(search.getStatus())) {
                                WHERE("pay.status = 'cancelled'");
                            }
                            ORDER_BY("pay.id desc");
                        }}.toString()
                        } ) as tt
                    """);
                WHERE("TO_CHAR(tt.status_approval_date, 'YYYY-MM-DD') between #{search.startDate} and #{search.endDate}");
                LIMIT(search.getLength());
                OFFSET(search.getStart());
            }
        }.toString();
    }


    public String getPaymentCount(PageablePayment search) {
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM(STR."""
                       ( \{new SQL() {
                    {
                        SELECT("""
                                        ROW_NUMBER() OVER (ORDER BY pay.id asc) AS no,
                                        pay.id,
                                        pay.merchant_uid, pay.imp_uid, pay.status,
                                        pay.name as order_name, pay.pay_method as method, pay.paid_amount as amount, pay.currency,
                                        case when pay.status = 'failed' then pay.failed_at
                                                when pay.status = 'cancelled' then pay.cancelled_at
                                        else pay.paid_at end as status_approval_date
                                    """);
                        FROM("""
                                    (
                                             SELECT
                                                 *,
                                                 ROW_NUMBER() OVER (
                                                     PARTITION BY merchant_uid
                                                     ORDER BY
                                                         CASE status
                                                             WHEN 'failed' THEN 1
                                                             WHEN 'cancelled' THEN 2
                                                             WHEN 'paid' THEN 3
                                                             ELSE 4
                                                             END ASC,
                                                         id DESC
                                                     ) AS rn
                                             FROM payment
                                         ) AS pay
                                    """);
                        JOIN("ticket_order as o on pay.merchant_uid = o.merchant_uid");
                        JOIN("ticket as t on o.ticket_id = t.id");
                        WHERE("pay.rn = 1");
                        WHERE("t.exhibition_id = #{search.exhibitionId}");
                        if ("P".equals(search.getStatus())) {
                            WHERE("pay.status = 'paid'");
                        }
                        if ("F".equals(search.getStatus())) {
                            WHERE("pay.status = 'failed'");
                        }
                        if ("C".equals(search.getStatus())) {
                            WHERE("pay.status = 'cancelled'");
                        }
                    }}.toString()
                        } ) as tt
                    """);
                WHERE("TO_CHAR(tt.status_approval_date, 'YYYY-MM-DD') between #{search.startDate} and #{search.endDate}");
            }
        }.toString();
    }
}
