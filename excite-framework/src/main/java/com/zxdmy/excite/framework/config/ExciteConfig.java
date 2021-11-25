package com.zxdmy.excite.framework.config;

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
     */
    private Boolean allowRedis;

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
}
