package com.zxdmy.excite.component.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2022/1/4 11:51
 */
@Data
@Accessors(chain = true)
public class QiniuFileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    public String key;

    /**
     * 文件hash值
     */
    public String hash;

    /**
     * 文件的md5值
     */
    public String md5;

    /**
     * 文件大小，单位：字节
     */
    public long size;

    /**
     * 文件上传时间，单位为：100纳秒
     */
    public long putTime;

    /**
     * 文件的mimeType
     */
    public String mimeType;

    /**
     * 文件的状态，0表示启用，1表示禁用
     */
    public int status;

    /**
     * 七牛云该空间（bucket）绑定的CDN加速域名
     */
    private String domain;

    /**
     * 七牛云该空间（bucket）绑定的CDN加速域名的协议 http | https
     */
    private String protocol;

    /*
     * 上传Token
     */
    private String uploadToken;
}
