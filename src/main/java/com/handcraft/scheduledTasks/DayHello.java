package com.handcraft.scheduledTasks;

import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.util.MsgCreate;

/**
 * @author HeilantG
 * 定时消息
 */
@CronTask("0 0 7 1/1 1/1 ?")
public class DayHello implements TimeJob {
    //每日早报

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        msgSender.SENDER.sendGroupMsg("361081715", MsgCreate.getDayMsg());
        msgSender.SENDER.sendGroupMsg("816924822", MsgCreate.getDayMsg());
    }

}
