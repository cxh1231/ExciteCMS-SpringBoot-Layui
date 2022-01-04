package com.zxdmy.excite.common.service.impl;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxdmy.excite.common.config.ExciteConfig;
import com.zxdmy.excite.common.entity.GlobalConfig;
import com.zxdmy.excite.common.mapper.GlobalConfigMapper;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * 组件配置信息表 服务实现类
 * </p>
 *
 * @author 拾年之璐
 * @since 2022-01-03
 */
@Service
@AllArgsConstructor
public class GlobalConfigServiceImpl extends ServiceImpl<GlobalConfigMapper, GlobalConfig> implements IGlobalConfigService {

    ObjectMapper objectMapper;

    ExciteConfig exciteConfig;

    /**
     * 将配置信息保存/更新至数据库
     *
     * @param confService 配置服务名
     * @param confKey     配置信息主键
     * @param object      实体类
     * @param encrypt     是否对value开启加密
     * @return 结果
     */
    @Override
    public boolean save(String confService, String confKey, Object object, boolean encrypt) {
        // 全局配置信息类
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setConfService(confService);
        globalConfig.setConfKey(confKey);
        // 实体类转JSON格式字符串
        String confValue;
        try {
            confValue = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return false;
        }
        // 如果开启加密，则对confValue：使用公钥加密
        if (encrypt) {
            RSA rsa = new RSA(null, exciteConfig.getRsaPublicKey());
            confValue = rsa.encryptBase64(confValue, KeyType.PublicKey);
            globalConfig.setEncrypt(1);
        } else {
            globalConfig.setEncrypt(0);
        }
        globalConfig.setConfValue(confValue);
        globalConfig.setUpdateTime(LocalDateTime.now());
        QueryWrapper<GlobalConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conf_service", confService).eq("conf_key", confKey);
        return this.saveOrUpdate(globalConfig, queryWrapper);
    }

    /**
     * 从数据库中读取一条配置信息
     *
     * @param confService 配置信息服务名
     * @param confKey     配置信息的key
     * @param object      配置信息实体
     * @return 配置信息实体
     */
    @Override
    public Object get(String confService, String confKey, Object object) {
        // 根据要求查询指定【模块】和【key】的【value】
        QueryWrapper<GlobalConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conf_service", confService).eq("conf_key", confKey);
        GlobalConfig globalConfig = this.getOne(queryWrapper);
        String confValue = globalConfig.getConfValue();
        // 为空：返回null
        if (null == confValue || "".equals(confValue)) {
            return null;
        }
        // 如果开启了加密：使用私钥进行解密
        if (1 == globalConfig.getEncrypt()) {
            RSA rsa = new RSA(exciteConfig.getRsaPrivateKey(), null);
            confValue = rsa.decryptStr(confValue, KeyType.PrivateKey);
        }
        // 尝试转换成指定类型
        try {
            object = objectMapper.readValue(confValue, object.getClass());
        } catch (IOException e) {
            // 转换出错：报错并返回空
            System.out.println(e.getMessage());
            return null;
        }
        // 返回转换后的结果
        return object;
    }
}
