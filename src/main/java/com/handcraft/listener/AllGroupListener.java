package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.template.OnGroup;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.api.CreateApiMsg;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.features.share.ShareFormat;
import com.handcraft.mapper.ImgInfoMapper;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.ImgDownload;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import com.simplerobot.modules.utils.KQCodeUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * 公用群组监听器
 * 这里是公用监听器 对所有群开放
 *
 * @author Heilant Gong
 *
 * {@link OnGroup} 此类为监听所有监听群消息的方法
 *
 * <p>方法作用说明
 * {@link this#share(GroupMsg, MsgSender)} 解析qq的小程序/分享 还原原始链接
 * this#iptkTalk(GroupMsg, MsgSender) 闲聊(暂时禁用
 * {@link this#menu(GroupMsg, MsgSender)} 机器人菜单
 * {@link this#programmerCalendar(GroupMsg, MsgSender)} 主动获取老黄历
 * {@link this#emj(GroupMsg, MsgSender)} 发送一个emj
 * {@link this#sexImg(GroupMsg, MsgSender)} 获取一张涩图
 * {@link this#sweet(GroupMsg, MsgSender)} 舔/甜/毒狗模式
 */
@Component
@OnGroup
public class AllGroupListener {

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
    ShareFormat shareFormat;
    @Resource
    StringUtil stringUtil;
    @Resource
    ImgInfoMapper imgInfoMapper;


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

    /*@Filter(value = {"[ \f\r\t\n].*"})
    public void iptkTalk(GroupMsg msg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(msg, iptkBotTalk.getTalk(msg.getMsg().substring(1)));
    }*/

    @Filter(value = {".*菜单", ".*你会什么"}, at = true)
    public void menu(GroupMsg msg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(msg, msgCreate.getMenu());
    }


    @Filter(value = {"来.*老黄历.*"})
    public void programmerCalendar(GroupMsg msg, MsgSender sender) {
        String dayMsg = msgCreate.getProgrammerCalendar(1);
        sender.SENDER.sendGroupMsg(msg, dayMsg);
    }


    @Filter(value = {"emj.*"})
    public void emj(GroupMsg msg, MsgSender sender) {
        CQCode cqCode = cqCodeUtil.getCQCode_Face("179");
        sender.SENDER.sendGroupMsg(msg, cqCode.toString());
    }


    @Filter(value = {"来.*[色|涩]图"})
    public void sexImg(GroupMsg msg, MsgSender sender) {
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
            System.out.println(seTu.toString());
            imgDownload.download(seTu.getImageUrl(), null, seTu.getUuid());
            imgInfoMapper.addImg(seTu);
            cqCodeLocal.append(cqCodeUtil.getCQCode_Image(System.getProperty("user.dir") + "\\image\\" + seTu.getUuid() + seTu.getFormat()).toString() + "\n");
            cqCodeLocal.append("标题:" + seTu.getTitle() + "\n");
            cqCodeLocal.append("P站ID:" + seTu.getId());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            sender.SENDER.sendGroupMsg(msg, cqCodeLocal.toString());
        }
    }


    @Filter(value = {".我"})
    public void sweet(GroupMsg msg, MsgSender sender) {
        CQCode cqCode_at = cqCodeUtil.getCQCode_At(msg.getQQ());
        KQCodeUtils.INSTANCE.toCq("at", "qq=" + msg.getQQ());
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

    /**
     * 消息例子: 甜他 @XXX
     */
    @Filter(value = {"[舔,甜,毒][她,他,它].*"})
    public void sweetAt(GroupMsg msg, MsgSender sender) {
        String msgStr = msg.getMsg();
        //获取At的cq码 然后直接使用就可以了
        String at = msgStr.substring(msgStr.indexOf("["), msgStr.indexOf("]") + 1);
        //信息标识
        String markStr;
        try {
            markStr = msgStr.substring(0, 1);
        } catch (Exception e) {
            return;
        }
        String sendMsg;
        switch (markStr) {
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
        sender.SENDER.sendGroupMsg(msg, at + " " + sendMsg);
    }


}