package com.handcraft.features.repeat;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Component
public class RepeatTalk {
    TreeMap<String, GroupMsg> talkSave = new TreeMap<>();
    TreeMap<String, String> stopRepeat = new TreeMap<>();

    /**
     * @param groupMsg 群聊消息
     * @return 是否与上一条消息一致
     */
    public boolean judge(GroupMsg groupMsg) {

      /*  talkSave.forEach((key, value) -> {
            System.out.println(key + "-------->" + value);
        });
    */
        if (groupMsg.getMsg().equals(stopRepeat.get(groupMsg.getGroupCode()))) {
            return false;
        }
        GroupMsg saveGroupMsg = talkSave.get(groupMsg.getGroupCode());
        if (saveGroupMsg == null) {
            talkSave.put(groupMsg.getGroupCode(), groupMsg);
            return false;
        }
        if (groupMsg.getQQCode().equals(saveGroupMsg.getQQCode())) {
            return false;
        }
        if (groupMsg.getMsg().equals(saveGroupMsg.getMsg())) {
            stopRepeat.put(groupMsg.getGroupCode(), groupMsg.getMsg());
            return true;
        } else {
            talkSave.put(groupMsg.getGroupCode(), groupMsg);
            return false;
        }

    }
}
