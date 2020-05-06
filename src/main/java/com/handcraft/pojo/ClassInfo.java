package com.handcraft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.sql.Time;

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
