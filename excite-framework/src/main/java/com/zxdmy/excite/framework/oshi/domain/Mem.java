package com.zxdmy.excite.framework.oshi.domain;

import cn.hutool.core.util.NumberUtil;
import com.zxdmy.excite.common.utils.ArithUtils;
import lombok.Data;

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
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal()
    {
        return ArithUtils.div(total, (1024 * 1024 * 1024), 2);
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public double getUsed()
    {
        return ArithUtils.div(used, (1024 * 1024 * 1024), 2);
    }

    public void setUsed(long used)
    {
        this.used = used;
    }

    public double getFree()
    {
        return ArithUtils.div(free, (1024 * 1024 * 1024), 2);
    }

    public void setFree(long free)
    {
        this.free = free;
    }

    public double getUsage()
    {
        return ArithUtils.mul(ArithUtils.div(used, total, 4), 100);
    }
}
