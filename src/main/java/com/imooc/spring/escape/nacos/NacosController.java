package com.imooc.spring.escape.nacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zlp
 * @date ：2022/11/17 23:57
 * @version: 1.0
 */
@RestController
@RequestMapping("/nacos")
@Slf4j
@RefreshScope
public class NacosController {

    @Value("${diyData}")
    private String diyData;

    @GetMapping("/getNacosConfig")
    public void getNacosConfig(){
        log.info("Nacos config {}", diyData);
    }

}
