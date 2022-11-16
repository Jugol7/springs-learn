package com.imooc.spring.escape.cyclic_dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 单例模式下：
 * Spring通过三级缓存解决循环依赖的问题，主要借助与通过二级与三级缓存提前曝光bean
 *
 * @author zlp
 * @date 2022/11/15
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ImoocCourseService {

    /**
     * 构造函数的方式，无法解决循环依赖的问题
     * 建议使用此方式进行注入
     * 让Spring帮助我们发现循环依赖的问题
     */
//    private final QinyiJavaService javaService;
//
//    @Autowired
//    public ImoocCourseService(QinyiJavaService javaService) {
//        this.javaService = javaService;
//    }

    // Field
    @Autowired
    private QinyiJavaService javaService;

    // setter
//    @Autowired
//    public void setQinyiJavaService(QinyiJavaService javaService) {
//        this.javaService = javaService;
//    }

    public void imoocCourse() {
        javaService.qinyiJava();
    }
}
