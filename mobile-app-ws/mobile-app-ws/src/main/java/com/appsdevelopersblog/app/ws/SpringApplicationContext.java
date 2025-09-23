package com.appsdevelopersblog.app.ws;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//We are going to access application context to find and use bean objects.
//This class must be annotated with @Component annotation if it has one or zero constructor. But this limits the control of bean creation.
//So we are going to create a bean of this class inside MobileAppWsApplication class.
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    //In this method, ApplicationContext is a method argument and we can use it access beans that are inside of application context.
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        CONTEXT = applicationContext;//We initialized our CONTEXT variable for accessing application context.

    }

    //We will return particular bean object with particular name. With this, we're able to access beans without injecting objects.
    public static Object getBean(String beanName){

        return CONTEXT.getBean(beanName);
    }
}
