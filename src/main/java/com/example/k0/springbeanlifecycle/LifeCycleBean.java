package com.example.k0.springbeanlifecycle;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Data
@Slf4j
public class LifeCycleBean extends TotalAware {
    private String userName;

    public LifeCycleBean() {
        log.info("lifeCycleBean is created.");
    }

    @Override
    public void setBeanName(String s) {
        log.info("BeanNameAware接口的setBeanName方法执行了{}", s);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.info("BeanClassLoaderAware接口的setBeanClassLoader方法执行了 {}", classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("BeanFactoryAware接口的setBeanFactory方法执行了 factory {}, factory.class: {}", beanFactory, beanFactory.getClass());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("ApplicationContextAware接口的setApplicationContext方法执行了 applicationContext:{}, applicationContext.class: {}", applicationContext, applicationContext.getClass());
    }

    @PostConstruct
    public void init() {
        log.info("@PostConstruct注解的init方法执行了");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("InitializingBean接口的afterPropertiesSet方法执行了");
    }

    public void initMethod() {
        log.info("init-method方法执行了");
    }

    @PreDestroy
    public void destroy() {
        log.info("@PreDestroy注解的destroy方法执行了");
    }

    public void destroyMethod() {
        log.info("destroy-method方法执行了");
    }
}
