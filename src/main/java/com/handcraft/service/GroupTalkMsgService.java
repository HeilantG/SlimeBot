package com.handcraft.service;

import com.handcraft.mapper.GroupTalkMsgMapper;
import com.handcraft.pojo.GroupTalkMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 群消息存储Service层
 *
 * @author Heilant Gong
 */
@Service
public class GroupTalkMsgService {
    @Resource
    GroupTalkMsgMapper groupTalkMsgMapper;

    /**
     * 存储一条信息
     *
     * @param groupTalkMsg 群消息
     * @return 增加标记
     */
    public int add(GroupTalkMsg groupTalkMsg) {
        return groupTalkMsgMapper.add(groupTalkMsg);
    }

    /**
     * 查询所有
     *
     * @return 课程信息List
     */
    public List<GroupTalkMsg> queryAll() {
        return groupTalkMsgMapper.queryAll();
    }
}
