package com.example.k0.springbeanlifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AllLifeCycleBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(LifeCycleBean.class)) {
            log.info("postProcessAfterInstantiation执行了");
        }
        return SmartInstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanClass.equals(LifeCycleBean.class)) {
            log.info("postProcessBeforeInstantiation执行了");
        }
        return SmartInstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(LifeCycleBean.class)) {
            log.info("postProcessBeforeInitialization执行了");
        }
        return SmartInstantiationAwareBeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(LifeCycleBean.class)) {
            log.info("postProcessAfterInitialization执行了");
        }
        return SmartInstantiationAwareBeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(LifeCycleBean.class)) {
            MutablePropertyValues propertyValues = new MutablePropertyValues();
            propertyValues.add("userName", "zhangWei");
            log.info("postProcessProperties执行了");
            return propertyValues;
        }
        return SmartInstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
    }
}
