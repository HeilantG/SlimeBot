package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * @author HeilantG
 * 这是挥手的专用监听器
 * groupId = "361081715";
 */
@Beans
public class ListenerForHuiShou {

    public CQCodeUtil cqCodeUtil = CQCodeUtil.build();

    //发送表情

    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = "img.*", group = {"361081715"})
    public void img(GroupMsg msg, MsgSender sender) {
        CQCode cqCode = cqCodeUtil.getCQCode_Face("\\ue21c");
        sender.SENDER.sendGroupMsg(msg, cqCode.toString());
    }


}
