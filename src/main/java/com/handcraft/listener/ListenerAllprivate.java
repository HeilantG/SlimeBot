package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.api.CreateApiMsg;
import com.handcraft.features.iptk.IptkBotTalk;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.mapper.ImgMapper;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.ImgDownload;
import com.handcraft.util.MsgCreate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;

@Component
@Listen(MsgGetTypes.privateMsg)
public class ListenerAllprivate {
    CQCodeUtil cqCodeUtil = CQCodeUtil.build();

    @Resource
    MsgCreate msgCreate;
    @Resource
    PixivMsg pixivMsg;
    @Resource
    ImgDownload imgDownload;
    @Resource
    CreateApiMsg createTianGouMsg;
    @Resource
    IptkBotTalk iptkBotTalk;
    @Resource
    ImgMapper imgMapper;

    //闲聊
    @Filter(value = {"[ \f\r\t\n].*"})
    public void iptkTalk(PrivateMsg msg, MsgSender sender) {
        System.out.println("talk->" + msg);
        sender.SENDER.sendPrivateMsg(msg, iptkBotTalk.getTalk(msg.getMsg().substring(1)));
    }

    //查题
    @Filter(value = {"查题:.*", "查题：.*"})
    public void getAnswer(PrivateMsg msg, MsgSender sender) {
        sender.SENDER.sendPrivateMsg(msg, "答案为:" + msgCreate.getAnswer(msg.getMsg().substring(3)));
    }

    //菜单
    @Filter(value = {".*菜单", "你会什么"})
    public void menu(PrivateMsg msg, MsgSender sender) {
        sender.SENDER.sendPrivateMsg(msg, msgCreate.getMenu());
    }

    //老黄历

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
        ImgInfo seTu = pixivMsg.getSeTu("348731155e9d5ed04a05b7", 0);

        //System.out.println("开始下载");
        /*
         * local:
         * C:\Users\HeilantG\Desktop\酷Q Pro\data\image\
         * Server:
         * C:\Users\Administrator\Desktop\酷Q Pro\data\image\
         * */

        StringBuffer cqCodeLocal = new StringBuffer();
        try {
            imgDownload.download(seTu.getImageUrl(), null, seTu.getUuid());
            imgMapper.addImg(seTu);
            cqCodeLocal.append(cqCodeUtil.getCQCode_Image(seTu.getUuid() + seTu.getFormat()).toString() + "\n");
            cqCodeLocal.append("标题:" + seTu.getTitle() + "\n");
            cqCodeLocal.append("P站ID:" + seTu.getId());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            sender.SENDER.sendPrivateMsg(msg, cqCodeLocal.toString());
        }
        // System.out.println(cqCodeLocal);
    }

    //嘴甜

    @Filter(value = {"舔我"})
    public void sweet(PrivateMsg msg, MsgSender sender) {
        sender.SENDER.sendPrivateMsg(msg, createTianGouMsg.getSweet());
    }

    //毒鸡汤

    @Filter(value = {"毒我"})
    public void poisonousChickenSoup(PrivateMsg msg, MsgSender sender) {
        sender.SENDER.sendPrivateMsg(msg, createTianGouMsg.getSweet());
    }

}
