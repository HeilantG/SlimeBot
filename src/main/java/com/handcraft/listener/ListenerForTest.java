package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.mapper.ImgInfoMapper;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author HeilantG
 * 这是测试用类，
 */
@Component
public class ListenerForTest {
    public CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    @Resource
    MsgCreate msgCreate;
    @Resource
    ImgInfoMapper imgInfoMapper;
    @Resource
    StringUtil stringUtil;

    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"#提醒签到"})
    public void remindQianDao(GroupMsg groupMsg, MsgSender sender) {
        System.out.println("提醒签到");
        CQCode cqCode_image = cqCodeUtil.getCQCode_Image(System.getProperty("user.dir") + "\\image\\TimeTasks\\RemindQianDao.png");
        sender.SENDER.sendGroupMsg(groupMsg, cqCode_image.toString());
    }

    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"#提醒上课"})
    public void remindClass(GroupMsg groupMsg, MsgSender sender) {
        System.out.println("提醒上课");
        CQCode cqCode_image = cqCodeUtil.getCQCode_Image(System.getProperty("user.dir") + "\\image\\TimeTasks\\RemindClass.jpg");
        sender.SENDER.sendGroupMsg(groupMsg, cqCode_image.toString());
    }

}
