package com.luckybird.springbackend.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 鉴权注解
 * 该注解用于标识不需要进行权限验证的方法，在方法上添加该注解后，该方法将不再需要进行权限验证，即无需携带token进行访问。
 *
 * @author 新云鸟
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuth {
}
