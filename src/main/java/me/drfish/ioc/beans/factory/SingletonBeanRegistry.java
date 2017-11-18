package me.drfish.ioc.beans.factory;

/**
 * @author drfish
 * @date 18/11/2017
 **/
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    int getSingletonCount();
}
