package com.handcraft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 课程信息
 *
 * @author Heilant Gong
 * <p>
 * {@link this#uuid} 确保唯一的标识
 * {@link this#name} 课程名
 * {@link this#week} 上课星期
 * {@link this#startTime} 开始时间
 * {@link this#endTime} 结束时间
 * {@link this#teacher} 授课老师
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ClassInfo {
    String uuid;
    String name;
    int week;
    String startTime;
    String endTime;
    String teacher;
}
