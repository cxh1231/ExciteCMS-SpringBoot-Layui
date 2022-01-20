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
     * 接口：添加|更新角色
     *
     * @param role     角色实体，ID为空表示添加
     * @param menusIds 为当前角色添加的菜单/权限ID列表
     * @return 影响的行数 0 - 失败 | >0 - 成功
     */
    public int saveRole(SysRole role, Integer[] menusIds);

    /**
     * 接口：根据ID查询某个角色详情
     *
     * @param id 角色的ID
     * @return 角色实体
     */
    public SysRole getRole(Integer id);

    /**
     * 接口：查询状态正常的角色列表
     *
     * @return 角色列表
     */
    public List<SysRole> getListByUserId(Integer userId);

    /**
     * 接口：分页查询接口列表
     *
     * @param current    当前页
     * @param size       页大小
     * @param name       检索：名称
     * @param permission 检索：权限字符
     * @return 结果页面
     */
    public Page<SysRole> getPage(Integer current, Integer size, String name, String permission);

    /**
     * 接口：修改角色状态
     *
     * @param newStatus 新状态
     * @param roleIds   角色ID列表
     * @return 结果
     */
    public int[] changeStatus(Integer newStatus, Integer[] roleIds);

    /**
     * 接口：删除角色
     *
     * @param roleIds 角色ID数组
     * @return 删除结果：0:成功个数 1:删除失败个数 2:因已分配用户，禁止删除数
     */
    public int[] deleteRoleById(Integer[] roleIds);


    /**
     * 接口：将指定角色，授权分配给系列用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID列表
     * @return 授权结果
     */
    public boolean authRoleForUsers(Integer roleId, Integer[] userIds);

    /**
     * 接口：取消指定角色已授权的系列用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID列表
     * @return 取消授权结果
     */
    public int[] revokeRoleForUsers(Integer roleId, Integer[] userIds);
}
