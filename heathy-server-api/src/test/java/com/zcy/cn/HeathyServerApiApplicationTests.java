package com.zcy.cn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HeathyServerApiApplicationTests {

    @Value("${ss:sss}")
    private String ss;

    @Test
    public void contextLoads() {
        System.out.println(ss);
    }

}
