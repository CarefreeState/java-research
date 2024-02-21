package com.itheima.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.itheima.mp.mapper")
@SpringBootApplication
public class MpDemoApplication {

    //学习文档：https://b11et3un53m.feishu.cn/wiki/PsyawI04ei2FQykqfcPcmd7Dnsc
    //插件如何生成代码：https://zhuanlan.zhihu.com/p/589075572
    public static void main(String[] args) {
        SpringApplication.run(MpDemoApplication.class, args);
    }

}

