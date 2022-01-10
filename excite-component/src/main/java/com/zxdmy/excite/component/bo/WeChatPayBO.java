package com.zxdmy.excite.component.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2022/1/9 20:27
 */
@Data
@Accessors(chain = true)
public class WeChatPayBO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 本条微信支付配置信息的key（主键）
     */
    private String key;

    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 设置APIv2密钥(微信支付商户密钥
     */
    private String mchKey;

    /**
     * APIv3密钥(微信支付商户密钥
     */
    private String apiV3Key;
    /**
     * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
     */
    private String subAppId;

    /**
     * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
     */
    private String subMchId;

    /**
     * V2：apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;

    /**
     * V3：key.pem文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String privateKeyPath;

    /**
     * V3：cert.pem文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String privateCertPath;

    /**
     * 通知地址.通知url必须为外网可访问的url，不能携带参数。公网域名必须为https。
     */
    private String notifyUrl;

}
