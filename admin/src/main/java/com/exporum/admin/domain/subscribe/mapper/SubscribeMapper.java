package com.exporum.admin.domain.subscribe.mapper;

import com.exporum.admin.domain.subscribe.model.PageableSubscribe;
import com.exporum.admin.domain.subscribe.model.SubscribeDTO;
import com.exporum.admin.domain.subscribe.model.SubscribeExcel;
import com.exporum.admin.domain.subscribe.model.SubscribeList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */

@Mapper
public interface SubscribeMapper {

    @SelectProvider(type = SubscribeSqlProvider.class, method = "getSubscribeList")
    List<SubscribeList> getSubscribeList(@Param("subscribe")PageableSubscribe subscribe);

    @SelectProvider(type = SubscribeSqlProvider.class, method = "getSubscribeCount")
    long getSubscribeCount(@Param("subscribe")PageableSubscribe subscribe);

    @SelectProvider(type = SubscribeSqlProvider.class, method = "getSubscribeExcel")
    List<SubscribeExcel> getSubscribeExcel();

    @UpdateProvider(type = SubscribeSqlProvider.class, method = "updateSubscribe")
    int updateSubscribe(@Param("id") long id, @Param("subscribe") SubscribeDTO subscribe);
}
