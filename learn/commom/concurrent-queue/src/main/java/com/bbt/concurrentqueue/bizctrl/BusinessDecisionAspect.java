package com.bbt.concurrentqueue.bizctrl;

import com.bbt.concurrentqueue.bizctrl.annotation.BusinessInventoryCtrl;
import com.bbt.concurrentqueue.lock.service.LockService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description: 描述 注解切面
 * @Author:  zhangrc
 * @CreateDate:  2018/11/27 17:51
 */
@Aspect
@Component
public class BusinessDecisionAspect implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(BusinessDecisionAspect.class);

    /**
     * 业务并发锁
     */
    public static final String LOCK_KEY = "BUSINESS_DECISION_LOCK_";


    private ApplicationContext applicationContext;

    @Autowired
    private BusinessInventorySynService businessInventorySynService;

    @Autowired
    private LockService lockService;

    /**
     * 找到注解
     */
    @Pointcut("@annotation(com.bbt.concurrentqueue.bizctrl.annotation.BusinessInventoryCtrl)")
    public void aspect() {}


    @SuppressWarnings("rawtypes")
    @Around("aspect()")
    public Object aroundMethod(ProceedingJoinPoint point) {

        MethodSignature signature = (MethodSignature)point.getSignature();
        Method targetMethod = signature.getMethod();
        BusinessInventoryCtrl businessInventoryCtrl = targetMethod.getAnnotation(BusinessInventoryCtrl.class);

        int idParamIndex = businessInventoryCtrl.idParamIndex();
        String businessKey = businessInventoryCtrl.businessKey();
        boolean isLock = businessInventoryCtrl.isLock();

        BusinessDecisionService businessDecisionService = applicationContext.getBean(businessInventoryCtrl.using());

        Object businessId = point.getArgs()[idParamIndex];

        logger.info(idParamIndex+"----"+businessKey+"----"+isLock+"----"+businessId);

        // 缓存库存减一
        boolean isSuccess = businessInventorySynService.decreaseInventory(businessDecisionService, businessKey, businessId);

        if (!isSuccess) {
            return businessDecisionService.sellOut();
        }

        String lockKey = LOCK_KEY.concat(businessKey).concat("_").concat(businessId.toString());
        if (isLock) {
            boolean lock = lockService.tryLock(1000, 5000, lockKey);
            if (!lock) {
                return businessDecisionService.fail(new BusinessExecuteException());
            }
        }

        try {
            return point.proceed();
        }catch (BusinessExecuteException e){
            // 异常时缓存库存加一
            businessInventorySynService.increaseInventory(businessDecisionService, businessKey, businessId);
            return  businessDecisionService.fail(e);
        }catch (Throwable e) {
            businessInventorySynService.increaseInventory(businessDecisionService, businessKey, businessId);
            return  businessDecisionService.fail(new BusinessExecuteException(e));
        }finally {
            /*if (isLock) {
                lockService.unLock(lockKey);
            }*/
        }



    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext=applicationContext;

    }
}
