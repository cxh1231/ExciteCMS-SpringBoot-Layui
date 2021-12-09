package com.zxdmy.excite.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单/权限表
 * </p>
 * 不返回为null的字段
 *
 * @author 拾年之璐
 * @since 2021-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单/权限ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单/权限名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @JsonProperty("title")
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单/权限路由：对于后台控制类定义，示例：system/user/get/{id}
     */
    @JsonProperty("href")
    private String path;

    /**
     * 权限标识符：对于后台控制类定义。示例：system:user:list
     */
    @NotBlank(message = "权限标识不能为空")
    private String permission;

    /**
     * 组件路径：对于前端框架定义。示例：system/user/index
     */
    private String component;

    /**
     * 上级菜单ID
     */
    private Integer parentId;

    /**
     * 菜单类型：N - 顶部菜单 | C - 目录 | M - 菜单 | B - 按钮
     */
    @NotBlank(message = "菜单类型不能为空")
    private String type;

    /**
     * 排序：数值越大越靠前
     */
    @NotNull(message = "菜单显示顺序不能为空")
    private Integer sort;

    /**
     * 可编辑：0-禁止编辑 | 1-可编辑
     */
    private Integer editable;

    /**
     * 可删除：0-禁止删除 | 1-可删除
     */
    private Integer removable;

    /**
     * 菜单状态：0-封禁 | 1-正常 | 2-正常且禁止封禁
     */
    private Integer status;

    /**
     * 是否删除：0-未删除 | 1-已删除
     */
    @JsonIgnore
    private Integer isDelete;

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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime deleteTime;

    /**
     * 子菜单列表
     */
    @TableField(exist = false)
    private List<SysMenu> child;

    /**
     * 复选框使用的字段，默认为"0"，选中为"1"，
     */
    @TableField(exist = false)
    private String checkArr = "0";
}
