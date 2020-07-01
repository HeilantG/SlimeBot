package com.handcraft.mapper;

import com.handcraft.pojo.MsgTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 提醒信息操作类
 *
 * @author Heilant Gong
 */
@Mapper
@Repository
public interface MsgTimeMapper {
    /**
     * 查找所有信息
     *
     * @return 所有信息列表
     */
    List<MsgTime> queryAll();

    /**
     * 增加消息
     *
     * @param msgTime 提醒信息实体类
     * @return 增加标记
     */
    int insert(@Param("msgTime") MsgTime msgTime);

    /**
     * 删除指定消息
     *
     * @param msgTime 提醒信息实体类
     * @return 删除标记
     */
    int delete(@Param("msgTime") MsgTime msgTime);
}
