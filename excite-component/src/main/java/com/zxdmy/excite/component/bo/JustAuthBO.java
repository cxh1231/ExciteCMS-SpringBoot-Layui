package com.zxdmy.excite.component.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 第三方登录的配置信息实体
 *
 * @author 拾年之璐
 * @since 2022/1/6 17:30
 */
@Data
@Accessors(chain = true)
public class JustAuthBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 本条登录类型的KEY，如：qq、weibo等
     */
    private String key;

    /**
     * 客户端id：对应各平台的appKey
     */
    private String appID;

    /**
     * 客户端Secret：对应各平台的appSecret
     */
    private String appSecret;

    /**
     * 登录成功后的回调地址
     */
    private String redirectUri;

    /**
     * 只针对qq登录生效：是否需要申请unionid
     */
    private Boolean unionId;

    /**
     * 企业微信第三方授权用户类型：member|admin
     */
    private String usertype;

    /**
     * 企业微信登录：授权方的网页应用ID
     */
    private String agentId;

    /**
     * 支付宝的公钥，只针对支付宝、阿里云登陆有效
     */
    private String publicKey;
}
