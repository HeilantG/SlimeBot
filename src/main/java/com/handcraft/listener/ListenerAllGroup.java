package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.api.CreateApiMsg;
import com.handcraft.features.iptk.IptkBotTalk;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.features.share.ShareFormat;
import com.handcraft.mapper.ImgInfoMapper;
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
    CreateApiMsg createApiMsg;
    @Resource
    ImgDownload imgDownload;
    @Resource
    IptkBotTalk iptkBotTalk;
    @Resource
    ShareFormat shareFormat;
    @Resource
    StringUtil stringUtil;
    @Resource
    ImgInfoMapper imgInfoMapper;

    //解析分享
    @Filter(value = {".*CQ:rich.*"})
    public void share(GroupMsg msg, MsgSender sender) {
        List<String> format = shareFormat.format(msg.getMsg());
        StringBuffer sb = new StringBuffer();
        String local = imgDownload.biliDownload(format.get(2), null, stringUtil.getUUID());
        sb.append(cqCodeUtil.getCQCode_Image(local) + "\n");
        sb.append("标题: " + format.get(0) + "\n");
        sb.append("链接: " + format.get(1));
        sender.SENDER.sendGroupMsg(msg, sb.toString());
    }

    //闲聊
    /*@Filter(value = {"[ \f\r\t\n].*"})
    public void iptkTalk(GroupMsg msg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(msg, iptkBotTalk.getTalk(msg.getMsg().substring(1)));
    }*/

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

    @Filter(value = {"来.*[色|涩]图"})
    public void setu(GroupMsg msg, MsgSender sender) {
        StringBuffer cqCodeLocal = new StringBuffer();
        if (msg.getGroupCode().equals("175183084")) {
            sender.SENDER.sendGroupMsg(msg, "别看涩图了,作业写了吗,妹子谈了嘛,没有你还在这看涩图");
            return;
        }
        String msgStr = msg.getMsg();
        ImgInfo seTu = pixivMsg.getSeTu("348731155e9d5ed04a05b7", msgStr.substring(2, msgStr.length() - 2), 0);
        if (seTu.getId().equals("0")) {
            cqCodeLocal.append("虽然没有指定类别的涩图，但是我找到了别的好康的\n");
            seTu = pixivMsg.getSeTu("348731155e9d5ed04a05b7", "", 0);
        }
        try {
            imgDownload.download(seTu.getImageUrl(), null, seTu.getUuid());
            imgInfoMapper.addImg(seTu);
            cqCodeLocal.append(cqCodeUtil.getCQCode_Image(seTu.getUuid() + seTu.getFormat()).toString() + "\n");
            cqCodeLocal.append("标题:" + seTu.getTitle() + "\n");
            cqCodeLocal.append("P站ID:" + seTu.getId());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            sender.SENDER.sendGroupMsg(msg, cqCodeLocal.toString());
        }
    }

    //嘴甜模式

    @Filter(value = {".我"})
    public void sweet(GroupMsg msg, MsgSender sender) {
        CQCode cqCode_at = cqCodeUtil.getCQCode_At(msg.getQQ());
        String sendMsg;
        switch (msg.getMsg().substring(0, 1)) {
            case "舔":
                sendMsg = createApiMsg.getTianGou();
                sendMsg = sendMsg.substring(0, sendMsg.length() - 1);
                break;
            case "甜":
                sendMsg = createApiMsg.getSweet();
                break;
            case "毒":
                sendMsg = createApiMsg.getPoisonousChickenSoup();
                break;
            default:
                return;
        }
        sender.SENDER.sendGroupMsg(msg, cqCode_at + " " + sendMsg);
    }
    //定向嘴甜模式

    @Filter(value = {".[她,他].*"})
    public void sweetAt(GroupMsg msg, MsgSender sender) {
        String msgStr = msg.getMsg();
        CQCode cqCode_at = cqCodeUtil.getCQCode_At(msgStr.substring(msgStr.indexOf("qq=") + 3, msgStr.length() - 1));
        String sendMsg;
        switch (msg.getMsg().substring(0, 1)) {
            case "舔":
                sendMsg = createApiMsg.getTianGou();
                sendMsg = sendMsg.substring(0, sendMsg.length() - 1);
                break;
            case "甜":
                sendMsg = createApiMsg.getSweet();
                break;
            case "毒":
                sendMsg = createApiMsg.getPoisonousChickenSoup();
                break;
            default:
                return;
        }
        sender.SENDER.sendGroupMsg(msg, cqCode_at + " " + sendMsg);
    }


}
