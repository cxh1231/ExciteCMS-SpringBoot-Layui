package com.zxdmy.excite.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.system.entity.SysLogLogin;
import com.zxdmy.excite.system.entity.SysLogRequest;
import com.zxdmy.excite.system.mapper.SysLogLoginMapper;
import com.zxdmy.excite.system.service.ISysLogLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-12-28
 */
@Service
@AllArgsConstructor
public class SysLogLoginServiceImpl extends ServiceImpl<SysLogLoginMapper, SysLogLogin> implements ISysLogLoginService {


    SysLogLoginMapper loginMapper;

    @Override
    public Page<SysLogLogin> getPage(Integer current, Integer size) {
        current = null == current ? 1 : current;
        size = null == size ? 10 : size;
        QueryWrapper<SysLogLogin> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return loginMapper.selectPage(new Page<>(current, size), wrapper);
    }
}
