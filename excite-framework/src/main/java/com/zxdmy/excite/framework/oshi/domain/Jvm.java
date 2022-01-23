package com.zxdmy.excite.framework.oshi.domain;

import com.zxdmy.excite.common.utils.ArithUtils;
import com.zxdmy.excite.common.utils.DateUtils;

import java.io.Serializable;
import java.lang.management.ManagementFactory;

/**
 * 系统监控之：Java虚拟机信息实体
 *
 * @author 拾年之璐
 * @since 2022/1/22 20:47
 */
public class Jvm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前JVM占用的内存总数
     */
    private String total;

    /**
     * JVM最大可用内存总数
     */
    private String max;

    /**
     * JVM已用内存数目
     */
    private String used;

    /**
     * JVM空闲内存(M)
     */
    private String free;

    /**
     * JVM内存使用率
     */
    private String usage;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    /**
     * JDK启动时间
     */
    public String getStartTime() {
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
    }
}
