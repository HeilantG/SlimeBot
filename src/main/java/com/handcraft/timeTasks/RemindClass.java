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
 * 提醒上课小助手
 *
 * @author HeilantG
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
    // 班级群号
    private static String CLASS_QQ_CODE = "903811253";
    /**
     * 自定义送信器
     */
    @Resource
    private BotManager botManager;

    /**
     * 每日十二点提醒签到
     */
    //@Scheduled(cron = "0 0 12 * * ?")
    public void execute() {
        CQCode cqCode_atAll = cqCodeUtil.getCQCode_AtAll();
        CQCode cqCode_image = cqCodeUtil.getCQCode_Image(System.getProperty("user.dir") + "\\image\\TimeTasks\\RemindClassQianDao.png");
        BotSender sender = botManager.defaultBot().getSender();
        sender.SENDER.sendGroupMsg(CLASS_QQ_CODE, cqCode_image.toString() + cqCode_atAll.toString());
    }


    /**
     * 每日早报
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void dayHello() {
        BotSender sender = botManager.defaultBot().getSender();
        String dayMsg = msgCreate.getDayMsg();
        sender.SENDER.sendGroupMsg("361081715", dayMsg);
        //班级群
        sender.SENDER.sendGroupMsg(CLASS_QQ_CODE, dayMsg);
    }

    /**
     * 每过30分钟检查上课
     */
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
                    CQCode cqCode_image = cqCodeUtil.getCQCode_Image(System.getProperty("user.dir") + "\\image\\TimeTasks\\RemindClass.png");
                    sender.SENDER.sendGroupMsg(CLASS_QQ_CODE, cqCodeUtil.getCQCode_AtAll().toString() + cqCode_image);
                    sender.SENDER.sendGroupMsg(CLASS_QQ_CODE, "这节课是" + classInfo.getTeacher() + "老师的" + classInfo.getName());
                    DelaySender delaySender = new DelaySender(sender.SENDER, 900);
                    delaySender.sendGroupMsg(CLASS_QQ_CODE, "还有" + (minute - 15) + "就要上课了,没到实训楼的同学抓紧了");
                }
            }
        }
    }


    /**
     * 每过一分钟检测数据库中是否有提醒消息
     *
     * @throws ParseException simpleFormat格式化
     */
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
                    sender.SENDER.sendGroupMsg(CLASS_QQ_CODE, cqCodeUtil.getCQCode_AtAll() + msgTime.getMsg());
                } else {
                    sender.SENDER.sendGroupMsg(CLASS_QQ_CODE, msgTime.getMsg());
                }
            }
        }
    }
}
