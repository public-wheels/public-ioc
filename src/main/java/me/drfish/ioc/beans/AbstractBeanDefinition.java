package me.drfish.ioc.beans;

import java.util.List;

/**
 * @author drfish
 * @date 18/11/2017
 **/
public abstract class AbstractBeanDefinition implements BeanDefinition{
    private static final String DEFAULT_SCOPE = BeanScope.SCOPE_SINGLETON.getBeanScope();
    private String scope = DEFAULT_SCOPE;
    private Object beanClass;
    private List<String> dependsOn;

    @Override
    public List<String> getDependsOn() {
        return dependsOn;
    }

    @Override
    public void addDependOn(String dependOn) {
        dependsOn.add(dependOn);
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean isSingleton() {
        return BeanScope.SCOPE_SINGLETON.getBeanScope().equals(scope);
    }

    @Override
    public boolean isPrototype() {
        return BeanScope.SCOPE_PROTOTYPE.getBeanScope().equals(scope);
    }

    @Override
    public Class<?> getBeanClass() {
        Object beanClassObject = this.beanClass;
        if(beanClassObject==null){
            throw new IllegalStateException("No bean class specified for bean definition");
        }
        if (!(beanClassObject instanceof Class)) {
            throw new IllegalStateException(
                    "Bean class name [" + beanClassObject + "] has not been resolved into an actual Class");
        }
        return (Class<?>) beanClassObject;
    }

    @Override
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
}
