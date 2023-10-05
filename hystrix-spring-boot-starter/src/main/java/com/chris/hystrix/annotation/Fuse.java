package com.chris.hystrix.annotation;

import java.lang.annotation.*;

/**
 * 熔断注解
 * @author 史偕成
 * @date 2023/10/04 11:41
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Fuse {
    /**
     * 设置超时时常
     *
     * @return
     */
    int timeoutValue() default 0;

    /**
     * 在拦截到请求 可以设置返回消息
     *
     * @return
     */
    String msg() default "";
}
