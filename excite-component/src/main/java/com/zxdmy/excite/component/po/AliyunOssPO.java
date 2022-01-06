package com.zxdmy.excite.component.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 阿里云OSS配置信息
 *
 * @author 拾年之璐
 * @since 2022/1/5 20:18
 */
@Data
@Accessors(chain = true)
public class AliyunOssPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 本条OSS配置信息的key
     */
    private String key;

    /**
     * OSS上的命名空间
     */
    private String bucketName;

    /**
     * Bucket的域名，由阿里云官方生成，该域名用来访问空间中的文件。
     */
    private String bucketDomain;

    /**
     * 自己通过CNAME域名解析绑定的域名
     */
    private String bindDomain;

    /**
     * 访问域名（地域节点），上传使用。
     * 详情请参考：https://help.aliyun.com/document_detail/31837.html
     */
    private String endpoint;

    /**
     * 阿里云账号（建议使用子账号）的AccessKey
     */
    private String accessKeyId;

    /**
     * 阿里云账号（建议使用子账号）的AccessKey的秘钥
     */
    private String accessKeySecret;


}
