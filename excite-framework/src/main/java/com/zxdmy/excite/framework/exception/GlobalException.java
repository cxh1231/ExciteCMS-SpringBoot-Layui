package com.zxdmy.excite.framework.exception;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;

import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
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
    public BaseResult handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 打印堆栈，以供调试
        System.out.println("==============全局异常==============");
        e.printStackTrace();
        // 不同异常返回不同状态码

        String result = "系统错误，请重试！";
        // 如果是未登录异常
        if (e instanceof NotLoginException) {
            NotLoginException ee = (NotLoginException) e;
            result = "用户未登录";
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
//        else if(e instanceof HttpRequestMethodNotSupportedException){
//
//        }
        // 普通异常, 输出：500 + 异常信息
        else {
            result = e.getMessage();
        }
        // 返回给前端
        return error(500, result);
    }
}
