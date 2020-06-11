package com.handcraft.timeTasks;


import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.bot.BotSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.pojo.ClassInfo;
import com.handcraft.pojo.MsgTime;
import com.handcraft.service.ClassInfoService;
import com.handcraft.service.MsgTimeService;
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
    @Resource
    MsgTimeService msgTimeService;
    String classQQCode = "816924822";
    /**
     * 自定义送信器
     */
    @Resource
    private BotManager botManager;

    //签到

    @Scheduled(cron = "0 0 12 * * ?")
    public void execute() {
        CQCode cqCode_atAll = cqCodeUtil.getCQCode_AtAll();
        BotSender sender = botManager.defaultBot().getSender();
        sender.SENDER.sendGroupMsg(classQQCode, cqCode_atAll + "各位记得签到,如果没开的话就是宋姐姐摸鱼了");
    }

    //每日早报

    @Scheduled(cron = "0 0 7 * * ?")
    public void dayHello() {
        BotSender sender = botManager.defaultBot().getSender();
        String dayMsg = msgCreate.getDayMsg();
        sender.SENDER.sendGroupMsg(classQQCode, dayMsg);
        sender.SENDER.sendGroupMsg("361081715", dayMsg);
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
                if (hour == 0 && minute > 0 && minute < 40) {
                    sender.SENDER.sendGroupMsg(classQQCode, cqCodeUtil.getCQCode_AtAll().toString() + "还有" + minute + "分钟就要上课了,组长开始查人了嘛?");
                    sender.SENDER.sendGroupMsg(classQQCode, "这节课是" + classInfo.getTeacher() + "老师的" + classInfo.getName());
                    DelaySender delaySender = new DelaySender(sender.SENDER, 900);
                    delaySender.sendGroupMsg(classQQCode, "还有" + (minute - 15) + "就要上课了,快去腾讯会议报道");
                }
            }
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void atAllMsg() throws ParseException {
        BotSender sender = botManager.defaultBot().getSender();
        List<MsgTime> msgTimes = msgTimeService.queryAll();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowTime = new Date();
        for (MsgTime msgTime : msgTimes) {
            //System.out.println("msgTime==>" + msgTime.toString());
            Date fromDate1 = simpleFormat.parse(msgTime.getSendTime());
            long sendTime = fromDate1.getTime();
            long nowTimeLong = nowTime.getTime();
            /*天数差*/
            int days = (int) ((sendTime - nowTimeLong) / (1000 * 60 * 60 * 24));
            //System.out.println("天数差=>" + days);
            /*小时差*/
            int hours = (int) ((sendTime - nowTimeLong) / (1000 * 60 * 60));
            //System.out.println("小时差=>" + hours);
            /*分钟差*/
            int minutes = (int) ((sendTime - nowTimeLong) / (1000 * 60));
            //System.out.println("分钟差=>" + minutes);
            if (days == 0 && hours == 0 && minutes + 1 == 1) {
                /*判断是否At*/
                if (msgTime.getAt()) {
                    sender.SENDER.sendGroupMsg(classQQCode, cqCodeUtil.getCQCode_AtAll() + msgTime.getMsg());
                } else {
                    sender.SENDER.sendGroupMsg(classQQCode, msgTime.getMsg());
                }
            }
        }
    }
}
