package com.zxdmy.excite.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxdmy.excite.system.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-01
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过角色ID查询拥有此角色的用户列表
     *
     * @param roleId 角色列表
     * @return 拥有此角色的用户列表
     */
    List<SysUser> selectUsersByRoleId(Integer roleId);

    /**
     * 通过角色ID查询不拥有此角色的用户列表
     *
     * @param roleId 角色列表
     * @return 不拥有此角色的用户列表
     */
    List<SysUser> selectUsersByRoleIdNotIn(Integer roleId);
}
