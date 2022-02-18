package com.zxdmy.excite.component.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2022/1/3 16:07
 */
@Data
@Accessors(chain = true)
public class QiniuOssBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 本条七牛云配置信息的key（主键）
     */
    private String key;

    /**
     * 七牛云账号的秘钥管理之：AccessKey
     */
    @NotBlank(message = "AccessKey不能为空")
    private String accessKey;

    /**
     * 七牛云账号的秘钥管理之：SecretKey
     */
    @NotBlank(message = "SecretKey不能为空")
    private String secretKey;

    /**
     * 七牛云空间名称
     */
    @NotBlank(message = "空间名称不能为空")
    private String bucket;

    /**
     * 七牛云该空间（bucket）绑定的CDN加速域名
     */
    @NotBlank(message = "CDN加速域名不能为空")
    private String domain;

    /**
     * 地域选择：https://developer.qiniu.com/kodo/1671/region-endpoint-fq
     * 华东:z0; 华北:z1; 华南:z2; 北美:na0; 东南亚:as0; 华东-浙江2:cn-east-2
     * 可以为空，填写其他字符，由系统自己选
     */
//    @NotBlank(message = "地域不能为空")
    private String region;

    /**
     * 七牛云该空间（bucket）绑定的CDN加速域名的协议 http | https
     */
    @NotBlank(message = "CDN加速域名协议不能为空")
    private String protocol;

    /*
     * 上传Token
     */
    private String uploadToken;
}
