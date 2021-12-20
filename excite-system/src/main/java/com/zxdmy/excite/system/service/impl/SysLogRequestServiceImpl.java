package com.zxdmy.excite.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.system.entity.SysLogRequest;
import com.zxdmy.excite.system.entity.SysMenu;
import com.zxdmy.excite.system.mapper.SysLogRequestMapper;
import com.zxdmy.excite.system.service.ISysLogRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-12-09
 */
@Service
@AllArgsConstructor
public class SysLogRequestServiceImpl extends ServiceImpl<SysLogRequestMapper, SysLogRequest> implements ISysLogRequestService {

    SysLogRequestMapper logRequestMapper;

    @Override
    public Page<SysLogRequest> getPage(Integer current, Integer size) {
        current = null == current ? 1 : current;
        size = null == size ? 10 : size;
        QueryWrapper<SysLogRequest> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return logRequestMapper.selectPage(new Page<>(current, size), wrapper);
    }
}
