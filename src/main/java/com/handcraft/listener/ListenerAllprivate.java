package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.TianGou.CreateTianGouMsg;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.util.MsgCreate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;

@Component
public class ListenerAllprivate {
    CQCodeUtil cqCodeUtil = CQCodeUtil.build();

    @Resource
    MsgCreate msgCreate;
    @Resource
    PixivMsg pixivMsg;
    @Resource
    CreateTianGouMsg createTianGouMsg;
    //老黄历

    @Listen(MsgGetTypes.privateMsg)
    @Filter(value = {"来.*老黄历.*"})
    public void programmerCalendar(PrivateMsg msg, MsgSender sender) {
        int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String str = msgCreate.getProgrammerCalendar(i);
        sender.SENDER.sendPrivateMsg(msg, str);
    }

    //涩图

    @Listen(MsgGetTypes.privateMsg)
    @Filter(value = {"来.*涩图"})
    public void setu(PrivateMsg msg, MsgSender sender) {
        System.out.println(msg.getQQ() + msg.getId() + "请求了色图bot");
        sender.SENDER.sendPrivateMsg(msg, "开始定位涩图,可能因网络原因略有延迟(如果太久没消息就是挂了");
        String s = pixivMsg.getSeTu("348731155e9d5ed04a05b7", 0);
        CQCode cqCode_image = cqCodeUtil.getCQCode_Image(s);
        sender.SENDER.sendPrivateMsg(msg, cqCode_image.toString());
    }

    //天狗模式

    @Listen(MsgGetTypes.privateMsg)
    @Filter(value = {"舔我"})
    public void tianGou(PrivateMsg msg, MsgSender sender) {
        sender.SENDER.sendPrivateMsg(msg, createTianGouMsg.get());
    }

}
