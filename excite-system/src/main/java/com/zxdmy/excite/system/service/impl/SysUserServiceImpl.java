package com.zxdmy.excite.system.service.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxdmy.excite.common.config.ExciteConfig;
import com.zxdmy.excite.common.enums.SystemCode;
import com.zxdmy.excite.common.service.RedisService;
import com.zxdmy.excite.system.entity.SysUser;
import com.zxdmy.excite.system.entity.SysUserRole;
import com.zxdmy.excite.system.mapper.SysUserMapper;
import com.zxdmy.excite.system.service.ISysUserRoleService;
import com.zxdmy.excite.system.service.ISysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
     * 私有方法：更新用户-角色关联信息
     *
     * @param userId  用户ID
     * @param roleIds 角色ID数组
     */
    private void insertUserRole(Integer userId, Integer[] roleIds) {
        List<SysUserRole> userRoleList = new ArrayList<>();
        for (Integer roleId : roleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRoleList.add(userRole);
        }
        if (userRoleList.size() > 0) {
            userRoleService.saveBatch(userRoleList);
        }
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
