package me.drfish.ioc.beans;

import java.util.List;

/**
 * @author drfish
 * @date 18/11/2017
 **/
public interface BeanDefinition {
    String SCOPE_SINGLETON = BeanScope.SCOPE_SINGLETON.getBeanScope();
    String SCOPE_PROTOTYPE = BeanScope.SCOPE_PROTOTYPE.getBeanScope();

    List<String> getDependsOn();

    void addDependOn(String dependOn);

    String getScope();

    void setScope(String scope);

    boolean isSingleton();

    boolean isPrototype();

    Class<?> getBeanClass();

    void setBeanClass(Class<?> beanClass);
}
