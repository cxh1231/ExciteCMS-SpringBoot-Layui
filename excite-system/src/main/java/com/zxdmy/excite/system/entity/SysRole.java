package com.zxdmy.excite.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色权限标识符，如：admin、guest
     */
    @NotBlank(message = "权限标识符不能为空")
    private String permission;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序：数值越大越靠前
     */
    private Integer sort;

    /**
     * 角色状态：0-封禁 | 1-正常 | 2-正常且禁止封禁
     */
    private Integer status;

    /**
     * 是否删除：0-未删除 | 1-已删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
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


    /**
     * 复选框使用的字段，默认为"0"，选中为"1"，
     */
    @TableField(exist = false)
    private String checkArr = "0";

    @TableField(exist = false)
    private String parentId = "-1";
}
