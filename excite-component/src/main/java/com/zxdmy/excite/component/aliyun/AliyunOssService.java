package com.zxdmy.excite.component.aliyun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.zxdmy.excite.component.po.AliyunOssPO;
import com.zxdmy.excite.component.po.AliyunSmsPO;
import com.zxdmy.excite.component.vo.AliyunOssPolicyVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 阿里云对象存储服务
 *
 * @author 拾年之璐
 * @since 2022/1/5 20:37
 */
@Service
@AllArgsConstructor
public class AliyunOssService {

    IGlobalConfigService configService;

    private static final String DEFAULT_SERVICE = "aliyunOss";

    private static final String DEFAULT_KEY = "aliyunOss";

    public boolean saveConfig(AliyunOssPO aliyunOssVO) throws JsonProcessingException {
        if (null == aliyunOssVO.getBucketName() || null == aliyunOssVO.getBucketDomain() || null == aliyunOssVO.getEndpoint() || null == aliyunOssVO.getAccessKeyId() || null == aliyunOssVO.getAccessKeySecret()) {
            throw new ServiceException("部分参数为空，请检查！");
        }
        if (null == aliyunOssVO.getKey()) {
            return configService.save(DEFAULT_SERVICE, DEFAULT_KEY, aliyunOssVO, true);
        } else {
            return configService.save(DEFAULT_SERVICE, aliyunOssVO.getKey(), aliyunOssVO, true);
        }
    }

    public AliyunOssPO getConfig(String confKey) {
        AliyunSmsPO aliyunSmsVO = new AliyunSmsPO();
        return (AliyunOssPO) configService.get(DEFAULT_SERVICE, confKey, aliyunSmsVO);
    }

    public AliyunOssPO getConfig() {
        return this.getConfig(DEFAULT_KEY);
    }


    /**
     * 前端直传用的方法
     *
     * @return
     */
    public AliyunOssPolicyVO generatorToken() {
        return null;
    }


}
