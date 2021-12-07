package com.zxdmy.excite.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * <p>
 * 第三方验证码实体类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-10-04 0004 20:02
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaptchaDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 验证码的Token
     */
    private String token;

    /**
     * 验证码的字符。返回的JSON，禁止返回给前端。
     */
    @JsonIgnore
    private String text;

    /**
     * 验证码的验证字符。比如算式的结果等。
     */
    @JsonIgnore
    private String code;

    /**
     * 验证码缓冲图像
     */
    @JsonIgnore
    private BufferedImage image;

    /**
     * 验证码图片的Base64字符串
     */
    private String base64;
}
