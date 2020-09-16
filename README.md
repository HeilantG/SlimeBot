# SlimeBot

这是基于Mirai与Simple-robot-core的一个个人用整合，暂时不保证复用性。

### ***项目近期重构中***

# 什么是Mirai

详情请见[Mirai](https://github.com/mamoe/mirai)

我处在一边学习[ForteScarlet](https://github.com/ForteScarlet) 的
[simple-robot-core](https://github.com/ForteScarlet/simple-robot-core) 一边写代码的状态

后台使用的是[光年后台管理模板](https://gitee.com/yinqi/Light-Year-Admin-Using-Iframe)

# 目前功能

对号代表功能正常，错号代码功能不正常，基本都是因为更换核心导致的，正在逐渐修复

- ff狩猎群信息转发 √ #暂时关闭
- 程序员的老黄历 √
- 提醒上课/签到小助手 √
- 网课查询答案 √
- 构思与数据库交互的功能
    + p站日图 √
    + 涩图BOT √
- 闲聊 √
- 甜蜜话/毒鸡汤 √
- 来张涩图机器人 √
- 解析分享链接 √
- 控制后端 √
- 解析百度云直链

# 计划任务

按照顺序视为优先级高低
- 修复解析分享 √
    + 增加全类型解析
- 修复日图 √
    + 优化图片下载
- 重构工具类
- Qr码生成
- 更换闲聊Ai √
- 缝合更多功能
- 沙雕发言

# 注意

- 这是一个自用的机器人,理论来说我不会接受任何人提交的任何代码，当然你可以下载源码然后修改或者学习
- 打包的时候记得把jar一起打进去
- 可能会停止更新一段时间(其实以我的更新速度,也不会差觉到停止更新),然后项目会进行整体重构

# 问题

- ~~有很奇怪的枚举乱码问题~~ 在Maven中统一了编码格式 完成
- 修改老黄历的数据库
- p站日图图片发送问题
    + ~~需要自己写一个图片下载进行缓存~~ 完成
    + 进一步优化图片下载路径
- ~~闲聊词库中有奇怪的词条~~ ~~暂时关闭 寻找更好的Ai~~ 更换了腾讯Ai

# 鸣谢

> [IntelliJ IDEA](https://zh.wikipedia.org/zh-hans/IntelliJ_IDEA) 是一个在各个方面都最大程度地提高开发人员的生产力的 IDE，适用于 JVM 平台语言。

特别感谢 [JetBrains](https://www.jetbrains.com/?from=SlimeBot) 为开源项目提供免费的 [IntelliJ IDEA](https://www.jetbrains.com/idea/?from=SlimeBot) 等 IDE 的授权  
[<img src=".github/jetbrains-variant-3.png" width="200"/>](https://www.jetbrains.com/?from=SlimeBot)