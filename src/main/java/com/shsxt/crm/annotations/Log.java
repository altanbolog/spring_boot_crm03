package com.shsxt.crm.annotations;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     * @return
     */
    String module() default "";

    /**
     * 操作描述
     * @return
     */
    String desc() default "";

}
