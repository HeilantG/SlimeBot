package com.handcraft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

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
