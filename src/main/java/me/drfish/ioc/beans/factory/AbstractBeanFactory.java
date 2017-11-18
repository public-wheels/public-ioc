package me.drfish.ioc.beans.factory;

import me.drfish.ioc.beans.BeanDefinition;
import me.drfish.ioc.exception.CircularDependencyException;
import me.drfish.ioc.exception.NoSuchBeanDefinitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author drfish
 * @date 18/11/2017
 **/
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private Logger logger = LoggerFactory.getLogger(AbstractBeanFactory.class);

    protected Map<String, Object> completedBeanPool = new ConcurrentHashMap<>();
    protected Map<String, Object> babyBeanPool = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) throws CircularDependencyException {
        return doGetBean(name, Object.class);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws CircularDependencyException {
        return doGetBean(name, requiredType);
    }

    protected <T> T doGetBean(String name, Class<T> requiredType) throws CircularDependencyException {
        babyBeanPool.put(name, requiredType);
        Object bean;
        if ((bean = getSingleton(name)) != null) {
            if (requiredType == null || !requiredType.isInstance(bean)) {
                logger.error("Require type %s not match bean %s", requiredType, bean);
                throw new ClassCastException();
            }
        } else {
            BeanDefinition beanDefinition = getBeanDefinition(name);
            if (beanDefinition == null) {
                try {
                    throw new NoSuchBeanDefinitionException();
                } catch (NoSuchBeanDefinitionException e1) {
                    logger.error("cannot find bean definition for %s", name);
                    return null;
                }
            }
            List<String> depends = beanDefinition.getDependsOn();
            if (depends != null && depends.size() >= 1) {
                for (String depend : depends) {
                    if (depend.indexOf('.') == 0) {
                        continue;
                    }
                    if (!containsBeanDefintion(depend)) {

                    } else {
                        if (babyBeanPool.get(depend) != null) {
                            logger.error("there is circular dependencies in your beans");
                            throw new CircularDependencyException();
                        }
                        getBean(depend);
                    }
                }
            }
            bean = createBean(name, beanDefinition);
            addToCompletedBeanPoolAndRemoveFromBabyBeanPool(name, bean);
        }
        return (T) bean;
    }

    private synchronized void addToCompletedBeanPoolAndRemoveFromBabyBeanPool(String name, Object bean) {
        completedBeanPool.putIfAbsent(name, bean);
        babyBeanPool.remove(name);
    }

    @Override
    public boolean isSingleton(String beanDefinitionName) throws NoSuchBeanDefinitionException {
        if (!containsBeanDefintion(beanDefinitionName)) {
            throw new NoSuchBeanDefinitionException();
        } else {
            return getBeanDefinition(beanDefinitionName).isSingleton();
        }
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);
}
