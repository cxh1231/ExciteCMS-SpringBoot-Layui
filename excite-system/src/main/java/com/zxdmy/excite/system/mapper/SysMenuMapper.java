package com.zxdmy.excite.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zxdmy.excite.system.entity.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统菜单/角色表 Mapper 接口
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-12
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色ID查询菜单/权限（不含禁用）
     *
     * @param roleId 角色ID
     * @return 菜单/权限列表
     */
    List<SysMenu> selectMenusByRoleId(Integer roleId);

    /**
     * 根据用户的ID查询菜单/权限（不含禁用）
     *
     * @param userId     用户的ID
     * @param isOnlyMenu 是否只包含菜单：1：只返回菜单 | 0：返回包含菜单+按钮
     * @return 菜单/权限列表
     */
    List<SysMenu> selectMenusByUserId(Integer userId, int isOnlyMenu);
}
