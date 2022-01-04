package com.zxdmy.excite.component.vo;

import lombok.Data;

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
public class QiniuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 本条七牛云配置信息的key（主键）
     */
    private String key;

    /**
     * 七牛云账号的秘钥管理之：AccessKey
     */
    private String accessKey;

    /**
     * 七牛云账号的秘钥管理之：SecretKey
     */
    private String secretKey;

    /**
     * 七牛云空间名称
     */
    private String bucket;

    /**
     * 七牛云该空间（bucket）绑定的CDN加速域名
     */
    private String domain;

    /**
     * 地域选择：https://developer.qiniu.com/kodo/1239/java#simple-uptoken
     * 华东[z0]、华北[z1]、华南[z2]、北美[na0]、东南亚（新加坡）[na0]，
     */
    private String region;

    /**
     * 七牛云该空间（bucket）绑定的CDN加速域名的协议 http | https
     */
    private String protocol;

    /*
     * 上传Token
     */
    private String uploadToken;
}
