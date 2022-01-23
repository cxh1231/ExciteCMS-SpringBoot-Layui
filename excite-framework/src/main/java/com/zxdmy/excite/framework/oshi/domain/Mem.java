package com.zxdmy.excite.framework.oshi.domain;

import com.zxdmy.excite.common.utils.ArithUtils;

import java.io.Serializable;

/**
 * 系统监控之：内存信息实体
 *
 * @author 拾年之璐
 * @since 2022/1/22 20:51
 */
public class Mem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 内存总量
     */
    private String total;

    /**
     * 已用内存
     */
    private String used;

    /**
     * 剩余内存
     */
    private String free;

    /**
     * 使用率
     */
    private String usage;

    public String getTotal() {
//        return ArithUtils.div(total, (1024 * 1024 * 1024), 2);
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
//        return ArithUtils.div(used, (1024 * 1024 * 1024), 2);
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getFree() {
//        return ArithUtils.div(free, (1024 * 1024 * 1024), 2);
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
//        return ArithUtils.mul(ArithUtils.div(used, total, 4), 100);
        return this.usage;
    }
}
