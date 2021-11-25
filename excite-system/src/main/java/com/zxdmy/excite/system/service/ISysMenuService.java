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
     * 添加一个菜单/权限
     *
     * @param menu 菜单/权限 实体
     * @return 影响的行数 0-失败 | 1-成功
     */
    public int addMenu(SysMenu menu, String cacheKey);

    /**
     * 获取一个菜单/权限
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
     * 权限管理模块使用到的获取权限列表
     *
     * @param cacheKey 列表的key值
     * @return
     */
    public List<SysMenu> getMenuListForSaToken(String cacheKey);

    /**
     * 通过用户的ID获取权限列表
     *
     * @param userId 用户ID
     * @return
     */
    public List<SysMenu> getMenuListByUserId(Integer userId);

    /**
     * 通过角色的ID查询该角色拥有的菜单/权限
     *
     * @param roleId 角色ID
     * @return 菜单/权限列表
     */
    public List<SysMenu> getMenuListByRoleId(Integer roleId);

    /**
     * 更新一个菜单/权限
     *
     * @param menu 菜单/权限 实体
     * @return 影响的行数 0-失败 | 1-成功
     */
    public int updateMenu(SysMenu menu, String cacheKey);

    /**
     * 删除一个菜单/权限
     *
     * @param id 菜单/权限的ID
     * @return 影响的行数 0-失败 | 1-成功
     */
    public int deleteMenu(Integer id, String cacheKey);


}
