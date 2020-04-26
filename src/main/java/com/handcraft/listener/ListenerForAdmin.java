package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.util.MsgCreate;
import com.simplerobot.modules.delay.DelaySender;

import java.util.concurrent.TimeUnit;

/**
 * @author HeilantG
 * 自用私聊测试
 */
@Beans
public class ListenerForAdmin {
    public CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    //本地图片测试

    @Listen(MsgGetTypes.privateMsg)
    @Filter(code = {"1310147115"}, value = {"img.*"})
    public void imgTest(PrivateMsg msg, MsgSender sender) {
        System.out.println(msg);
        CQCode cqCode_image = cqCodeUtil.getCQCode_Image("1.jpg");
        sender.SENDER.sendPrivateMsg("1310147115", cqCode_image.toString());
    }


    @Listen(MsgGetTypes.privateMsg)
    @Filter(code = {"1310147115"}, value = {"a.*"})
    public void img(PrivateMsg msg, MsgSender sender) {
        StringBuffer str = new StringBuffer();
        CQCode cqCode_image = cqCodeUtil.getCQCode_Image("http://image.imufu.cn/forum/201204/28/195258jmwwpvw48zcjmdj8.jpg");
        sender.SENDER.sendPrivateMsg("1310147115", cqCode_image.toString());
    }


    //今日新闻

    @Listen(MsgGetTypes.privateMsg)
    @Filter(code = {"1310147115"}, value = {"hello.*"})
    public void today(PrivateMsg privateMsg, MsgSender sender) {
        sender.SENDER.sendPrivateMsg("1310147115", MsgCreate.getDayMsg());
        sender.SENDER.sendPrivateMsg("1310147115", " cqCode_image.toString()");
    }

    //延时消息

    @Listen(MsgGetTypes.privateMsg)
    @Filter(code = {"1310147115"}, value = {"延时"})
    public void testYs(PrivateMsg msg, MsgSender sender) {
        DelaySender delaySender = new DelaySender(sender.SENDER, 2);
//        DelaySender delaySender = new DelaySender(sender.SENDER, 2000, TimeUnit.MILLISECONDS); // 使用毫秒
        // 2秒后复读一次
        delaySender.sendPrivateMsg(msg.getQQCode(), "2秒后： " + msg.getMsg());
        // 10秒后再复读一次
        // 重新设置时间, 注意！此处设置的时间格式默认为毫秒，所以需要添加时间格式，或者使用10*1000
        delaySender.setDelayTime(10, TimeUnit.SECONDS);
        // 延时发送
        delaySender.sendPrivateMsg(msg.getQQCode(), "10秒后： " + msg.getMsg());
        // 用原本的sender先直接发送一个消息
        sender.SENDER.sendPrivateMsg(msg.getQQCode(), "直接发送： " + msg.getMsg());
    }

    @Listen(MsgGetTypes.privateMsg)
    @Filter(code = {"1310147115"}, value = {"上课"})
    public void testSk(PrivateMsg msg, MsgSender sender) {
        CQCode cqCode_atAll = cqCodeUtil.getCQCode_AtAll();
        sender.SENDER.sendPrivateMsg("1310147115", cqCode_atAll.toString() +
                "还有三十分钟就要上课啦,各组准备开始查人");
        DelaySender delaySender = new DelaySender(sender.SENDER, 900);
        delaySender.sendPrivateMsg("1310147115", cqCode_atAll.toString() +
                "还有十五分钟就要上课啦 赶紧去腾讯会议报道, 赶紧去腾讯会议报道,苏哥说他抖音想涨粉了");

    }



}
