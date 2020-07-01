package com.handcraft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * 私聊消息实体类
 *
 * @author Heilant Gong
 * {@link this#msg} 消息内容
 * {@link this#qqCode} 发送者qq号
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Accessors(chain = true)
public class PrivateTalkMsg {
    String msg;
    String qqCode;
    String sendTime;
}
