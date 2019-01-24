package com.alibaba.metrics.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alibaba.metrics.MetricLevel;

/**
 * FastCompass类型对应的方法注解
 *
 * 方法需要返回非空字符串，用于表明
 *
 * 等价执行逻辑：
 *
 * //key()指注解中的key属性值, group()指注解中的group属性值
 * FastCompass globalFastCompass = MetricManager.getFastCompass(group(), MetricName.build(key()));
 *
 * //rt为targetMethod的执行时间，response取值规则：若方法抛出Throwable，取值exception，否则取值success
 * //请阅读{@link com.alibaba.metrics.FastCompass#record(long, String)}方法的注释
 * globalFastCompass.record(rt, response);
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableFastCompass {
    /**
     * Metric的group
     *
     * @return
     */
    String group();

    /**
     * MetricName中的key，请参考Dubbo-Metrics的命名规范进行命名
     *
     * @return
     */
    String key();

    /**
     * MetricName中的tag，请参考Dubbo-Metrics的命名规范进行命名
     *
     * 注意：以该注解方式使用Metrics，只能使用静态tag，无法实现根据参数或者返回值进行区别的动态参数，请直接使用Dubbo-Metrics的API实现
     *
     * @return
     */
    String tags() default "";

    /**
     * Metric的等级,默认为{@link MetricLevel#NORMAL},请参考Dubbo-Metrics的命名规范
     *
     * @return
     */
    MetricLevel level() default MetricLevel.NORMAL;
}
