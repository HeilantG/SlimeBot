package com.handcraft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * 群聊信息实体类
 *
 * @author Heilant Gong
 * <p>
 * {@link this#msg} 消息内容
 * {@link this#qqCode} 发送者qq号
 * {@link this#groupCode} 消息来源qq群
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Component
public class GroupTalkMsg {
    String msg;
    String qqCode;
    String groupCode;
    String sendTime;
}
