package com.handcraft.timeTasks;

import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;

@CronTask("0 0 12 ? 1-12 * *")
public class RemindQiandao implements TimeJob {
    CQCodeUtil cqCodeUtil = CQCodeUtil.build();

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        CQCode cqCode_atAll = cqCodeUtil.getCQCode_AtAll();
        msgSender.SENDER.sendGroupMsg("816924822", cqCode_atAll + "各位记得签到,如果没开的话就是宋姐姐摸鱼了");
    }
}
