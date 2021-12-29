package com.zxdmy.excite.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-10-07 0007 19:04
 */
@Component
@ConfigurationProperties(prefix = "excite")
public class ExciteConfig {

    private String name;

    /**
     * 是否允许Redis缓存：true:允许 | false:不允许
     * 此Redis缓存用到的地方主要有：用户角色、权限控制；
     */
    private Boolean allowRedis;

    /**
     * RSA加密算法的公钥
     */
    private String rsaPublicKey;

    /**
     * RSA加密算法的私钥
     */
    private String rsaPrivateKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean getAllowRedis() {
        return allowRedis;
    }

    public void setAllowRedis(Boolean allowRedis) {
        this.allowRedis = allowRedis;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }
}
