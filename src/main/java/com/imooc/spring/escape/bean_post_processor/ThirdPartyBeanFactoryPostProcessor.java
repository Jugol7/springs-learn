package com.imooc.spring.escape.bean_post_processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 第三方bean工厂后置处理程序
 * BeanFactoryPostProcessor：在bean初始化之前执行
 *
 * @author zlp
 * @date 2022/11/16
 */
@Component
public class ThirdPartyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        // 默认bean是单例的，通过BeanFactoryPostProcessor处理，在初始化之前对bean进行处理，修改成原型模式
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(
                "thirdPartyClass");
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
    }
}
