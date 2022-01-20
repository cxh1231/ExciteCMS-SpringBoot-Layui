package com.zxdmy.excite.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxdmy.excite.common.config.ExciteConfig;
import com.zxdmy.excite.common.enums.SystemCode;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.RedisService;
import com.zxdmy.excite.system.entity.SysMenu;
import com.zxdmy.excite.system.entity.SysRole;
import com.zxdmy.excite.system.entity.SysRoleMenu;
import com.zxdmy.excite.system.entity.SysUserRole;
import com.zxdmy.excite.system.mapper.SysRoleMapper;
import com.zxdmy.excite.system.service.ISysRoleMenuService;
import com.zxdmy.excite.system.service.ISysRoleService;
import com.zxdmy.excite.system.service.ISysUserRoleService;


import com.zxdmy.excite.system.utils.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
     * 接口实现：添加|更新角色
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
            role.setIsDelete(SystemCode.DELETE_N.getCode());
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
            // 清除缓存
            AuthUtils.clearUserMenuListCache(result != 0);
        }
        return result;
    }

    /**
     * 接口实现：根据ID查询某个角色详情
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
        wrapper.eq("id", id).ne("status", SystemCode.STATUS_N.getCode());
        return roleMapper.selectOne(wrapper);
    }

    /**
     * 接口实现：根据用户ID，查询状态正常的角色列表，其中该用户拥有的角色的checkArr字段为1
     *
     * @param userId 用户的ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> getListByUserId(Integer userId) {
        // 查询status正常的角色
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.ne("status", SystemCode.STATUS_N.getCode());
        // 如果userId为0，则表示获取全部角色
        if (userId == 0) {
            return roleMapper.selectList(wrapper);
        }
        // 否则，获取角色后，将该用户已分配的角色checkArr设置为1
        else {
            // 获取全部正常角色
            List<SysRole> roleList = roleMapper.selectList(wrapper);
            // 获取当前用户拥有的角色ID列表
            List<Integer> userRoleList = userRoleService.getListByUserId(userId).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            // 使用lambda表达式，直接在角色列表上修改checkArr字段
            return roleList.stream().peek(
                    role -> {
                        if (userRoleList.contains(role.getId())) {
                            role.setCheckArr("1");
                            role.setLAY_CHECKED(true);
                        }
                    }
            ).collect(Collectors.toList());
        }
    }

    /**
     * 接口实现：分页查询接口列表
     *
     * @param current    当前页
     * @param size       页大小
     * @param name       检索：名称
     * @param permission 检索：权限字符
     * @return 结果页面
     */
    @Override
    public Page<SysRole> getPage(Integer current, Integer size, String name, String permission) {
        current = null == current ? 1 : current;
        size = null == size ? 10 : size;
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.like(null != name && !"".equals(name), "name", name)
                .like(null != permission && !"".equals(permission), "permission", permission);
        return roleMapper.selectPage(new Page<>(current, size), wrapper);
    }

    /**
     * 接口实现：改变角色状态
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
        // 清除缓存
        AuthUtils.clearUserMenuListCache(result[0] != 0);
        return result;
    }

    /**
     * 接口实现：通过ID删除角色（注意：已经分配给用户的角色，不允许删除！）
     *
     * @param roleIds 角色ID
     * @return 删除结果：0:成功个数 1:删除失败个数 2:因已分配用户，禁止删除数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int[] deleteRoleById(Integer[] roleIds) {
        if (null == roleIds || roleIds.length == 0) {
            throw new ServiceException("删除的角色ID不能为空！");
        }
        int[] result = new int[3];
        for (int roleId : roleIds) {
            // 已经分配用户的角色，不允许删除！
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            if (userRoleService.count(queryWrapper) > 0) {
                // throw new ServiceException("当前角色已分配给用户，禁止删除！");
                result[2]++;
            } else {
                // 删除 用户-角色关联表 中的数据
                QueryWrapper<SysUserRole> userRoleQueryWrapper = new QueryWrapper<>();
                userRoleQueryWrapper.eq("role_id", roleId);
                userRoleService.remove(userRoleQueryWrapper);
                // 删除 角色-权限关联表 中的数据
                QueryWrapper<SysRoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
                roleMenuQueryWrapper.eq("role_id", roleId);
                roleMenuService.remove(roleMenuQueryWrapper);
                // 删除该角色
                if (roleMapper.deleteById(roleId) > 0) {
                    result[0]++;
                } else {
                    result[1]++;
                }
            }
        }
        return result;
    }

    /**
     * 接口：将指定角色，授权分配给系列用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID列表
     * @return 授权结果
     */
    @Override
    public boolean authRoleForUsers(Integer roleId, Integer[] userIds) {
        // 如果用户列表为空，则直接返回真即可
        if (null == userIds || userIds.length == 0) {
            return true;
        }
        // 存储列表
        List<SysUserRole> userRoleList = new ArrayList<>();
        for (Integer userId : userIds) {
            // 构造数据，并添加至列表
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRoleList.add(userRole);
        }
        // 执行插入
        if (userRoleService.saveBatch(userRoleList)) {
            // 清除缓存
            AuthUtils.clearUserMenuListCacheById(true, userIds);
            return true;
        } else
            return false;

    }

    /**
     * 接口：取消指定角色已授权的系列用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID列表
     * @return 取消授权结果
     */
    @Override
    public int[] revokeRoleForUsers(Integer roleId, Integer[] userIds) {
        if (null == userIds || userIds.length == 0) {
            return new int[]{0, 0};
        }
        int[] result = new int[2];
        // 遍历用户列表，逐个删除
        for (Integer userId : userIds) {
            QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id", roleId).eq("user_id", userId);
            // TODO 完善：超级管理员不允许取消授权！
            if (userRoleService.remove(wrapper)) {
                result[0]++;
            } else
                result[1]++;
        }
        // 删除缓存
        AuthUtils.clearUserMenuListCacheById(result[0] != 0, userIds);
        return result;
    }


    /**
     * 私有方法：将指定角色拥有的菜单/权限，写入角色-菜单关联表
     *
     * @param roleId   角色ID
     * @param menusIds 菜单ID
     */
    private void insertRoleMenu(Integer roleId, Integer[] menusIds) {
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

//    /**
//     * 私有方法：删除 已缓存的用户列表和权限表 的缓存（详情请见 权限缓存策略）
//     * 前提条件：result > 0 & 开启redis
//     *
//     * @param result 条件：>0 才执行
//     */
//    private void deleteRedisUserMenuCache(int result) {
//        if (exciteConfig.getAllowRedis() && result > 0) {
//            // 获取已缓存的用户列表
//            Set<Integer> userSet = (Set<Integer>) redisService.get("menu:userList");
//            // 统统清空
//            if (userSet != null) {
//                for (Integer userId : userSet) {
//                    redisService.remove("menu:userMenuList:" + userId);
//                }
//                redisService.remove("menu:userList");
//            }
//        }
//    }
//
//    /**
//     * 私有方法：删除指定用户的缓存权限信息
//     *
//     * @param result  条件
//     * @param userIds 用户ID
//     */
//    private void deleteUserMenuCache(int result, Integer[] userIds) {
//        if (result > 0 && exciteConfig.getAllowRedis()) {
//            for (int userId : userIds) {
//                redisService.remove("menu:userMenuList:" + userId);
//            }
//        }
//    }
}
