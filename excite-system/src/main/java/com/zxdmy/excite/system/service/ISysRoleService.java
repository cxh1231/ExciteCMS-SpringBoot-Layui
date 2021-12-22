package com.zxdmy.excite.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * 添加|更新角色
     *
     * @param role     角色实体，ID为空表示添加
     * @param menusIds 为当前角色添加的菜单/权限ID列表
     * @return 影响的行数 0 - 失败 | >0 - 成功
     */
    public int saveRole(SysRole role, Integer[] menusIds);

    /**
     * 根据ID查询某个角色详情
     *
     * @param id 角色的ID
     * @return 角色实体
     */
    public SysRole getRole(Integer id);

    /**
     * 查询状态正常的角色列表
     *
     * @return 角色列表
     */
    public List<SysRole> getList();


    public Page<SysRole> getPage(Integer current, Integer size, String name, String permission);
    /**
     * 修改角色状态
     *
     * @param newStatus 新状态
     * @param roleIds   角色ID列表
     * @return 结果
     */
    public int[] changeStatus(Integer newStatus, Integer[] roleIds);

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 影响的行数 0-失败 | 1-成功
     */
    public int deleteRoleById(Integer id);

}
