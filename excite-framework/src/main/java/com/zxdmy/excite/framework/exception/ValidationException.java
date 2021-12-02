//package com.zxdmy.excite.framework.exception;
//
//
//import com.zxdmy.excite.common.base.BaseController;
//import com.zxdmy.excite.common.base.BaseResult;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * <p>
// * 数据校验错误异常处理
// * </p>
// *
// * @author 拾年之璐
// * @since 2021-09-12 0012 23:12
// */
//@ControllerAdvice
//public class ValidationException extends BaseController {
//
//    /**
//     * 处理@Validated参数校验失败异常
//     *
//     * @param exception BindException异常类
//     * @return 统一返回结果
//     */
//    @ResponseBody
//    @ExceptionHandler(value = BindException.class)
//    public BaseResult validationException(BindException exception) {
//        BindingResult bindingResult = exception.getBindingResult();
//        StringBuilder errMsg = new StringBuilder();
//        if (bindingResult.hasErrors()) {
//            Integer i = 1;
//            for (FieldError fieldError : bindingResult.getFieldErrors()) {
//                errMsg.append(i).append(".").append(fieldError.getDefaultMessage()).append("!");
//                i++;
//            }
//        }
//        // 返回错误结果
//        return error(HttpStatus.BAD_REQUEST, errMsg.toString());
//    }
//}
