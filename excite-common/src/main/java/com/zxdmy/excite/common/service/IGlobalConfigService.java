package com.zxdmy.excite.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxdmy.excite.common.entity.GlobalConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组件配置信息表 服务类
 * </p>
 *
 * @author 拾年之璐
 * @since 2022-01-03
 */
public interface IGlobalConfigService extends IService<GlobalConfig> {

    boolean save(String confService, String confKey, Object object, boolean encrypt) throws JsonProcessingException;

    Object get(String confService, String confKey, Object object);

    List<Object> getList(String confService, Object object);
}
