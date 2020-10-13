package com.handcraft;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testApp {

    @Test
    public void all() {
        System.out.println(System.getProperty("user.dir"));
    }


}
