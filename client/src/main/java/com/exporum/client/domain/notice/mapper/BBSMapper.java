package com.exporum.client.domain.notice.mapper;

import com.exporum.client.domain.notice.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 2.
 * @description :
 */

@Mapper
public interface BBSMapper {

    @SelectProvider(type = BBSSqlProvider.class, method = "getBBSList")
    List<BBSList> getBBSList(@Param("searchBBS") SearchBBS searchBBS);

    @SelectProvider(type = BBSSqlProvider.class, method = "getBBSCount")
    long getBBSCount(@Param("searchBBS") SearchBBS searchBBS);



    @SelectProvider(type = BBSSqlProvider.class, method = "getBBS")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "bbs_code", property = "bbsCode"),
            @Result(column = "id", property = "attachFiles", many = @Many(select = "getAttachFiles")),
            @Result(column = "{id=id, bbsCode=bbs_code}", property = "prePost", one = @One(select = "getPreBBS")),
            @Result(column = "{id=id, bbsCode=bbs_code}", property = "nextPost", one = @One(select = "getNextBBS"))
    })
    Post getBBS(@Param("id") long id);


    @SelectProvider(type = BBSSqlProvider.class, method = "getAttachFiles")
    List<PostAttachFile> getAttachFiles(@Param("id") long id);

    @SelectProvider(type = BBSSqlProvider.class, method = "getPreBBS")
    PreviousAndNextPost getPreBBS(@Param("id") long id, @Param("bbsCode") String bbsCode);
    @SelectProvider(type = BBSSqlProvider.class, method = "getNextBBS")
    PreviousAndNextPost getNextBBS(@Param("id")long id, @Param("bbsCode") String bbsCode);
}
