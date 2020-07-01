package com.handcraft.mapper;

import com.handcraft.pojo.PrivateTalkMsg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 私聊消息操作类
 *
 * @author Heilant Gong
 */
@Mapper
@Component
public interface PrivateTalkMsgMapper {
    /**
     * 存入消息
     *
     * @param privateTalkMsg 消息内容
     * @return 增加标记
     */
    int add(PrivateTalkMsg privateTalkMsg);

    /**
     * 查询所有
     *
     * @return 消息List
     */
    List<PrivateTalkMsgMapper> queryAll();
}
