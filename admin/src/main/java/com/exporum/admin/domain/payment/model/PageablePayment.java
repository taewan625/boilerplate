package com.exporum.admin.domain.payment.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Getter
@Setter
public class PageablePayment extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;

    private long exhibitionId;
    private String status;
    private String startDate;
    private String endDate;

    List<PaymentList> data;

    public PageablePayment(){}

    public PageablePayment(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageablePayment(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageablePayment(List<PaymentList> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}
