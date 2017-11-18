package me.drfish.ioc.beans.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author drfish
 * @date 18/11/2017
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletonObjects.containsKey(beanName);
    }

    @Override
    public int getSingletonCount() {
        return singletonObjects.size();
    }
}
