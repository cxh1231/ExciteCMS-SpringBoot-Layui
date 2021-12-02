package com.zxdmy.excite.framework.exception;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;

import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 全局异常处理
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-10-12 0012 20:57
 */
@ControllerAdvice
public class GlobalException extends BaseController {

    @ResponseBody
    @ExceptionHandler
    public Object handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 打印堆栈，以供调试
        System.out.println("==============全局异常==============");
        e.printStackTrace();
        // 不同异常返回不同状态码

        String result = "系统错误，请重试！";
        // 如果是未登录异常
        if (e instanceof NotLoginException) {
            NotLoginException ee = (NotLoginException) e;
            result = "用户未登录";
            // 前后端同域，所以未登录就直接跳转至登录页面
            response.sendRedirect(request.getContextPath() + "/system/login");
        }
        // 如果是角色异常
        else if (e instanceof NotRoleException) {
            NotRoleException ee = (NotRoleException) e;
            result = "无此角色：" + ee.getRole();
        }
        // 如果是权限异常
        else if (e instanceof NotPermissionException) {
            NotPermissionException ee = (NotPermissionException) e;
            result = "无此权限：" + ee.getCode();
        }
        // 如果是被封禁异常
        else if (e instanceof DisableLoginException) {
            DisableLoginException ee = (DisableLoginException) e;
            result = "账号被封禁：" + ee.getDisableTime() + "秒后解封";
        }
        // 不支持的请求
        else if (e instanceof HttpRequestMethodNotSupportedException) {
            return "请求不支持";
        }
        // 信息校验失败提示
        else if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            StringBuilder errMsg = new StringBuilder();
            if (bindingResult.hasErrors()) {
                Integer i = 1;
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    errMsg.append(i).append(". ").append(fieldError.getDefaultMessage()).append("！");
                    i++;
                }
            }
            result = errMsg.toString();
        }
        // 其他异常, 输出：500 + 异常信息
        else {
            result = e.getMessage();
        }
        // 返回给前端
        return error(500, result);
    }
}
