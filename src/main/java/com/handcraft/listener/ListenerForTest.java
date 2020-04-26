package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.util.MsgCreate;

/**
 * @author HeilantG
 * groupId = "816924822"
 */
@Beans
public class ListenerForTest {
    public CQCodeUtil cqCodeUtil = CQCodeUtil.build();

 /*   @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"emj.*"}, group = {"190375193"})
    public void img(GroupMsg msg, MsgSender sender) {
        CQCode cqCode_atAll = cqCodeUtil.getCQCode_AtAll();
        sender.SENDER.sendGroupMsg(msg, cqCode_atAll.toString());
    }*/

    @Listen(MsgGetTypes.groupMsg)
    @Filter(group = {"190375193"}, value = {"hello.*"})
    public void today(GroupMsg groupMsg, MsgSender sender) {
        String msg = MsgCreate.getDayMsg();
    }
}
