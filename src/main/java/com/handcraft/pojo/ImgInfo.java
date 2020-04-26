package com.handcraft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImgInfo {
    String uuid;
    String id;
    String title;
    String imageUrl;
    String tags;
    Date date;
}
