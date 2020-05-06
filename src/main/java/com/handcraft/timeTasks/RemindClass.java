package com.handcraft.timeTasks;


import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.bot.BotSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.pojo.ClassInfo;
import com.handcraft.service.ClassInfoService;
import com.handcraft.util.MsgCreate;
import com.simplerobot.modules.delay.DelaySender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author HeilantG
 * 提醒上课小助手
 */
@Component
@EnableScheduling
public class RemindClass {
    CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    @Resource
    ClassInfoService classInfoService;
    @Resource
    MsgCreate msgCreate;
    /*自定义送信器*/
    @Resource
    private BotManager botManager;

    //签到

    @Scheduled(cron = "0 0 12 * * ? ")
    public void execute() {
        CQCode cqCode_atAll = cqCodeUtil.getCQCode_AtAll();
        BotSender sender = botManager.defaultBot().getSender();
        sender.SENDER.sendGroupMsg("816924822", cqCode_atAll + "各位记得签到,如果没开的话就是宋姐姐摸鱼了");
    }

    //每日早报

    @Scheduled(cron = "0 0 7 * * ?")
    public void dayHello() {
        BotSender sender = botManager.defaultBot().getSender();
        String dayMsg = msgCreate.getDayMsg();
        sender.SENDER.sendGroupMsg("361081715", dayMsg);
        sender.SENDER.sendGroupMsg("816924822", dayMsg);
        sender.SENDER.sendGroupMsg("190375193", dayMsg);
    }

    // 每过30分钟检查上课

    @Scheduled(cron = "0 0/30 * * * ?")
    public void remind() {
        BotSender sender = botManager.defaultBot().getSender();
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
        Date now = null;
        Date begin = null;
        List<ClassInfo> classInfoList = classInfoService.queryAllClass();
        for (ClassInfo classInfo : classInfoList) {
            // System.out.println(classInfo.toString());
            if (classInfo.getWeek() == week) {
                try {
                    now = dfs.parse(dfs.format(new Date()));
                    begin = dfs.parse(classInfo.getStartTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long between = (begin.getTime() - now.getTime()) / 1000;
                long hour = between % (24 * 3600) / 3600;
                long minute = between % 3600 / 60;
                //long second = between % 60 / 60;
                // System.out.println("hour=>"+hour+"minute=>"+minute);
                // 验证一小时内上课z`
                if (hour == 0 && minute > 0 && minute < 40) {
                    sender.SENDER.sendGroupMsg("816924822", cqCodeUtil.getCQCode_AtAll().toString() + "还有" + minute + "分钟就要上课了,组长开始查人了嘛?");
                    sender.SENDER.sendGroupMsg("816924822", "这节课是" + classInfo.getTeacher() + "老师的" + classInfo.getName());
                    DelaySender delaySender = new DelaySender(sender.SENDER, 900);
                    delaySender.sendGroupMsg("816924822", "还有" + (minute - 15) + "就要上课了,快去腾讯会议报道");
                }
            }
        }
    }

}
