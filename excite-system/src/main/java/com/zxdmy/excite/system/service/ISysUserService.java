package com.zxdmy.excite.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxdmy.excite.system.entity.SysUser;

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
     * 接口：根据ID删除用户
     *
     * @param userId 用户ID
     * @return 删除结果：>0 表示成功
     */
    int deleteUserById(Integer userId);
}
