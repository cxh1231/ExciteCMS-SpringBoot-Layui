package com.zxdmy.excite.system.service.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxdmy.excite.common.config.ExciteConfig;
import com.zxdmy.excite.common.enums.SystemCode;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.RedisService;
import com.zxdmy.excite.system.entity.SysRole;
import com.zxdmy.excite.system.entity.SysUser;
import com.zxdmy.excite.system.entity.SysUserRole;
import com.zxdmy.excite.system.mapper.SysUserMapper;
import com.zxdmy.excite.system.service.ISysRoleService;
import com.zxdmy.excite.system.service.ISysUserRoleService;
import com.zxdmy.excite.system.service.ISysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-01
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    SysUserMapper userMapper;

    ISysUserRoleService userRoleService;

    ISysRoleService roleService;

    RedisService redisService;

    ExciteConfig exciteConfig;

    /**
     * 登录服务
     *
     * @param username 用户名，邮箱或者手机号
     * @param password 密码
     * @return SysUser：登录成功 | null：登录失败
     */
    @Override
    public SysUser login(String username, String password) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        // 这里用了 Hutool 的验证工具Validator
        if (Validator.isEmail(username)) {
            wrapper.eq("email", username).eq("password", password);
        } else {
            wrapper.eq("phone", username).eq("password", password);
        }
        return userMapper.selectOne(wrapper);
    }

    /**
     * 获取用户列表
     *
     * @param current  当前页
     * @param size     当前页大小
     * @param username 检索用户名
     * @param account  检索邮箱/手机号
     * @return 用户页面信息
     */
    @Override
    public Page<SysUser> getPage(Integer current, Integer size, String username, String account) {
        size = null == size ? 1 : size;
        current = null == current ? 10 : current;
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like(null != username && !"".equals(username), "username", username)
                .and(null != account && !"".equals(account), new Consumer<QueryWrapper<SysUser>>() {
                    @Override
                    public void accept(QueryWrapper<SysUser> sysUserQueryWrapper) {
                        sysUserQueryWrapper
                                .like("email", account)
                                .or()
                                .like("phone", account);
                    }
                });
        return userMapper.selectPage(new Page<>(current, size), wrapper);
    }

    /**
     * 保存用户：添加 | 更新
     *
     * @param user    用户信息
     * @param roleIds 角色ID列表
     * @return 修改的行数 >0：成功 | <=0：失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int save(SysUser user, Integer[] roleIds) {
        int result = 0;
        // 新建用户：其ID为空
        if (null == user.getId()) {
            // 初始化部分信息
            if (null == user.getStatus())
                user.setStatus(SystemCode.STATUS_Y.getCode());
            // 未删除状态
            user.setIsDelete(SystemCode.DELETE_N.getCode());
            // 创建时间
            user.setCreateTime(LocalDateTime.now());
            // 执行新建
            result = userMapper.insert(user);
            // 如果当前新用户分配的角色ID不为空
            if (null != roleIds)
                // 插入角色
                this.insertUserRole(user.getId(), roleIds);
        }
        // 更新信息用户：其ID不为空
        else {
            user.setUpdateTime(LocalDateTime.now());
            // 如果当前新用户分配的角色ID不为空
            if (null != roleIds) {
                // 先把该用户已有的角色关联信息删除
                QueryWrapper<SysUserRole> userRoleQueryWrapper = new QueryWrapper<>();
                userRoleQueryWrapper.eq("user_id", user.getId());
                userRoleService.remove(userRoleQueryWrapper);
                // 插入新的角色
                this.insertUserRole(user.getId(), roleIds);
            }
            // 更新用户信息
            result = userMapper.updateById(user);
            // 清除缓存
            this.deleteUserMenuCache(result, user.getId());
        }
        return result;
    }

    /**
     * 接口：修改用户状态
     *
     * @param newStatus 新状态
     * @param userIds   用户ID列表
     * @return 修改结果数组 0:成功个数 1:失败个数
     */
    @Override
    public int[] changeStatus(Integer newStatus, Integer[] userIds) {
        /// 信息不能为空
        if (null == newStatus || null == userIds) {
            throw new ServiceException("新状态或用户ID不能为空");
        }
        // 新状态只能为 0 | 1
        if (newStatus == 0 || newStatus == 1) {
            int[] result = new int[2];
            // 遍历用户列表
            for (int userId : userIds) {
                // 定义用户信息
                SysUser user = new SysUser();
                user.setId(userId);
                user.setStatus(newStatus);
                // 执行修改（其中锁定，即状态为2的用户禁止修改）
                QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
                wrapper.eq("id", userId)
                        .ne("status", SystemCode.STATUS_Y_BLOCK.getCode());
                // 修改成功
                if (userMapper.update(user, wrapper) > 0) {
                    result[0]++;
                    // 清除该用户的权限缓存
                    this.deleteUserMenuCache(1, userId);
                } else result[1]++;
            }
            // 返回结果
            return result;
        } else {
            throw new ServiceException("新状态只能为0或1！");
        }
    }

    /**
     * 接口：给用户分配角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID数组
     * @return 修改结果数组 0:分配个数 1:失败个数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean setRoleForUser(Integer userId, Integer[] roleIds) {
        // 从 用户-角色关联表 中，删除包含该用户的关联信息
        QueryWrapper<SysUserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", userId);
        userRoleService.remove(userRoleQueryWrapper);
        // 记录结果
        boolean result;
        // 如果角色列表为null，或长度为 0，则表示清除该用户的全部角色，也就没必要插入了
        if (null == roleIds || roleIds.length == 0) {
            this.deleteUserMenuCache(1, userId);
            result = true;
        }
        // 不为空，执行授权
        else {
            // 执行插入，并返回结果
            result = this.insertUserRole(userId, roleIds);
        }
        // 如果角色分配成功，则清缓存
        if (result)
            this.deleteUserMenuCache(1, userId);
        return result;
    }

    /**
     * 接口：根据ID删除用户
     *
     * @param userId 用户ID
     * @return 删除结果：>0 表示成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteUserById(Integer userId) {
        if (null == userId) {
            throw new SecurityException("用户ID不能为空！");
        }
        // 从 用户-角色关联表 中，删除包含该用户的关联信息
        QueryWrapper<SysUserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", userId);
        userRoleService.remove(userRoleQueryWrapper);
        // 删除用户
        int result = userMapper.deleteById(userId);
        // 清除缓存
        this.deleteUserMenuCache(result, userId);
        return result;
    }

    /**
     * 接口：通过角色ID查询用户列表
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    @Override
    public List<SysUser> getUserListByRoleId(Integer roleId) {
        return userMapper.selectUsersByRoleId(roleId);
    }

    /**
     * 接口：通过角色ID查询不包含此角色的用户列表
     *
     * @param roleId 角色ID
     * @return 不包含此角色的用户列表
     */
    @Override
    public List<SysUser> getUserListByRoleIdNotIn(Integer roleId) {
        return userMapper.selectUsersByRoleIdNotIn(roleId);
    }

    /**
     * 私有方法：更新用户-角色关联信息
     *
     * @param userId  用户ID
     * @param roleIds 角色ID数组
     */
    private boolean insertUserRole(Integer userId, Integer[] roleIds) {
        // 如果角色列表为空，直接返回真
        if (null == roleIds || roleIds.length == 0) {
            return true;
        }
        // 获取所有状态正常的角色集合
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.ne("status", SystemCode.STATUS_N);
        Set<Integer> roleSet = roleService.list(wrapper).stream().map(SysRole::getId).collect(Collectors.toSet());
        // 存储列表
        List<SysUserRole> userRoleList = new ArrayList<>();
        for (Integer roleId : roleIds) {
            // 只有符合条件的角色，才能添加
            if (roleSet.contains(roleId)) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRoleList.add(userRole);
            }
        }
        if (userRoleList.size() > 0) {
            return userRoleService.saveBatch(userRoleList);
        } else return false;
    }

    /**
     * 删除指定用户的缓存权限信息
     *
     * @param result 条件
     * @param userId 用户ID
     */
    private void deleteUserMenuCache(int result, int userId) {
        if (result > 0 && exciteConfig.getAllowRedis()) {
            redisService.remove("menu:userMenuList:" + userId);
        }
    }
}
