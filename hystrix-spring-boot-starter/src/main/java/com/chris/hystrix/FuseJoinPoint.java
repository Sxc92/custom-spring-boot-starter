package com.chris.hystrix;

import com.chris.hystrix.annotation.Fuse;
import com.chris.hystrix.value.IValueService;
import com.chris.hystrix.value.ValueServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 史偕成
 * @date 2023/10/04 22:34
 **/
@Aspect
@Component
public class FuseJoinPoint {

    @Pointcut("@annotation(com.chris.hystrix.annotation.Fuse)")
    public void fusePoint() {
    }

    @Around(value = "fusePoint() && @annotation(fuse)")
    public Object doRoute(ProceedingJoinPoint proceedingJoinPoint, Fuse fuse) throws Throwable {
        IValueService iValueService = new ValueServiceImpl(fuse.timeoutValue());
        return iValueService.access(proceedingJoinPoint, getMethod(proceedingJoinPoint),
                fuse, proceedingJoinPoint.getArgs());
    }


    private Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
    }
}
