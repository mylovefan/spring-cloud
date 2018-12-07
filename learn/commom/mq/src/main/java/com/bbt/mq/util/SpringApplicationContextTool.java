package com.bbt.mq.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * ApplicationContext工具类
 */
@Component
public class SpringApplicationContextTool implements ApplicationContextAware {
    
    private SpringApplicationContextTool(){
    }
    
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }
    
    /**
     * 根据代理目标类获取它的代理
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public <P, T extends P> P getProxy(T target, Class<P> clazz) {
        P proxy = null;
        try {
            proxy = (P) getApplicationContext().getBean(target.getClass());
        } catch (NoSuchBeanDefinitionException e) {
        }
        // 如果proxy不为空,说明不是AOP代理或者是Cglib代理
        if (proxy != null) {
            return proxy;
        }
        
        // 寻找当前target的上级接口,Interfaces的类型与P相同
        ResolvableType resolvableType = getInterfaces(ResolvableType.forInstance(target), clazz);
        
        String[] beanNames = getApplicationContext().getBeanNamesForType(resolvableType);
        for (String beanName : beanNames) {
            proxy = (P) getApplicationContext().getBean(beanName);
            Object targetObject = AopTargetUtils.getTarget(proxy);
            if (targetObject.equals(target)) {
                return (P) proxy;
            }
        }
        return null;
    }
    
    /**
     * 获取目标类的上级代理类类型
     * @param resolvableType
     * @param clazz
     * @return
     * @author jiangxinjun
     * @createDate 2018年2月12日
     * @updateDate 2018年2月12日
     */
    private <P> ResolvableType getInterfaces(ResolvableType resolvableType, Class<P> clazz) {
        if (resolvableType.getInterfaces() == null || resolvableType.getInterfaces().length == 0) {
            // 如果当前接口为空
            resolvableType = resolvableType.getSuperType();
            return getInterfaces(resolvableType, clazz);
        }
        for (ResolvableType item : resolvableType.getInterfaces()) {
            if (item.resolve().equals(clazz)) {
                return item;
            }
        }
        return resolvableType;
    }
    
}