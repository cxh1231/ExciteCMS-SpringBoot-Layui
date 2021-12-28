package com.zxdmy.excite.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.system.entity.SysLogLogin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-12-28
 */
public interface ISysLogLoginService extends IService<SysLogLogin> {
    Page<SysLogLogin> getPage(Integer current, Integer size);
}
