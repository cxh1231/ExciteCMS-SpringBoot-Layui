package com.zxdmy.excite.common.enums;

/**
 * <p>
 * 通用的返回结果返回码枚举类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-05 0005 23:59
 */
public enum ReturnCode {

    // 以下是常用的HTTP状态码
    /**
     * 客户端请求成功。状态码：200
     */
    OK(200, "OK"),
    /**
     * 客户端请求已创建。状态码：201
     */
    CREATED(201, "Created"),
    /**
     * 客户端请求已接受。状态码：202
     */
    ACCEPTED(202, "Accepted"),

    /**
     * 请求错误。状态码：400
     */
    BAD_REQUEST(400, "Bad Request"),
    /**
     * 拒绝访问。状态码：401
     */
    UNAUTHORIZED(401, "Unauthorized"),
    /**
     * 禁止访问。状态码：403
     */
    FORBIDDEN(403, "Forbidden"),
    /**
     * 请求不存在。状态码：404
     */
    NOT_FOUND(404, "Not Found"),
    /**
     * 方法不被允许。状态码：405
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    // 当然还可以添加自定义的其他返回码。
    INVALID_ID(40001, "不合法的ID"),
    INVALID_FILE_TYPE(40002, "不合法的文件类型"),
    INVALID_FILE_SIZE(40003, "不合法的文件大小"),
    INVALID_URL(40004, "不合法的链接");

    private final int code;

    private final String reason;

    ReturnCode(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return this.code;
    }

    public String getReason() {
        return this.reason;
    }
}
