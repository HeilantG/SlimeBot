package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.template.OnPrivate;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.api.CreateApiMsg;
import com.handcraft.features.baiduyun.YunGet;
import com.handcraft.features.chaoxing.GetAnswer;
import com.handcraft.features.iptk.IptkBotTalk;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.mapper.ImgInfoMapper;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.ImgDownload;
import com.handcraft.util.MsgCreate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Map;


/**
 * 公用私聊监听器
 * 这里是公用监听器 对所有群开放
 *
 * @author Heilant Gong
 * <p>
 * {@link OnPrivate} 此类为监听所有细聊消息的方法
 *
 * <p>方法作用说明
 * {@link this#iptkTalk(PrivateMsg, MsgSender)} 闲聊模式 很蠢
 * {@link this#getAnswer(PrivateMsg, MsgSender)} 查题
 * {@link this#menu(PrivateMsg, MsgSender)} 菜单
 * {@link this#programmerCalendar(PrivateMsg, MsgSender)} 程序员的老黄历
 * {@link this#sexImg(PrivateMsg, MsgSender)} 涩图
 * {@link this#sweet(PrivateMsg, MsgSender)} 舔/甜/毒狗模式
 * {@link this#getLink(PrivateMsg, MsgSender)} 百度云解析
 */

@Component
@OnPrivate
public class AllPrivateListener {
    CQCodeUtil cqCodeUtil = CQCodeUtil.build();

    @Resource
    MsgCreate msgCreate;
    @Resource
    PixivMsg pixivMsg;
    @Resource
    ImgDownload imgDownload;
    @Resource
    CreateApiMsg createApiMsg;
    @Resource
    IptkBotTalk iptkBotTalk;
    @Resource
    GetAnswer getAnswer;
    @Resource
    ImgInfoMapper imgInfoMapper;
    @Resource
    YunGet yunGet;

    @Filter(value = {"[ \f\r\t\n].*"})
    public void iptkTalk(PrivateMsg msg, MsgSender sender) {
        sender.SENDER.sendPrivateMsg(msg, iptkBotTalk.getTalk(msg.getMsg().substring(1)));
    }

    @Filter(value = {"查题:.*", "查题：.*"})
    public void getAnswer(PrivateMsg msg, MsgSender sender) {
        sender.SENDER.sendPrivateMsg(msg, "答案为:" + getAnswer.get(msg.getMsg().substring(3)));
    }

    @Filter(value = {".*菜单", "你会什么"})
    public void menu(PrivateMsg msg, MsgSender sender) {
        sender.SENDER.sendPrivateMsg(msg, msgCreate.getMenu());
    }

    @Filter(value = {"来.*老黄历.*"})
    public void programmerCalendar(PrivateMsg msg, MsgSender sender) {
        int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String str = msgCreate.getProgrammerCalendar(i);
        sender.SENDER.sendPrivateMsg(msg, str);
    }

    @Listen(MsgGetTypes.privateMsg)
    @Filter(value = {"来.*涩图"})
    public void sexImg(PrivateMsg msg, MsgSender sender) {
        ImgInfo seTu = pixivMsg.getSeTu("348731155e9d5ed04a05b7", "", 0);
        StringBuffer cqCodeLocal = new StringBuffer();
        try {
            imgDownload.download(seTu.getImageUrl(), null, seTu.getUuid());
            imgInfoMapper.addImg(seTu);
            cqCodeLocal.append(cqCodeUtil.getCQCode_Image(seTu.getUuid() + seTu.getFormat()).toString() + "\n");
            cqCodeLocal.append("标题:" + seTu.getTitle() + "\n");
            cqCodeLocal.append("P站ID:" + seTu.getId());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            sender.SENDER.sendPrivateMsg(msg, cqCodeLocal.toString());
        }
    }

    @Filter(value = {".我"})
    public void sweet(PrivateMsg msg, MsgSender sender) {
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
        sender.SENDER.sendPrivateMsg(msg, sendMsg);
    }

    //百度云解析
    @Filter(value = {"share=.*"})
    public void getLink(PrivateMsg msg, MsgSender sender) {
        String msgStr = msg.getMsg();
        String link = null;
        String pwd = null;
        try {
            link = msgStr.substring(msgStr.indexOf("share=") + 6, msgStr.indexOf("pwd") - 1);
            pwd = msgStr.substring(msgStr.indexOf("pwd") + 4);
        } catch (Exception e) {
            sender.SENDER.sendPrivateMsg(msg, "输入有误,请确保格式正确 示例:\nshare=xxx&pwd=bbb xxx代表百度分享id bbb代表密码");
            return;
        }
        Map<String, String> stringStringMap = yunGet.get(link, pwd);
        if (stringStringMap == null) {
            sender.SENDER.sendPrivateMsg(msg.getQQCode(), "访问超时,请稍后再试");
            return;
        }
        StringBuffer sendStr = new StringBuffer();
        sendStr.append("文件名为:").append(stringStringMap.get("name")).append("\n");
        sendStr.append("大小为:").append(stringStringMap.get("size")).append("\n");
        sendStr.append("下载链接:").append(stringStringMap.get("link")).append("\n");
        sendStr.append("请使用IDM进行下载");
        sender.SENDER.sendPrivateMsg(msg, sendStr.toString());
    }


}
