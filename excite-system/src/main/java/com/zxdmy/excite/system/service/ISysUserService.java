package com.zxdmy.excite.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxdmy.excite.system.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-01
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 登录服务
     *
     * @param username 用户名
     * @param password 密码
     * @return SysUser：登录成功 | null：登录失败
     */
    public SysUser login(String username, String password);


    /**
     * 获取用户列表
     *
     * @param current  当前页
     * @param size     当前页大小
     * @param username 检索用户名
     * @param account  检索邮箱/手机号
     * @return 用户页面信息
     */
    public Page<SysUser> getPage(Integer current, Integer size, String username, String account);

    /**
     * 保存用户：添加 | 更新
     *
     * @param user    用户信息
     * @param roleIds 角色ID列表
     * @return 修改的行数 >0：成功 | <=0：失败
     */
    public int save(SysUser user, Integer[] roleIds);

    /**
     * 接口：修改用户状态
     *
     * @param newStatus 新状态
     * @param userIds   用户ID列表
     * @return 修改结果数组 0:成功个数 1:失败个数
     */
    public int[] changeStatus(Integer newStatus, Integer[] userIds);

    /**
     * 接口：给用户分配角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID数组
     * @return 修改结果数组 0:分配个数 1:失败个数
     */
    public boolean setRoleForUser(Integer userId, Integer[] roleIds);

    /**
     * 接口：根据ID删除用户
     *
     * @param userId 用户ID
     * @return 删除结果：>0 表示成功
     */
    int[] deleteUserById(Integer[] userId);

    /**
     * 接口：通过角色ID查询用户列表
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    List<SysUser> getUserListByRoleId(Integer roleId);

    /**
     * 接口：通过角色ID查询不包含此角色的用户列表
     *
     * @param roleId 角色ID
     * @return 不包含此角色的用户列表
     */
    List<SysUser> getUserListByRoleIdNotIn(Integer roleId);
}
