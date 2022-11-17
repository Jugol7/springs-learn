package com.imooc.spring.escape.date_se_de;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

/**
 * 全局日期转换配置
 *
 * @author zlp
 * @date 2022/11/17
 */
@Configuration
@Slf4j
public class DateConverterConfig {

    @Bean
    public DateJacksonConverter dateJacksonConverter() {
        return new DateJacksonConverter();
    }

    @Bean
    public Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean(
            @Autowired DateJacksonConverter dateJacksonConverter
    ) {
        Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean =
                new Jackson2ObjectMapperFactoryBean();
        jackson2ObjectMapperFactoryBean.setDeserializers(dateJacksonConverter);

        return jackson2ObjectMapperFactoryBean;
    }
}
