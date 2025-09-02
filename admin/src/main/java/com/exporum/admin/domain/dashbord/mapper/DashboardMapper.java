package com.exporum.admin.domain.dashbord.mapper;

import com.exporum.admin.domain.dashbord.model.DashboardDTO;
import com.exporum.admin.domain.dashbord.model.PlatformCount;
import com.exporum.admin.domain.dashbord.model.SalesTicket;
import com.exporum.admin.domain.dashbord.model.TotalPlatformCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 25.
 * @description :
 */

@Mapper
public interface DashboardMapper {

    @SelectProvider(type = DashboardSqlProvider.class, method = "totalSalesTicket")
    List<SalesTicket> totalSalesTicket(@Param("exhibitionId")long exhibitionId);
    @SelectProvider(type = DashboardSqlProvider.class, method = "todaySalesTicket")
    List<SalesTicket> todaySalesTicket(@Param("exhibitionId")long exhibitionId);


    @SelectProvider(type = DashboardSqlProvider.class, method = "dateRangeSalesTicket")
    List<SalesTicket> dateRangeSalesTicket(@Param("exhibitionId")long exhibitionId, @Param("dashboard") DashboardDTO dashboard);



    @SelectProvider(type = DashboardSqlProvider.class, method = "weekSalesTicket")
    List<SalesTicket> weekSalesTicket(@Param("exhibitionId")long exhibitionId, @Param("dashboard") DashboardDTO dashboard);


    @SelectProvider(type = DashboardSqlProvider.class, method = "visitPlatformCount")
    List<PlatformCount> visitPlatformCount();
    @SelectProvider(type = DashboardSqlProvider.class, method = "visitTotals")
    List<TotalPlatformCount> visitTotals(@Param("dashboard") DashboardDTO dashboard);
}
