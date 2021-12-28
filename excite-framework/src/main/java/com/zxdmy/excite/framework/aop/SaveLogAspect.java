package com.zxdmy.excite.framework.aop;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.bind.v2.TODO;
import com.zxdmy.excite.common.enums.SystemCode;
import com.zxdmy.excite.common.utils.HttpServletRequestUtil;
import com.zxdmy.excite.system.entity.SysLogLogin;
import com.zxdmy.excite.system.entity.SysLogRequest;
import com.zxdmy.excite.system.service.ISysLogLoginService;
import com.zxdmy.excite.system.service.ISysLogRequestService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.assertj.core.internal.ObjectArrays;
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
public class SaveLogAspect {

    ObjectMapper objectMapper;

    ISysLogRequestService logRequestService;

    ISysLogLoginService logLoginService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(AnnotationSaveReLog) || @within(AnnotationSaveReLog)")
    public void aspectSaveReLog() {
    }

    /**
     * 切入点
     */
    @Pointcut("@annotation(AnnotationSaveLoginLog) || @within(AnnotationSaveLoginLog)")
    public void aspectSaveLoginLog() {
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
        logRequest.setReqData(objectMapper.writeValueAsString(pjp.getArgs()));
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
            logRequest.setStatus(JSONUtil.parseObj(response).getBool("success", false) ? SystemCode.STATUS_Y.getCode() : SystemCode.STATUS_N.getCode());
        }
        // 写入时间
        logRequest.setCreateTime(LocalDateTime.now());
        // 写入执行时间间隔
        logRequest.setTimeCost((int) Duration.between(start, Instant.now()).toMillis());
        // 写入数据库
        logRequestService.save(logRequest);
        // 打印测试
//        System.out.println(logRequest);
        // 返回前端
        return returnValue;
    }

    /**
     * AOP：保存登录日志
     *
     * @param pjp ProceedingJoinPoint
     * @return 返回数据
     * @throws Throwable 异常
     */
    @Around("aspectSaveLoginLog()")
    public Object saveLoginLogService(ProceedingJoinPoint pjp) throws Throwable {
        // 请求开始时间
        Instant start = Instant.now();
        // 请求数据实体
        SysLogLogin logLogin = new SysLogLogin();
        // 已登录？直接返回
        if (StpUtil.isLogin()) {
            return pjp.proceed();
        }
        // 写入请求的IP
        logLogin.setIp(HttpServletRequestUtil.getRemoteIP());
        // 写入UA
        UserAgent userAgent = HttpServletRequestUtil.getRequestUserAgent();
        logLogin.setUserAgent(userAgent.getOs().getName() + ";" + userAgent.getBrowser().getName() + " " + userAgent.getVersion());
        // 解析请求数据
        String request = objectMapper.writeValueAsString(pjp.getArgs());
        System.out.println(request);
        logLogin.setReqData(request);
        // 写入请求用户名
        if (JSONUtil.isJsonObj(request)) {
            logLogin.setUserAccount(JSONUtil.parseObj(request).getStr("username", "非法用户"));
        } else if (JSONUtil.isJsonArray(request)) {
            logLogin.setUserAccount(JSONUtil.parseArray(request).getStr(0, "非法用户"));
        }
        // 执行方法获取的返回值
        Object returnValue;
        try {
            returnValue = pjp.proceed();
        } catch (Exception e) {
            // 写入状态异常
            logLogin.setStatus(SystemCode.STATUS_N.getCode());
            // 写入返回信息（异常信息）
            logLogin.setResData(e.getMessage());
            // 写入时间
            logLogin.setCreateTime(LocalDateTime.now());
            // 写入执行时间间隔
            logLogin.setTimeCost((int) Duration.between(start, Instant.now()).toMillis());
            // 写入数据库
            logLoginService.save(logLogin);
            // 抛出异常
            throw e;
        }
        // 没有异常，执行正常
        String response = objectMapper.writeValueAsString(returnValue);
        // 如果登录成功
        if (JSONUtil.isJsonObj(response)) {
            if (JSONUtil.parseObj(response).getBool("success", false)) {
                logLogin.setStatus(SystemCode.STATUS_Y.getCode());
                logLogin.setUserId(StpUtil.getLoginIdAsInt());
            }
            // 否则登录失败
            else {
                logLogin.setStatus(SystemCode.STATUS_N.getCode());
            }
        }

        // 写入返回值
        if (StrUtil.isNotEmpty(response)) {
            logLogin.setResData(response);
        }

        // 写入时间
        logLogin.setCreateTime(LocalDateTime.now());
        // 写入执行时间间隔
        logLogin.setTimeCost((int) Duration.between(start, Instant.now()).toMillis());
        // 写入数据库
        logLoginService.save(logLogin);
        return returnValue;
    }
}
