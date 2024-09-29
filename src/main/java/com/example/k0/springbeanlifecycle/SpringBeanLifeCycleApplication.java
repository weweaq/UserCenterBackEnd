package com.example.k0.springbeanlifecycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBeanLifeCycleApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringBeanLifeCycleApplication.class, args);
        LifeCycleBean lifeCycleBean = run.getBean(LifeCycleBean.class);
        System.out.println(lifeCycleBean.getUserName());
        run.close();
    }
}
