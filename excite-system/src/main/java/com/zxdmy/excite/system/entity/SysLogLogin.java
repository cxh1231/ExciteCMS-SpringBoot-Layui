package com.zxdmy.excite.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录用户ID
     */
    private Integer userId;

    /**
     * 登录用户账号
     */
    private String userAccount;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 浏览器Agent
     */
    private String userAgent;

    /**
     * 请求数据
     */
    private String reqData;

    /**
     * 返回数据
     */
    private String resData;

    /**
     * 耗时
     */
    private Integer timeCost;

    /**
     * 请求状态：0-失败 | 1-成功
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


}
