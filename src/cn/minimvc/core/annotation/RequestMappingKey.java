/**
 * Copyright © 2018, TaoDing
 * <p>
 * All Rights Reserved.
 */


package cn.minimvc.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 *
 * @author Leon
 * @version 2018/4/2 10:32
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMappingKey {
    String value() default "";
}
