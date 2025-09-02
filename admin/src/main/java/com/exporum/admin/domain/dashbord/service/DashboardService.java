package com.exporum.admin.domain.dashbord.service;

import com.exporum.admin.domain.dashbord.mapper.DashboardMapper;
import com.exporum.admin.domain.dashbord.model.*;
import com.exporum.core.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 25.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardMapper dashboardMapper;

    public Dashboard getDashboardData(long exhibitionId, DashboardDTO dashboard) {
        return Dashboard.builder()
                .todaySales(todaySalesTicket(exhibitionId))
                .totalSales(totalSalesTicket(exhibitionId))
                .dateRangeSales(dateRangeSalesTicket(exhibitionId, dashboard))
                .weekSales(weekSalesTicket(exhibitionId, dashboard))
                .visitTotals(visitTotals(dashboard))
                .build();
    }


    private List<SalesTicket> totalSalesTicket(long exhibitionId){
        return dashboardMapper.totalSalesTicket(exhibitionId);
    }

    private List<SalesTicket>  todaySalesTicket(long exhibitionId){
        return dashboardMapper.todaySalesTicket(exhibitionId);
    }
    private List<SalesTicket>  dateRangeSalesTicket(long exhibitionId,DashboardDTO dashboard){
        return dashboardMapper.dateRangeSalesTicket(exhibitionId, dashboard);
    }

    private List<SalesTicket> weekSalesTicket(long exhibitionId, DashboardDTO dashboard){
        return dashboardMapper.weekSalesTicket(exhibitionId, dashboard);
    }

    private List<PlatformCount> visitPlatformCount(){
        return dashboardMapper.visitPlatformCount();
    }

    private List<TotalPlatformCount> visitTotals(DashboardDTO dashboard){
        return dashboardMapper.visitTotals(dashboard);
    }
}
