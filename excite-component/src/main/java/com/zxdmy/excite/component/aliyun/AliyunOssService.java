package com.zxdmy.excite.component.aliyun;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.zxdmy.excite.component.po.AliyunOssPO;
import com.zxdmy.excite.component.po.AliyunSmsPO;
import com.zxdmy.excite.component.vo.AliyunOssPolicyVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

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
    public AliyunOssPolicyVO createUploadToken(String confKey, String fileName, Long expireTime) {
        // 读取秘钥等信息
        AliyunOssPO aliyunOssPO = this.getConfig(confKey);
        // 创建OSSClient实例。
        OSS oss = new OSSClientBuilder().build(aliyunOssPO.getEndpoint(), aliyunOssPO.getAccessKeyId(), aliyunOssPO.getAccessKeySecret());

        PolicyConditions policyConditions = new PolicyConditions();
        // 策略：最大上传文件1G
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);

        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expireEndDate = new Date(expireEndTime);
        try {
            // 生成上传策略
            String postPolicy = oss.generatePostPolicy(expireEndDate, policyConditions);
            // 生成签名
            String postSignature = oss.calculatePostSignature(postPolicy);
            // 策略转码
            String base64Policy = Base64.encode(postPolicy);

            // 构造返回数据
            return new AliyunOssPolicyVO()
                    .setAccessKeyId(aliyunOssPO.getAccessKeyId())
                    .setPolicy(base64Policy)
                    .setSignature(postSignature)
                    .setHost(aliyunOssPO.getBucketDomain())
                    .setCallback("")
                    .setKey(fileName);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        } finally {
            oss.shutdown();
        }
    }

    /**
     * 前端直传用的方法
     *
     * @return
     */
    public AliyunOssPolicyVO createUploadToken(String fileName, Long expireTime) {
        return this.createUploadToken(DEFAULT_KEY, fileName, expireTime);
    }


    public boolean uploadFile(String confKey, File file) {
        // 读取配置
        AliyunOssPO aliyunOssPO = this.getConfig(confKey);
        // 创建OSSClient实例。
        OSS oss = new OSSClientBuilder().build(aliyunOssPO.getEndpoint(), aliyunOssPO.getAccessKeyId(), aliyunOssPO.getAccessKeySecret());
        try {
            PutObjectResult putObjectRequest = oss.putObject(aliyunOssPO.getBucketName(), file.getName(), file);
            return putObjectRequest.getResponse().isSuccessful();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            oss.shutdown();
        }
    }

    public boolean uploadFile(File file) {
        return this.uploadFile(DEFAULT_KEY, file);
    }
}
