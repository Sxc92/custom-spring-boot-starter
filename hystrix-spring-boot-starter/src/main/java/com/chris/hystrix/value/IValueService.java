package com.chris.hystrix.value;

import com.chris.hystrix.annotation.Fuse;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author 史偕成
 * @date 2023/10/04 21:49
 **/
public interface IValueService {

    /**
     * 封装熔断保护
     *
     * @param proceedingJoinPoint
     * @param method
     * @param fuse
     * @param args
     * @return
     * @throws Throwable
     */
    Object access(ProceedingJoinPoint proceedingJoinPoint, Method method, Fuse fuse, Object[] args) throws Throwable;
}
