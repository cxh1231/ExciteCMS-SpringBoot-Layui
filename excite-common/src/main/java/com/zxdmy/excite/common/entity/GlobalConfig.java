package com.zxdmy.excite.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 组件配置信息表
 * </p>
 *
 * @author 拾年之璐
 * @since 2022-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GlobalConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置信息的模块：如system、user、component
     */
    private String confModule;

    /**
     * 配置信息的主键：如qiniu、alipay等等
     */
    private String confKey;

    /**
     * 配置信息的JSON值
     */
    private String confValue;

    /**
     * 是否加密：1-是 0-否
     */
    private Integer encrypt;

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
