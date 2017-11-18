package me.drfish.ioc.beans;

/**
 * @author drfish
 * @date 18/11/2017
 **/
public enum BeanScope {
    SCOPE_SINGLETON("singleton"),
    SCOPE_PROTOTYPE("prototype");

    private String beanScope;

    BeanScope(String beanScope){
        this.beanScope = beanScope;
    }

    public String getBeanScope(){
        return beanScope;
    }
}
