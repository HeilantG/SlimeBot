package com.handcraft.service;

import com.handcraft.mapper.PrivateTalkMsgMapper;
import com.handcraft.pojo.PrivateTalkMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Heilant Gong
 */
@Service
public class PrivateTalkMsgService {
    @Resource
    PrivateTalkMsgMapper privateTalkMsgMapper;

    public int add(PrivateTalkMsg privateTalkMsg) {
        return privateTalkMsgMapper.add(privateTalkMsg);
    }

    public List<PrivateTalkMsgMapper> queryAll() {
        return privateTalkMsgMapper.queryAll();
    }
}
