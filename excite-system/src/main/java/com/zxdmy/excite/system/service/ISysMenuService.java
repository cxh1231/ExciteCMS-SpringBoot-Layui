package com.zxdmy.excite.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxdmy.excite.system.entity.SysMenu;


import java.util.List;

/**
 * <p>
 * 系统菜单/权限表 服务类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-12
 */
public interface ISysMenuService extends IService<SysMenu> {


    /**
     * 添加或更新一个菜单/权限
     * 使用接口：新增接口、编辑接口
     *
     * @param menu 菜单/权限 实体
     * @return 影响的行数 0-失败 | 1-成功
     */
    public int saveMenu(SysMenu menu);

    /**
     * 修改菜单的状态
     * 使用接口：修改菜单状态接口
     *
     * @param newStatus 新状态
     * @return 影响的行数 0-失败 | 1-成功
     */
    public int[] changeStatus(int newStatus, Integer[] menuIds);

    /**
     * 获取一个菜单/权限
     * 使用接口：编辑接口（读取当前编辑的菜单详情）
     *
     * @param id 菜单/权限的ID
     * @return 菜单实体
     */
    public SysMenu getMenu(Integer id);

    /**
     * 获取菜单/权限列表
     *
     * @param isAll True:返回全部 | false:只返回目录+菜单
     * @return 菜单实体列表
     */
    public List<SysMenu> getMenuList(Boolean isAll);

    /**
     * 删除一个菜单/权限
     *
     * @param id 菜单/权限的ID
     * @return 影响的行数 0-失败 | 1-成功
     */
    public int deleteMenu(Integer id);

    /**
     * 通过角色ID查询全部菜单列表
     * 使用接口：角色管理---编辑角色，用于加载当前角色已经选中的菜单权限
     *
     * @param roleId 角色ID。若为0，表示不指定角色ID。
     * @return 全部菜单列表
     */
    public List<SysMenu> getMenuListForRole(int roleId);

    /**
     * 权限管理模块使用到的获取权限列表
     *
     * @return
     */
    public List<SysMenu> getMenuListForSaToken();

    /**
     * 通过用户的ID获取权限列表
     *
     * @param userId 用户ID
     * @param userId 用户ID     * @param isOnlyMenu 是否只获取菜单（不含按钮）
     * @return 菜单/权限列表
     */
    public List<SysMenu> getMenuListByUserId(Integer userId, boolean isOnlyMenu);

    /**
     * 通过角色的ID查询该角色拥有的菜单/权限
     *
     * @param roleId 角色ID
     * @return 菜单/权限列表
     */
    public List<SysMenu> getMenuListByRoleId(Integer roleId);


}
