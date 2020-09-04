create database slimebot default character set utf8 collate utf8_general_ci;
use slimebot;
/*pixiv 图片信息表*/
create table imginfo
(
    `uuid`     varchar(40) primary key,
    `id`       varchar(8) COMMENT 'id',
    `title`    varchar(100) COMMENT '标题',
    `imageUrl` text COMMENT '图片链接',
    `tags`     text COMMENT 'tag',
    `date`       DATE COMMENT '图片日期',
    `format` varchar(10) COMMENT '图片格式'
);
insert into imginfo (uuid, id, title, imageUrl, tags)
values ('1', '1', '1', '1', '1');

select *
from imginfo;
/*上课通知*/
create table classInfo
(
    `uuid`      varchar(40) primary key,
    `name`      varchar(20) COMMENT '名称',
    `week`      int COMMENT '星期',
    `startTime` TIME COMMENT '开始时间',
    `endTime`   TIME COMMENT '结束时间',
    `teacher`   varchar(10) COMMENT '授课老师'
);
alter table imginfo convert to character set utf8mb4 collate utf8mb4_bin;

/*全体成员通知*/
create table msgTime(
    `uuid` varchar(40) primary key ,
    `qqCode` varchar (20) COMMENT '通知群号',
    `sendTime` datetime COMMENT '通知时间',
    `msg` text COMMENT '通知内容',
    `at` boolean COMMENT '是否At'
);
insert into msgTime (uuid, qqCode, sendTime, msg)
values ('addasda','123456',now(),'123');

/*私聊消息存储表*/
create table privateMsgRecord(
    `msg` text COMMENT '消息内容',
    `qqCode` varchar(20) COMMENT '发送者',
    `sendTime` datetime COMMENT '接收时间'
);
insert into  privateMsgRecord(msg,qqCode,sendTime)
values ('测试','123456',now());

/*群聊息存储表*/
create table groupMsgRecord(
    `msg` text COMMENT '消息内容',
    `qqCode` varchar(20) COMMENT '发送者',
    `groupCode` varchar(20) COMMENT '群号',
    `sendTime` datetime COMMENT '接收时间'
);
insert into  groupMsgRecord(msg,qqCode,groupCode,sendTime)
values ('测试','123456','654321',now());