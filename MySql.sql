create database slimebot;
use slimebot;
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