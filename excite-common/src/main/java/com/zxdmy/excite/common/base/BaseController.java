package com.zxdmy.excite.common.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 通用的控制类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-05 0005 22:58
 */
public class BaseController {

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    @ModelAttribute
    public void initReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 请求成功消息（返回码：200；Status Code：200）
     *
     * @param message 消息
     * @return 结果类
     */
    public BaseResult success(String message) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(HttpStatus.OK.value(), message);
    }

    /**
     * 请求成功消息（返回码：自定义；Status Code：200）
     *
     * @param code    状态码
     * @param message 消息
     * @return 结果类
     */
    public BaseResult success(int code, String message) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(code, message);
    }

    /**
     * 请求成功实体（返回码：200；Status Code：200）
     *
     * @param data 实体
     * @return 结果类
     */
    public BaseResult success(Object data) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(HttpStatus.OK.value(), data);
    }

    /**
     * 请求成功实体（返回码：自定义；Status Code：200）
     *
     * @param code 状态码
     * @param data 消息
     * @return 结果类
     */
    public BaseResult success(int code, Object data) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(code, data);
    }

    /**
     * 请求成功消息（返回码：自定义；Status Code：同返回码）
     *
     * @param httpStatus 状态码
     * @param message    消息
     * @return 结果类
     */
    public BaseResult success(HttpStatus httpStatus, String message) {
        response.setStatus(httpStatus.value());
        return new BaseResult(httpStatus.value(), message);
    }

    /**
     * 请求成功消息+数据（返回码：200；Status Code：200）
     *
     * @param message 消息
     * @param data    数据
     * @return 结果类
     */
    public BaseResult success(String message, Object data) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(HttpStatus.OK.value(), message, data);
    }

    /**
     * 请求成功消息+数据（返回码：自定义；Status Code：200）
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @return 结果类
     */
    public BaseResult success(int code, String message, Object data) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(code, message, data);
    }

    /**
     * 请求成功消息+数据（返回码：自定义；Status Code：同返回码）
     *
     * @param httpStatus 状态码
     * @param message    消息
     * @param data       数据
     * @return 结果类
     */
    public BaseResult success(HttpStatus httpStatus, String message, Object data) {
        response.setStatus(httpStatus.value());
        return new BaseResult(httpStatus.value(), message, data);
    }

    /**
     * 请求成功消息+数据+总数，用于分页显示（返回码：200；Status Code：200）
     *
     * @param message 消息
     * @param data    数据
     * @param count   记录数
     * @return 结果类
     */
    public BaseResult success(String message, Object data, int count) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(HttpStatus.OK.value(), message, data).put("count", count);
    }

    /**
     * 请求成功消息+数据+总数，用于分页显示（返回码：自定义；Status Code：200）
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @param count   记录数
     * @return 结果类
     */
    public BaseResult success(int code, String message, Object data, int count) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(code, message, data).put("count", count);
    }

    /**
     * 请求成功消息+数据+总数，用于分页显示（返回码：自定义；Status Code：同返回码）
     *
     * @param httpStatus 状态码
     * @param message    消息
     * @param data       数据
     * @param count      记录数
     * @return 结果类
     */
    public BaseResult success(HttpStatus httpStatus, String message, Object data, int count) {
        response.setStatus(httpStatus.value());
        return new BaseResult(httpStatus.value(), message, data).put("count", count);
    }


    /**
     * 请求失败消息（返回码：200）
     *
     * @param message 消息
     * @return 结果类
     */
    public BaseResult error(String message) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(HttpStatus.OK.value(), message);
    }

    /**
     * 请求失败消息（返回码：自定义）
     *
     * @param code    状态码
     * @param message 消息
     * @return 结果类
     */
    public BaseResult error(int code, String message) {
        response.setStatus(HttpStatus.OK.value());
        return new BaseResult(code, message);
    }

    /**
     * 请求失败消息（返回码：自定义；浏览器Status Code：同返回码）
     *
     * @param httpStatus 状态码
     * @param message    消息
     * @return 结果类
     */
    public BaseResult error(HttpStatus httpStatus, String message) {
        response.setStatus(httpStatus.value());
        return new BaseResult(httpStatus.value(), message);
    }

}
