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


    Page<SysUser> getPage(Integer current, Integer size, String username, String account);
}
