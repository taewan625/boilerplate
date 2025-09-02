package com.exporum.core.domain.order.mapper;

import com.exporum.core.domain.order.model.Order;
import org.apache.ibatis.annotations.*;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 9.
 * @description :
 */

@Mapper
public interface OrderMapper {

    @InsertProvider(type = OrderSqlProvider.class, method = "insertOrder")
    int insertOrder(@Param("order") Order order);

    @SelectProvider(type = OrderSqlProvider.class, method = "getOrder")
    Order getOrder(String merchantUid);

    @UpdateProvider(type = OrderSqlProvider.class, method = "updateOrderStatus")
    int updateOrderStatus(@Param("merchantUid") String merchantUid, @Param("orderStatusCode") String orderStatusCode);
}
