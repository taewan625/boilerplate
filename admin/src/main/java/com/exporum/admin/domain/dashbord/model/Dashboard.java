package com.exporum.admin.domain.dashbord.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 25.
 * @description :
 */

@Getter
@Setter
public class Dashboard {

    private List<SalesTicket> totalSales;
    private List<SalesTicket> todaySales;
    private List<SalesTicket> dateRangeSales;
    private List<SalesTicket> weekSales;
    private List<PlatformCount> weekVisitPlatform;
    private List<TotalPlatformCount> visitTotals;


    @Builder
    public Dashboard(List<SalesTicket> totalSales, List<SalesTicket> todaySales, List<SalesTicket> dateRangeSales, List<SalesTicket> weekSales,
                     List<PlatformCount> weekVisitPlatform, List<TotalPlatformCount> visitTotals) {
        this.totalSales = totalSales;
        this.todaySales = todaySales;
        this.dateRangeSales = dateRangeSales;
        this.weekSales = weekSales;
        this.weekVisitPlatform = weekVisitPlatform;
        this.visitTotals = visitTotals;
    }
}
