package com.example.usercenterbackend.service.impl;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

public class MyTestListener implements SpringApplicationRunListener, Ordered {

    private SpringApplication application;
    private String[] args;


    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        SpringApplicationRunListener.super.starting(bootstrapContext);
        System.out.println("表示准备开始使用监听器");

    }


    public MyTestListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        SpringApplicationRunListener.super.environmentPrepared(bootstrapContext, environment);
        System.out.println("表示已经开始读取配置文件");
        //配置文件到程序，再然后放入springboot容器
        Properties properties=new Properties();
        try {
            //读取properties容器
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application.yml"));
            //读取名字为my
            PropertySource propertySource=new PropertiesPropertySource("spring.application.name",properties) ;
            //加载资源到springboot容器
            MutablePropertySources propertySources=environment.getPropertySources();
            propertySources.addLast(propertySource);
            //换种思路，如果你配置文件是放在网络上，可以直接读取放入我们的项目中

        } catch (IOException e) {
            System.out.println("出错");
        }
    }


    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("表示初始化容器已经结束");

    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("表示可以使用springboot了");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        // TODO Auto-generated method stub

    }

    //读取优先级
    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return 1;
    }
}
