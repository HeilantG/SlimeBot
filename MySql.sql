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
    `date`       DATE COMMENT '图片日期'
);
insert into imginfo (uuid, id, title, imageUrl, tags)
values ('1', '1', '1', '1', '1');

select *
from imginfo;
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
