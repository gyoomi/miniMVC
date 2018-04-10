/**
 * Copyright © 2018, TaoDing
 * <p>
 * All Rights Reserved.
 */


package cn.minimvc.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解类
 *
 * @author Leon
 * @version 2018/4/2 10:31
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ControllerKey {
    String value() default "";
}
