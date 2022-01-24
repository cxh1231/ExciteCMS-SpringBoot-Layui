package com.zxdmy.excite.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.system.entity.SysLogRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-12-09
 */
public interface ISysLogRequestService extends IService<SysLogRequest> {

    Page<SysLogRequest> getPage(Integer current, Integer size, String userId, String startDate, String endDate);

    int deleteLog(Integer[] logIds);
}
