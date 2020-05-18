# SlimeBot
这是酷Q的一个个人用插件
我处在一边学习[ForteScarlet](https://github.com/ForteScarlet)的[simple-robot-core](https://github.com/ForteScarlet/simple-robot-core)一边写代码的状态
后台使用的是[光年后台管理模板](https://gitee.com/yinqi/Light-Year-Admin-Using-Iframe)
# 目前功能
- ff狩猎群信息转发 √
- 程序员的老黄历 √
- 提醒上课/签到小助手 √
- 网课查询答案 √
- 构思与数据库交互的功能
    + p站日图 √
    + 涩图BOT √
# 预期功能
- 沙雕发言
# 注意
- 打包的时候记得把jar一起打进去
- ```mvn assembly:assembly```
# 问题
- ~~有很奇怪的枚举乱码问题~~ 在Maven中统一了编码格式
- 修改老黄历的数据库
- p站日图图片发送问题
    + ~~需要自己写一个图片下载进行缓存~~ 完成
    + 进一步优化图片下载路径