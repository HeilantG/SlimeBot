package com.handcraft.mapper;

import com.handcraft.pojo.MsgTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MsgTimeMapper {
    List<MsgTime> queryAll();

    int insert(@Param("msgTime") MsgTime msgTime);

    int delete(@Param("msgTime") MsgTime msgTime);
}
