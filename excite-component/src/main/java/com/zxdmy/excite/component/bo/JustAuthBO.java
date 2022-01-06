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
 * @since 2022/1/6 17:30
 */
@Data
@Accessors(chain = true)
public class JustAuthBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String appID;

    private String appSecret;

    private String redirectUri;

    private Boolean unionId;
}
