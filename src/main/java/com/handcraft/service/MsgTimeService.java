package com.handcraft.service;

import com.handcraft.mapper.MsgTimeMapper;
import com.handcraft.pojo.MsgTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 提醒消息Service层
 *
 * @author Heilant Gong
 */
@Service
public class MsgTimeService {
    @Resource
    MsgTimeMapper msgTimeMapper;

    /**
     * 查找所有信息
     *
     * @return 所有信息列表
     */
    public List<MsgTime> queryAll() {
        return msgTimeMapper.queryAll();
    }

    /**
     * 增加消息
     *
     * @param msgTime 提醒信息实体类
     * @return 增加标记
     */
    public int insert(MsgTime msgTime) {
        return msgTimeMapper.insert(msgTime);
    }

    /**
     * 删除指定消息
     *
     * @param msgTime 提醒信息实体类
     * @return 删除标记
     */
    public int delete(MsgTime msgTime) {
        return msgTimeMapper.delete(msgTime);
    }
}
