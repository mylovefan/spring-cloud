package com.bbt.mq.annotation;

import java.lang.annotation.*;

/**
 * @Description:  补偿性事务主逻辑注解
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 15:20
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CompensatingTransactionMain {

    byte value();

    int seconds() default 60;

    String topic();

    String tags() default "";
}
