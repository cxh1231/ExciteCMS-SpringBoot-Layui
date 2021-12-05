package com.zxdmy.excite.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxdmy.excite.common.enums.SystemCode;
import com.zxdmy.excite.system.entity.SysRole;
import com.zxdmy.excite.system.entity.SysRoleMenu;
import com.zxdmy.excite.system.mapper.SysRoleMapper;
import com.zxdmy.excite.system.service.ISysRoleMenuService;
import com.zxdmy.excite.system.service.ISysRoleService;
import com.zxdmy.excite.system.service.ISysUserRoleService;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-23
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    SysRoleMapper roleMapper;

    ISysRoleMenuService roleMenuService;

    ISysUserRoleService userRoleService;

    /**
     * 添加角色
     *
     * @param role     角色实体
     * @param menusIds 为当前角色添加的菜单/权限ID列表
     * @return 影响的行数 =0 - 失败 | >0 - 成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addRole(SysRole role, Integer[] menusIds) {
        if (null == role) {
//            throw new ServiceException("角色实体SysRole不能为null");
            return 0;
        }
        // 角色的其他信息初始化
        if(null == role.getStatus()){
            role.setStatus(SystemCode.STATUS_Y.getCode());
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        role.setCreateTime(localDateTime);
        // 先插入角色，以便获取角色的ID
        int result = roleMapper.insert(role);
        if (result > 0) {
            // 插入权限关联列表
            insertRoleMenu(role.getId(), menusIds);
        }
        // 返回
        return result;
    }

    /**
     * 根据ID查询某个角色详情
     *
     * @param id 角色的ID
     * @return 角色实体
     */
    @Override
    public SysRole getRole(Integer id) {
        if (null == id) {
//            throw new ServiceException("角色ID不能为null");
        }
        // 根据ID查询status正常的角色
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("status", SystemCode.STATUS_Y.getCode());
        return roleMapper.selectOne(wrapper);
    }

    /**
     * 查询角色列表
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> getList() {
        // 根据ID查询status正常的角色
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("status", SystemCode.STATUS_Y.getCode());
        return roleMapper.selectList(wrapper);
    }

    /**
     * 修改角色
     *
     * @param role     角色实体
     * @param menusIds 为当前角色添加的菜单/权限ID列表
     * @return 影响的行数 0-失败 | 1-成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateRole(SysRole role, Integer[] menusIds) {
        if (null == role) {
//            throw new ServiceException("角色实体SysRole不能为null");
        }
        // 初始化数据
        LocalDateTime localDateTime = LocalDateTime.now();
        role.setUpdateTime(localDateTime);
        // 将新菜单插入
        int result = roleMapper.updateById(role);
        if (result > 0) {
            // 先从关联表中删除旧数据
            QueryWrapper<SysRoleMenu> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("role_id", role.getId());
            roleMenuService.remove(wrapper1);
            // 再添加新数据
            insertRoleMenu(role.getId(), menusIds);
        }
        return result;
    }

    /**
     * 通过ID删除角色
     *
     * @param id 角色ID
     * @return 影响的行数 0-失败 | 1-成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteRoleById(Integer id) {
        if (null == id) {
//            throw new ServiceException("角色ID不能为null");
        }
        // 当该角色已经分配给用户，则禁止删除
        if (null != userRoleService.getListByRoleId(id)) {
//            throw new ServiceException("当前角色已分配给用户，禁止删除！");
        }
        // 删除角色-权限关联表中的数据
        QueryWrapper<SysRoleMenu> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("role_id", id);
        roleMenuService.remove(wrapper1);
        // 删除该角色
        return roleMapper.deleteById(id);
    }

    /**
     * 将角色和菜单/权限的关联，写入关联表
     *
     * @param roleId   角色ID
     * @param menusIds 菜单ID
     */
    public void insertRoleMenu(Integer roleId, Integer[] menusIds) {
        List<SysRoleMenu> roleMenuList = new ArrayList<>();
        for (Integer menuId : menusIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        }
        // 如果权限列表不为空
        if (roleMenuList.size() > 0) {
            // 批量插入
            roleMenuService.saveBatch(roleMenuList);
        }
    }
}
