package com.handcraft.timeTasks;

import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.simplerobot.modules.delay.DelaySender;

/**
 * @author HeilantG
 * 提醒上课小助手
 */
@CronTask("0 30 14 ? 1-12 2,4,6 *")
public class Remindclass implements TimeJob {
    CQCodeUtil cqCodeUtil = CQCodeUtil.build();

    @Override
    public void execute(MsgSender sender, CQCodeUtil cqCodeUtil) {
        CQCode cqCode_atAll = cqCodeUtil.getCQCode_AtAll();
        sender.SENDER.sendGroupMsg("816924822", cqCode_atAll.toString() +
                "还有三十分钟就要上课啦,各组准备开始查人");
        DelaySender delaySender = new DelaySender(sender.SENDER, 900);
        delaySender.sendGroupMsg("816924822", cqCode_atAll.toString() +
                "还有十五分钟就要上课啦 赶紧去腾讯会议报道, 赶紧去腾讯会议报道,苏哥说他抖音想涨粉了");
    }
}
