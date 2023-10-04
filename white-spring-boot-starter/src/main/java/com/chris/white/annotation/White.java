package com.chris.white.annotation;

import java.lang.annotation.*;

/**
 * @author 史偕成
 * @date 2023/10/04 11:41
 **/
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface White {
    /**
     *
     * @return
     */
    String key() default "";

    /**
     * 在拦截到请求 可以设置返回消息
     *
     * @return
     */
    String msg() default "";
}
