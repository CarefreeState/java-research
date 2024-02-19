package com.macaku.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-10-26
 * Time: 9:15
 */
@Configuration
public class AppConfig {

    public static String ROOT;

    //被映射的目录和映射成的一级路由
    public static String MAP_ROOT;

    @Value("${res.path}")
    public void setROOT(String root) {
        ROOT = root;
    }

    @Value("${res.map}")
    public void setMAP_ROOT(String mapRoot) {
        MAP_ROOT = mapRoot;
    }

}
