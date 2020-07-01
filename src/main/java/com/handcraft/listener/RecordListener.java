package com.handcraft.listener;

import com.forte.qqrobot.anno.template.OnGroup;
import com.forte.qqrobot.anno.template.OnPrivate;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.handcraft.pojo.GroupTalkMsg;
import com.handcraft.pojo.PrivateTalkMsg;
import com.handcraft.service.GroupTalkMsgService;
import com.handcraft.service.PrivateTalkMsgService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息记录
 *
 * @author Heilant Gong
 * {@link this#recordPrivate(PrivateMsg)} 记录私聊消息
 * {@link this#recordGroup(GroupMsg)} 记录群聊消息
 */
@Component
public class RecordListener {
    @Resource
    PrivateTalkMsgService privateTalkMsgService;
    @Resource
    GroupTalkMsgService groupTalkMsgService;
    @Resource
    PrivateTalkMsg privateTalkMsg;
    @Resource
    GroupTalkMsg groupTalkMsg;

    @OnPrivate
    public void recordPrivate(PrivateMsg privateMsg) {
        privateTalkMsg.setMsg(privateMsg.getMsg()).setQqCode(privateMsg.getQQCode());
        privateTalkMsgService.add(privateTalkMsg);
    }

    @OnGroup
    public void recordGroup(GroupMsg groupMsg) {
        groupTalkMsg.setMsg(groupMsg.getMsg()).setGroupCode(groupMsg.getGroupCode()).setQqCode(groupMsg.getQQCode());
        groupTalkMsgService.add(groupTalkMsg);
    }
}
