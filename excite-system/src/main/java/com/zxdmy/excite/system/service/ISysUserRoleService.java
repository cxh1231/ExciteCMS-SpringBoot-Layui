package com.zxdmy.excite.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxdmy.excite.system.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 系统用户和角色关联表 服务类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-23
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 通过角色ID获取用户角色关联列表
     *
     * @param roleId 角色ID
     * @return 用户角色关联列表
     */
    public List<SysUserRole> getListByRoleId(Integer roleId);

    /**
     * 通过用户ID获取用户角色关联列表
     *
     * @param userId 用户ID
     * @return 用户角色关联列表
     */
    public List<SysUserRole> getListByUserId(Integer userId);

}
