package com.handcraft.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.template.OnGroup;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.api.CreateApiMsg;
import com.handcraft.features.baiduyun.YunGet;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.features.qqAi.QQAiTalk;
import com.handcraft.features.repeat.RepeatTalk;
import com.handcraft.features.share.ShareFormat;
import com.handcraft.mapper.ImgInfoMapper;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.ImgDownload;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import com.simplerobot.modules.utils.KQCodeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 公用群组监听器
 * 这里是公用监听器 对所有群开放
 *
 * @author Heilant Gong
 * <p>
 * {@link OnGroup} 此类为监听所有监听群消息的方法
 *
 * <p>方法作用说明
 * {@link this#repeat(GroupMsg, MsgSender)} 复读机
 * {@link this#share(GroupMsg, MsgSender)} 解析qq的小程序/分享 还原原始链接
 * {@link this#qqAiTalk(GroupMsg, MsgSender)} 闲聊
 * {@link this#menu(GroupMsg, MsgSender)} 机器人菜单
 * {@link this#programmerCalendar(GroupMsg, MsgSender)} 主动获取老黄历
 * {@link this#emj(GroupMsg, MsgSender)} 发送一个emj
 * {@link this#sexImg(GroupMsg, MsgSender)} 获取一张涩图
 * {@link this#sweet(GroupMsg, MsgSender)} 舔/甜/毒狗模式
 * {@link this#getLink(GroupMsg, MsgSender)} 百度云解析
 */
@Component
@OnGroup
@Log4j2
public class AllGroupListener {

    CQCodeUtil cqCodeUtil = CQCodeUtil.build();
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
    @Resource
    YunGet yunGet;
    @Resource
    QQAiTalk qqAiTalk;
    @Resource
    RepeatTalk repeatTalk;

    @Filter(value = {".*今天的我"}, at = true)
    public void todayMe(GroupMsg msg, MsgSender sender) {
        String at = KQCodeUtils.INSTANCE.toCq("at", "qq=" + msg.getQQ());
        String todayMe = createApiMsg.getTodayMe(msg.getQQCode(), sender.GETTER.getGroupMemberInfo(msg.getGroup(), msg.getQQCode()).getNickName());
        sender.SENDER.sendGroupMsg(msg, at + todayMe);
    }

    @Filter
    public void repeat(GroupMsg msg, MsgSender sender) {
        String groupCode = msg.getGroupCode();
        switch (groupCode) {
            case "175183084":
            case "641057857":
            case "925295392":
                return;
        }
        boolean judge = repeatTalk.judge(msg);
        if (judge) {
            sender.SENDER.sendGroupMsg(msg, msg.getMsg());
        }
    }

    @Filter(value = {"芦苇.*"})
    public void qqAiTalk(GroupMsg msg, MsgSender sender) {
        String talk = qqAiTalk.getTalk(msg.getMsg(), msg.getQQCode());
        JSONObject jsonObject = JSON.parseObject(talk);
        String answer = jsonObject.getJSONObject("data").getString("answer");
        if (null == answer) {
            answer = "听不懂你在说什么呢";
        }
        sender.SENDER.sendGroupMsg(msg, answer);
    }


    @Filter(value = {".*CQ:app.*"})
    public void share(GroupMsg msg, MsgSender sender) {
        HashMap<String, String> format = shareFormat.format(msg.toString());
        StringBuffer sendSb = new StringBuffer();
        String local = imgDownload.biliDownload(format.get("preview"), System.getProperty("user.dir") + "\\image\\share\\", stringUtil.getUUID());
        sendSb.append(cqCodeUtil.getCQCode_Image(local) + "\n");
        sendSb.append("标题: " + format.get("desc") + "\n");
        sendSb.append("链接: " + format.get("qqdocurl"));
        sender.SENDER.sendGroupMsg(msg, sendSb.toString());
    }


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
        //接口key
        String LOLIKEY = "348731155e9d5ed04a05b7";
        StringBuffer cqCodeLocal = new StringBuffer("");
        if (msg.getGroupCode().equals("175183084") | msg.getGroupCode().equals("903811253")) {
            sender.SENDER.sendGroupMsg(msg, "别看涩图了,作业写了吗,妹子谈了嘛,没有你还在这看涩图");
            return;
        }
        String msgStr = msg.getMsg();
        ImgInfo seTu = pixivMsg.getSeTu(msgStr.substring(2, msgStr.length() - 2), 0);
        // log.warn("涩图>>>>>>>>" + seTu.toString());
        if (seTu.getId().equals("0")) {
            cqCodeLocal.append("虽然没有指定类别的涩图，但是我找到了别的好康的\n");
            seTu = pixivMsg.getSeTu("", 0);
        }
        try {
            imgDownload.download(seTu.getImageUrl(), System.getProperty("user.dir") + "\\image\\", seTu.getUuid());
            imgInfoMapper.addImg(seTu);
            cqCodeLocal.append(cqCodeUtil.getCQCode_Image(System.getProperty("user.dir") + "\\image\\" + seTu.getUuid() + seTu.getFormat()).toString() + "\n");
            cqCodeLocal.append("标题:" + seTu.getTitle() + "\n");
            cqCodeLocal.append("P站ID:" + seTu.getId());
        } catch (Exception e) {
            sender.SENDER.sendGroupMsg(msg, "哎鸭,涩图不见了呢");
            e.printStackTrace();
        } finally {
            // log.warn("cqCode>>>>>>>>>" + cqCodeLocal.toString());
            sender.SENDER.sendGroupMsg(msg, cqCodeLocal.toString());
        }
    }


    @Filter(value = {".我"})
    public void sweet(GroupMsg msg, MsgSender sender) {
        String at = KQCodeUtils.INSTANCE.toCq("at", "qq=" + msg.getQQ());
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
        sender.SENDER.sendGroupMsg(msg, at + " " + sendMsg);
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

    //百度云解析
    @Filter(value = {"share=.*"})
    public void getLink(GroupMsg msg, MsgSender sender) {
        String msgStr = msg.getMsg();
        String link = null;
        String pwd = null;
        try {
            link = msgStr.substring(msgStr.indexOf("share=") + 6, msgStr.indexOf("pwd") - 1);
            pwd = msgStr.substring(msgStr.indexOf("pwd") + 4);
        } catch (Exception e) {
            sender.SENDER.sendGroupMsg(msg, "输入有误,请确保格式正确 示例:\nshare=xxx&pwd=bbb xxx代表百度分享id bbb代表密码");
            return;
        }
        System.err.println(link);
        System.err.println(pwd);
        Map<String, String> stringStringMap = yunGet.get(link, pwd);
        if (stringStringMap == null) {
            sender.SENDER.sendGroupMsg(msg, "访问超时,请先检查链接是否失效,若链接正常 请稍后再试");
            return;
        }
        StringBuffer sendStr = new StringBuffer();
        sendStr.append("文件名为:").append(stringStringMap.get("name")).append("\n");
        sendStr.append("大小为:").append(stringStringMap.get("size")).append("\n");
        sendStr.append("下载链接:").append(stringStringMap.get("link")).append("\n");
        sendStr.append("请使用IDM进行下载");
        sender.SENDER.sendGroupMsg(msg, sendStr.toString());
    }

}