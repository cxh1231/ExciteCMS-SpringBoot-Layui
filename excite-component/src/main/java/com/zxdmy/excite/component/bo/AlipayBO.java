package com.zxdmy.excite.component.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 支付宝支付配置信息
 *
 * @author 拾年之璐
 * @since 2022/1/10 22:13
 */
@Data
@Accessors(chain = true)
public class AlipayBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 本条支付宝支付信息的KEY
     */
    private String key;

    /**
     * 必填：应用AppId，例如：2019091767145019
     */
    private String appId;

    /**
     * 必填：应用名称
     */
    private String appName;

    /**
     * 必填：应用私钥（Java语言），例如：MIIEvQIBADANB ... ...
     */
    private String merchantPrivateKey;

    // 接口加签方式分：证书模式（收、退款）和公钥模式（只能收款）
    // 证书模式文档：https://opendocs.alipay.com/common/02kipl
    // 优先级：证书模式 ＞ 非证书模式

    /**
     * 证书模式：【应用公钥证书】文件路径，路径优先级：文件系统 ＞ CLASS_PATH
     */
    private String merchantCertPath;

    /**
     * 证书模式：【支付宝公钥证书】文件路径，路径优先级：文件系统 ＞ CLASS_PATH
     */
    private String alipayCertPath;

    /**
     * 证书模式：【支付宝根证书】文件路径，路径优先级：文件系统 ＞ CLASS_PATH
     */
    private String alipayRootCertPath;

    /**
     * 公钥模式：只需要填写此【支付宝公钥】即可，无需赋值上面的三个证书路径
     */
    private String alipayPublicKey;

    /**
     * 可选：异步通知接收服务地址，例如：https://www.test.com/callback
     */
    private String notifyUrl;

    /**
     * 可选：AES密钥（接口内容加密方式），调用AES加解密相关接口时需要
     */
    private String encryptKey;

    /**
     * 必填：协议类型：http | https 二选一
     */
    private String protocol = "https";

    /**
     * 必填：gatewayHost，默认：openapi.alipay.com
     */
    private String gatewayHost = "openapi.alipay.com";

    /**
     * 必填：签名类型，默认RSA2
     */
    private String signType = "RSA2";
}
