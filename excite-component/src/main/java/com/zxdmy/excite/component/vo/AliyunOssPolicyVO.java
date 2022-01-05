package com.zxdmy.excite.component.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 此[表现层对象]用来返回前端上传时，需要用到的各种参数。
 * 应用场景：后端生成Policy，前端直传阿里云OSS。上传最大值：5G。
 * 参考资料：
 * https://help.aliyun.com/document_detail/31927.htm
 * https://help.aliyun.com/document_detail/31926.htm
 *
 * @author 拾年之璐
 * @since 2022/1/5 21:06
 */
@Data
@Accessors(chain = true)
public class AliyunOssPolicyVO implements Serializable {

    /***
     * Bucket域名，即Endpoint访问域名。格式为：https://{bucketName}.{endpoint}，如：https://bucket-name.oss-cn-hangzhou.aliyuncs.com
     */
    private String host;

    /**
     * 文件完整名（如果有前缀则需要携带），如：hello.zip，user/10001/hello.rar
     */
    private String key;

    /**
     * 用户表单上传的策略（Policy），Policy为经过Base64编码过的字符串。详情请参见 https://help.aliyun.com/document_detail/31988.htm?spm=a2c4g.11186623.0.0.69de5458ikCoa2#section-d5z-1ww-wdb
     */
    private String policy;

    /**
     * 用户请求的AccessKey ID。
     */
    private String OSSAccessKeyId;

    /**
     * 设置阿里云OSS服务端返回状态码为200，不设置则默认返回状态码204。
     */
    private final String success_action_status = "200";

    /**
     * 上传后的阿里云OSS服务器回调功能。即上传完成后，阿里云向此地址发送一个回调。如果不涉及上传回调，请不要填充值。
     */
    private String callback;

    /**
     * 对Policy签名后的字符串。详情请参见：https://help.aliyun.com/document_detail/31988.htm?spm=a2c4g.11186623.0.0.69de5458ikCoa2#section-wny-mww-wdb
     */
    private String signature;
}
