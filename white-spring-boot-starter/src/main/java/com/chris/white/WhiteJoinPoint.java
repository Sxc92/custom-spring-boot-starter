package com.chris.white;

import com.alibaba.fastjson.JSON;
import com.chris.common.entity.Result;
import com.chris.white.annotation.White;
import com.chris.white.config.WhiteProperties;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 史偕成
 * @date 2023/10/04 11:57
 **/
@Slf4j
@Aspect
//@Component
public class WhiteJoinPoint {
    @Resource
    private String whiteConfig;

    /**
     * 设置切点
     */
    @Pointcut("@annotation(com.chris.white.annotation.White)")
    public void whitePoint() {
    }

    @Around("whitePoint()")
    public Object router(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Method method = getMethod(proceedingJoinPoint);
        White whiteAnnotation = method.getAnnotation(White.class);
        String keyValue = getFieldValue(whiteAnnotation.key(), proceedingJoinPoint.getArgs());
        log.info("white handler method: {} value: {}", method.getName(), keyValue);

        if (null == keyValue || "".equals(keyValue)) {
            return proceedingJoinPoint.proceed();
        }

        String[] split = whiteConfig.split(",");
        if (split.length == 0) {
            // TODO 没有设置白名单 处理方式 读取数据库信息 然后进行

        } else {
            for (String s : split) {
                if (keyValue.equals(s)) {
                    return proceedingJoinPoint.proceed();
                }
            }
        }
        return returnObject(whiteAnnotation, method);
    }

    /**
     * 获取方法信息
     *
     * @param proceedingJoinPoint
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return proceedingJoinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
    }


    /**
     * 获取参数值
     *
     * @param field
     * @param args
     * @return
     */
    private String getFieldValue(String field, @NonNull Object[] args) {
        for (Object arg : args) {
            try {
                return BeanUtils.getProperty(arg, field);
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return null;
    }


    private Object returnObject(White white, Method method) {
        Class<?> returnType = method.getReturnType();
        String preReturnInfo = white.msg();
        if (preReturnInfo.isEmpty()) {
            try {
                return returnType.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.error(preReturnInfo, 501);
    }
}
