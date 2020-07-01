package com.handcraft.mapper;

import com.handcraft.pojo.GroupTalkMsg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 群聊信息操作层
 *
 * @author Heilant Gong
 */
@Mapper
@Component
public interface GroupTalkMsgMapper {
    /**
     * 存储一条信息
     *
     * @param groupTalkMsg 群消息
     * @return 增加标记
     */
    int add(GroupTalkMsg groupTalkMsg);

    /**
     * 查询所有
     *
     * @return 课程信息List
     */
    List<GroupTalkMsg> queryAll();
}
