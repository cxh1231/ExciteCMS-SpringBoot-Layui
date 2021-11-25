package com.zxdmy.excite.common.base;

import java.util.HashMap;

/**
 * <p>
 * 通用的返回结果类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-05 0005 23:01
 */
public class BaseResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 几个静态常量
     */
    private static final Integer MAX_SUCCESS_VALUE = 299;
    private static final String SUCCESS = "success";
    private static final String CODE = "code";
    private static final String MSG = "msg";
    private static final String DATA = "data";
    private static final String COUNT = "count";
    private static final String PAGE = "page";
    private static final String SIZE = "size";

    /**
     * 初始化 BaseResult 对象，其消息为空。
     */
    public BaseResult() {
    }

    /**
     * 封装结果类，消息。
     *
     * @param code 状态代码
     * @param msg  消息内容
     */
    public BaseResult(int code, String msg) {
        if (code <= MAX_SUCCESS_VALUE) {
            super.put(SUCCESS, true);
        } else {
            super.put(SUCCESS, false);
        }
        super.put(CODE, code);
        super.put(MSG, msg);
    }

    /**
     * 封装结果类，实体。
     *
     * @param code 状态代码
     * @param data 消息实体
     */
    public BaseResult(int code, Object data) {
        if (code <= MAX_SUCCESS_VALUE) {
            super.put(SUCCESS, true);
        } else {
            super.put(SUCCESS, false);
        }
        super.put(CODE, code);
        if (null != data) {
            super.put(DATA, data);
        }
    }


    /**
     * 封装结果类，消息+实体。
     *
     * @param code 状态类型
     * @param msg  消息内容
     * @param data 数据对象
     */
    public BaseResult(int code, String msg, Object data) {
        if (code <= MAX_SUCCESS_VALUE) {
            super.put(SUCCESS, true);
        } else {
            super.put(SUCCESS, false);
        }
        super.put(CODE, code);
        super.put(MSG, msg);
        if (null != data) {
            super.put(DATA, data);
        }
    }


    /**
     * 以上三类是默认的类型。如果还有其他参数要返回，使用.put(key,value)的链式调用方式追加。
     *
     * @param key   键
     * @param value 值/任意数据对象
     * @return 链式调用
     */
    @Override
    public BaseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
