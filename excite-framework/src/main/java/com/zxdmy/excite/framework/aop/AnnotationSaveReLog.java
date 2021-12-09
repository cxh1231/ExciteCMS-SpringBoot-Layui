package com.zxdmy.excite.framework.aop;

import java.lang.annotation.*;

/**
 * <p>
 * 自定义注解：用在控制类或者控制类的方法上，用于保存请求和响应的日志
 * </p>
 *
 * @author 拾年之璐
 * @since 2021/12/9 17:22
 */
@Retention(RetentionPolicy.RUNTIME) // 何时运行和结束
@Target({ElementType.TYPE, ElementType.METHOD}) // 注解作用位置
public @interface AnnotationSaveReLog {
}
