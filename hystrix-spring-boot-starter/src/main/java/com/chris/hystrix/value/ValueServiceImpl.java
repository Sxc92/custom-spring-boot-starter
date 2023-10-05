package com.chris.hystrix.value;

import com.alibaba.fastjson.JSON;
import com.chris.hystrix.annotation.Fuse;
import com.netflix.hystrix.*;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author 史偕成
 * @date 2023/10/04 21:55
 **/

public class ValueServiceImpl extends HystrixCommand<Object> implements IValueService {

    private ProceedingJoinPoint proceedingJoinPoint;


    private Method method;

    private Fuse fuse;

    public ValueServiceImpl(int time) {
        // 设置命令组
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("govern-group"))
                // 该命令的名称
                .andCommandKey(HystrixCommandKey.Factory.asKey("govern-key"))
                //
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("govern-thread-pool"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(time)
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10))
        );
    }


    @Override
    public Object access(ProceedingJoinPoint proceedingJoinPoint,
                         Method method,
                         Fuse fuse, Object[] args) throws Throwable {
        this.proceedingJoinPoint = proceedingJoinPoint;
        this.method = method;
        this.fuse = fuse;
        Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("govern-group"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(fuse.timeoutValue()));
        return this.execute();
    }

    @Override
    protected Object run() throws Exception {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            return null;
        }
    }

    @Override
    protected Object getFallback() {
        return JSON.parseObject(fuse.msg(), method.getReturnType());
    }
}
