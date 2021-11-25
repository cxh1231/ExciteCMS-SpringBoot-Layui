package com.zxdmy.excite.system.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxdmy.excite.system.entity.SysUser;
import com.zxdmy.excite.system.mapper.SysUserMapper;
import com.zxdmy.excite.system.service.ISysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
            // 这里用了 Hutool 的加密工具SecureUtil
            wrapper.eq("email", username).eq("password", SecureUtil.md5(password));
        } else {
            wrapper.eq("phone", username).eq("password", SecureUtil.md5(password));
        }
        return userMapper.selectOne(wrapper);
    }
}
