package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.TianGou.CreateTianGouMsg;
import com.handcraft.features.iptk.IptkBotTalk;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.features.share.ShareFormat;
import com.handcraft.mapper.ImgMapper;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.ImgDownload;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/*
 *  公用监听器
 *  */

@Component
@Listen(MsgGetTypes.groupMsg)
public class ListenerAllGroup {
    private CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    @Resource
    MsgCreate msgCreate;
    @Resource
    PixivMsg pixivMsg;
    @Resource
    CreateTianGouMsg createTianGouMsg;
    @Resource
    ImgDownload imgDownload;
    @Resource
    IptkBotTalk iptkBotTalk;
    @Resource
    ShareFormat shareFormat;
    @Resource
    StringUtil stringUtil;
    @Resource
    ImgMapper imgMapper;

    //解析分享
    @Filter(value = {".*CQ:rich.*"})
    public void share(GroupMsg msg, MsgSender sender) {
        List<String> format = shareFormat.format(msg.getMsg());
        StringBuffer sb = new StringBuffer();
        System.out.println(format.get(2));
        String local = imgDownload.biliDownload(format.get(2),null, stringUtil.getUUID());
        sb.append(cqCodeUtil.getCQCode_Image(local));
        sb.append("title:" + format.get(0) + "\n");
        sb.append("url:" + format.get(1));
        sender.SENDER.sendGroupMsg(msg, sb.toString());
    }

    //闲聊
    @Filter(value = {"[ \f\r\t\n].*"})
    public void iptkTalk(GroupMsg msg, MsgSender sender) {
        System.out.println("talk->" + msg);
        sender.SENDER.sendGroupMsg(msg, iptkBotTalk.getTalk(msg.getMsg().substring(1)));
    }

    //菜单
    @Filter(value = {".*菜单", ".*你会什么"}, at = true)
    public void menu(GroupMsg msg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(msg, msgCreate.getMenu());
    }


    //老黄历单独调用

    @Filter(value = {"来.*老黄历.*"})
    public void programmerCalendar(GroupMsg msg, MsgSender sender) {
        String dayMsg = msgCreate.getProgrammerCalendar(1);
        sender.SENDER.sendGroupMsg(msg, dayMsg);
    }

    //qq emj 测试

    @Filter(value = {"emj.*"})
    public void img(GroupMsg msg, MsgSender sender) {
        CQCode cqCode = cqCodeUtil.getCQCode_Face("\\ue21c");
        sender.SENDER.sendGroupMsg(msg, cqCode.toString());
    }

    //涩图Time

    @Filter(value = {"来.*涩图"})
    public void setu(GroupMsg msg, MsgSender sender) {
        ImgInfo seTu = pixivMsg.getSeTu("348731155e9d5ed04a05b7", 0);
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
            sender.SENDER.sendGroupMsg(msg, cqCodeLocal.toString());
        }
    }

    //天狗模式

    @Filter(value = {"舔我"})
    public void tianGou(GroupMsg msg, MsgSender sender) {
        CQCode cqCode_at = cqCodeUtil.getCQCode_At(msg.getQQ());
        String str = cqCode_at.toString() + "\n" + createTianGouMsg.get();
        sender.SENDER.sendGroupMsg(msg, str);
    }


}
