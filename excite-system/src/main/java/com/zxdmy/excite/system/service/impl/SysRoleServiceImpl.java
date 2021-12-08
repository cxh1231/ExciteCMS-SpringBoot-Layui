package com.zxdmy.excite.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxdmy.excite.common.config.ExciteConfig;
import com.zxdmy.excite.common.enums.SystemCode;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.RedisService;
import com.zxdmy.excite.system.entity.SysMenu;
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
import java.util.Set;

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

    RedisService redisService;

    ExciteConfig exciteConfig;

    /**
     * 添加|更新角色
     *
     * @param role     角色实体，ID为空表示添加
     * @param menusIds 为当前角色添加的菜单/权限ID列表
     * @return 影响的行数 0 - 失败 | >0 - 成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int saveRole(SysRole role, Integer[] menusIds) {
        if (null == role) {
            throw new ServiceException("角色实体不能为null");
        }
        // 初始化：角色状态
        if (null == role.getStatus()) {
            role.setStatus(SystemCode.STATUS_Y.getCode());
        }
        // 初始化：角色顺序
        if (null == role.getSort()) {
            role.setStatus(SystemCode.SORT_DEFAULT.getCode());
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        int result = 0;
        // 新增
        if (null == role.getId()) {
            role.setCreateTime(localDateTime);
            // 先插入角色，以便获取角色的ID
            result = roleMapper.insert(role);
            if (result > 0) {
                // 插入权限关联列表
                insertRoleMenu(role.getId(), menusIds);
            }
        }
        // 更新
        else {
            role.setUpdateTime(localDateTime);
            // 将新菜单插入
            result = roleMapper.updateById(role);
            if (result > 0) {
                // 先从关联表中删除旧数据
                QueryWrapper<SysRoleMenu> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("role_id", role.getId());
                roleMenuService.remove(wrapper1);
                // 再添加新数据
                insertRoleMenu(role.getId(), menusIds);
            }
        }
        this.deleteRedisUserMenuCache(result);
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
            throw new ServiceException("角色ID不能为null");
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
//        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
//        wrapper.eq("status", SystemCode.STATUS_Y.getCode());
//        return roleMapper.selectList(wrapper);
        return roleMapper.selectList(null);
    }

    /**
     * 改变角色状态
     *
     * @param newStatus 新状态
     * @param roleIds   角色ID列表
     * @return 结果数组
     */
    @Override
    public int[] changeStatus(Integer newStatus, Integer[] roleIds) {
        if (null == newStatus || null == roleIds) {
            throw new ServiceException("新状态或角色ID不能为空");
        }
        int[] result = new int[2];
        for (Integer roleId : roleIds) {
            SysRole role = new SysRole();
            QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
            role.setId(roleId);
            role.setStatus(newStatus);
            wrapper.eq("id", roleId)
                    .ne("status", SystemCode.STATUS_Y_BLOCK.getCode());
            if (roleMapper.update(role, wrapper) > 0)
                result[0]++;
            else result[1]++;
        }
        this.deleteRedisUserMenuCache(result[0]);
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
            throw new ServiceException("角色ID不能为null");
        }
        // 当该角色已经分配给用户，则禁止删除
        if (null != userRoleService.getListByRoleId(id)) {
            throw new ServiceException("当前角色已分配给用户，禁止删除！");
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

    /**
     * 如果角色信息有改动，则需要情况Redis里的用户权限缓存
     */
    private void deleteRedisUserMenuCache(int result) {
        if (exciteConfig.getAllowRedis() && result > 0) {
            // 获取已缓存的用户列表
            Set<Integer> userSet = (Set<Integer>) redisService.get("menu:userList");
            // 统统清空
            if (userSet != null) {
                for (Integer userId : userSet) {
                    redisService.remove("menu:userMenuList:" + userId);
                }
                redisService.remove("menu:userList");
            }
        }

    }
}
