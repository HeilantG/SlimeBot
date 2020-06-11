package com.handcraft.service;

import com.handcraft.mapper.MsgTimeMapper;
import com.handcraft.pojo.MsgTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MsgTimeService {
    @Resource
    MsgTimeMapper msgTimeMapper;

    public List<MsgTime> queryAll() {
        return msgTimeMapper.queryAll();
    }

    public int insert(MsgTime msgTime) {
        return msgTimeMapper.insert(msgTime);
    }

    public int delete(MsgTime msgTime) {
        return msgTimeMapper.delete(msgTime);
    }
}
