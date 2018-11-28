package com.bbt.concurrentqueue.bizctrl.annotation;

import com.bbt.concurrentqueue.bizctrl.BusinessDecisionService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/27 17:52
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessInventoryCtrl {


    /**
     * 注解目标方法业务id参数位置索引
     * @return
     */
    int idParamIndex();

    /**
     * 业务key，标识一类义务，一个模块内不可重复
     * @return
     */
    String businessKey();

    /**
     * 实际业务处理服务
     * @return
     */
    Class<? extends BusinessDecisionService> using();

    /**
     * 是否加锁
     * @return
     */
    boolean isLock() default false;


}
