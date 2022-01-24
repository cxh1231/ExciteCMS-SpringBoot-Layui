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

import java.util.Arrays;

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
    public Page<SysLogRequest> getPage(Integer current, Integer size, String userId, String startDate, String endDate) {
        // 页码信息
        current = null == current ? 1 : current;
        size = null == size ? 10 : size;
        // 检索信息
        QueryWrapper<SysLogRequest> wrapper = new QueryWrapper<>();
        // 检索请求用户
        wrapper.like(userId != null && !"".equals(userId), "user_id", userId)
                // 检索起始时间
                .ge(startDate != null && !"".equals(startDate), "create_time", startDate)
                // 检索终止时间
                .le(endDate != null && !"".equals(endDate), "create_time", endDate)
                .orderByDesc("id");
        return logRequestMapper.selectPage(new Page<>(current, size), wrapper);
    }

    @Override
    public int deleteLog(Integer[] logIds) {
        return logRequestMapper.deleteBatchIds(Arrays.asList(logIds));
    }
}
