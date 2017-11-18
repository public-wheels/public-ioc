package me.drfish.ioc.beans.factory;

import me.drfish.ioc.beans.BeanDefinition;
import me.drfish.ioc.exception.CircularDependencyException;
import me.drfish.ioc.exception.NoSuchBeanDefinitionException;

/**
 * @author drfish
 * @date 18/11/2017
 **/
public interface BeanFactory {
    Object getBean(String name) throws CircularDependencyException;

    <T> T getBean(String name, Class<T> requiredType) throws CircularDependencyException;

    boolean containsBeanDefintion(String beanDefinitionName);

    boolean isSingleton(String beanDefinitionName) throws NoSuchBeanDefinitionException;

    boolean isProtoType(String beanDefinitionName);

    BeanDefinition getBeanDefinition(String beanDefinitionName);
}
