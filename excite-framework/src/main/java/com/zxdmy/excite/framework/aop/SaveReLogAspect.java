package com.zxdmy.excite.framework.aop;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxdmy.excite.common.enums.SystemCode;
import com.zxdmy.excite.common.utils.HttpServletRequestUtil;
import com.zxdmy.excite.system.entity.SysLogRequest;
import com.zxdmy.excite.system.service.ISysLogRequestService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 * AOP：保存指定请求的日志
 * </p>
 *
 * @author 拾年之璐
 * @since 2021/12/9 16:45
 */
@Aspect // AOP注解
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@AllArgsConstructor
public class SaveReLogAspect {

    ObjectMapper objectMapper;

    ISysLogRequestService logRequestService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(AnnotationSaveReLog) || @within(AnnotationSaveReLog)")
    public void aspectSaveReLog() {
    }

    /**
     * AOP：保存重要请求的日志
     *
     * @param pjp ProceedingJoinPoint
     * @return 返回数据
     * @throws Throwable 异常
     */
    @Around("aspectSaveReLog()")
    public Object saveReqLogService(ProceedingJoinPoint pjp) throws Throwable {
        // 请求开始时间
        Instant start = Instant.now();
        // 请求数据实体
        SysLogRequest logRequest = new SysLogRequest();
        // 已登录？写入登录用户的ID
        if (StpUtil.isLogin()) {
            logRequest.setUserId(StpUtil.getLoginIdAsInt());
        }
        // 写入请求的IP
        logRequest.setIp(HttpServletRequestUtil.getRemoteIP());
        // 写入UA
        UserAgent userAgent = HttpServletRequestUtil.getRequestUserAgent();
        logRequest.setUserAgent(userAgent.getOs().getName() + ";" + userAgent.getBrowser().getName() + " " + userAgent.getVersion());
        // 写入URI
        logRequest.setReqUri(HttpServletRequestUtil.getRequestURI());
        // 写入请求方法
        logRequest.setReqMethod(HttpServletRequestUtil.getRequest().getMethod());
        // 写入请求的函数
        logRequest.setReqFunction(pjp.getSignature().toShortString());
        // 写入请求的数据
        // TODO 转换为JSON格式
        logRequest.setReqData(Arrays.toString(pjp.getArgs()));
        // 执行方法获取的返回值
        Object returnValue;
        try {
            // 执行方法
            returnValue = pjp.proceed();
        } catch (Exception e) {
            // 写入返回信息（异常信息）
            logRequest.setResData(e.getMessage());
            // 写入状态异常
            logRequest.setStatus(SystemCode.STATUS_N.getCode());
            // 写入时间
            logRequest.setCreateTime(LocalDateTime.now());
            // 写入执行时间间隔
            logRequest.setTimeCost((int) Duration.between(start, Instant.now()).toMillis());
            // 写入数据库
            logRequestService.save(logRequest);
            // 抛出异常
            throw e;
        }
        // 没有异常，执行正常
        // 写入返回值
        String response = objectMapper.writeValueAsString(returnValue);
        if (StrUtil.isNotEmpty(response)) {
            logRequest.setResData(response);
        }
        // 写入请求状态
        if (JSONUtil.isJsonObj(response)) {
            logRequest.setStatus(JSONUtil.parseObj(response).getBool("success", true) ? SystemCode.STATUS_Y.getCode() : SystemCode.STATUS_N.getCode());
        }
        // 写入时间
        logRequest.setCreateTime(LocalDateTime.now());
        // 写入执行时间间隔
        logRequest.setTimeCost((int) Duration.between(start, Instant.now()).toMillis());
        // 写入数据库
        logRequestService.save(logRequest);
        // 打印测试
        System.out.println(logRequest);
        // 返回前端
        return returnValue;
    }
}
