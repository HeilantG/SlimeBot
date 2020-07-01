package com.handcraft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 提醒消息
 *
 * @author Heilant Gong
 * <p>
 * {@link this#uuid} 唯一标识
 * {@link this#qqCode} 发送群号
 * {@link this#sendTime} 发送时间
 * {@link this#msg} 提示消息
 * {@link this#at} 是否At全体成员
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class MsgTime {
    String uuid;
    String qqCode;
    String sendTime;
    String msg;
    Boolean at;
}
