package com.zxdmy.excite.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 请求用户ID
     */
    private Integer userId;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 浏览器Agent
     */
    private String userAgent;

    /**
     * 请求的URI
     */
    private String reqUri;

    /**
     * 请求方法：GET | POST | DELETE
     */
    private String reqMethod;
    /**
     * 请求的函数
     */
    private String reqFunction;

    /**
     * 请求的数据
     */
    private String reqData;

    /**
     * 返回的数据
     */
    private String resData;

    /**
     * 耗时，单位：ms
     */
    private Integer timeCost;

    /**
     * 耗时，单位：ms
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime deleteTime;


}
