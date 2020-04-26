package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.TianGou.CreateTianGouMsg;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.util.MsgCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 *  公用监听器
 *  */

@Component
public class ListenerAllGroup {
    public CQCodeUtil cqCodeUtil = CQCodeUtil.build();


    //老黄历单独调用

    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"来.*老黄历.*"})
    public void programmerCalendar(GroupMsg msg, MsgSender sender) {
        String dayMsg = MsgCreate.getProgrammerCalendar(1);
        sender.SENDER.sendGroupMsg(msg, dayMsg);
    }

    //qq emj 测试

    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"emj.*"})
    public void img(GroupMsg msg, MsgSender sender) {
        CQCode cqCode = cqCodeUtil.getCQCode_Face("\\ue21c");
        sender.SENDER.sendGroupMsg(msg, cqCode.toString());
    }

    //涩图Time

    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"来.*涩图"})
    public void setu(GroupMsg msg, MsgSender sender) {
        System.out.println(msg.getQQ() + msg.getId() + "请求了色图bot");
        sender.SENDER.sendGroupMsg(msg, "开始定位涩图,可能因网络原因略有延迟(如果太久没回消息就是挂了");
        String s = PixivMsg.getSeTu("348731155e9d5ed04a05b7", 0);
        CQCode cqCode_image = cqCodeUtil.getCQCode_Image(s);
        sender.SENDER.sendGroupMsg(msg, cqCode_image.toString());
    }

    //天狗模式

    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"舔我"})
    public void tianGou(GroupMsg msg, MsgSender sender) {
        CQCode cqCode_at = cqCodeUtil.getCQCode_At(msg.getQQ());
        String str = cqCode_at.toString() + "\n" + CreateTianGouMsg.get();
        sender.SENDER.sendGroupMsg(msg, str);
    }
}
