package com.zxdmy.excite.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxdmy.excite.system.entity.SysRole;


import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-23
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 添加角色
     *
     * @param role     角色实体
     * @param menusIds 为当前角色添加的菜单/权限ID列表
     * @return 影响的行数 0 - 失败 | >0 - 成功
     */
    public int addRole(SysRole role, Integer[] menusIds);

    /**
     * 根据ID查询某个角色详情
     *
     * @param id 角色的ID
     * @return 角色实体
     */
    public SysRole getRole(Integer id);

    /**
     * 查询角色列表
     *
     * @return 角色列表
     */
    public List<SysRole> getList();

    /**
     * 修改角色
     *
     * @param role     角色实体
     * @param menusIds 为当前角色添加的菜单/权限ID列表
     * @return 影响的行数 0-失败 | 1-成功
     */
    public int updateRole(SysRole role, Integer[] menusIds);

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 影响的行数 0-失败 | 1-成功
     */
    public int deleteRoleById(Integer id);

}
