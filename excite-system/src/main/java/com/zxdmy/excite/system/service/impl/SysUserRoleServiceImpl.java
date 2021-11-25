package com.zxdmy.excite.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxdmy.excite.system.entity.SysUserRole;
import com.zxdmy.excite.system.mapper.SysUserRoleMapper;
import com.zxdmy.excite.system.service.ISysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统用户和角色关联表 服务实现类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-23
 */
@Service
@AllArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    SysUserRoleMapper userRoleMapper;

    /**
     * 通过角色ID获取用户角色关联列表
     *
     * @param roleId 角色ID
     * @return 用户角色关联列表
     */
    @Override
    public List<SysUserRole> getListByRoleId(Integer roleId) {
        QueryWrapper<SysUserRole> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("role_id", roleId);
        return userRoleMapper.selectList(wrapper1);
    }

    /**
     * 通过用户ID获取用户角色关联列表
     *
     * @param userId 用户ID
     * @return 用户角色关联列表
     */
    @Override
    public List<SysUserRole> getListByUserId(Integer userId) {
        QueryWrapper<SysUserRole> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("user_id", userId);
        return userRoleMapper.selectList(wrapper1);
    }
}
