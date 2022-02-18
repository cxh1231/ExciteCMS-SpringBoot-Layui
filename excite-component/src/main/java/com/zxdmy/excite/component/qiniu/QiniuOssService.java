package com.zxdmy.excite.component.qiniu;

import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.*;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.zxdmy.excite.component.vo.QiniuFileVO;
import com.zxdmy.excite.component.bo.QiniuOssBO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 七牛云对象存储服务实现类
 * 更多接口，可以根据 https://developer.qiniu.com/kodo/1239/java 实现。
 *
 * @author 拾年之璐
 * @since 2022-01-02 0002 23:33
 */
@Service
@AllArgsConstructor
public class QiniuOssService {

    IGlobalConfigService configService;

    private static final String DEFAULT_SERVICE = "qiniu";
    private static final String DEFAULT_KEY = "qiniuOss";

    /**
     * 将七牛云的信息保存至数据库
     *
     * @param qiniuOssBO 七牛云信息实体。如果指定key，则存储的配置信息的key即为所指定
     * @return 结果
     */
    public boolean saveQiniuConfig(QiniuOssBO qiniuOssBO) throws JsonProcessingException {
        // 如果必填信息为空，则返回错误
        if (null == qiniuOssBO.getSecretKey() || null == qiniuOssBO.getAccessKey() || null == qiniuOssBO.getBucket() || null == qiniuOssBO.getDomain()) {
            throw new ServiceException("AK、SK、空间名称或者域名为空，请核实！");
        }
        // 核实无误，填充数据
        if (null == qiniuOssBO.getProtocol()) {
            qiniuOssBO.setProtocol("http");
        }
        if (null == qiniuOssBO.getKey()) {
            return configService.save(DEFAULT_SERVICE, DEFAULT_KEY, qiniuOssBO, false);
        }
        return configService.save(DEFAULT_SERVICE, qiniuOssBO.getKey(), qiniuOssBO, false);
    }

    /**
     * 从数据库读取七牛云配置信息（指定key）
     *
     * @param confKey 配置文件的key，不同的key对应不同的存储空间
     * @return 七牛云配置信息实体
     */
    public QiniuOssBO getQiniuConfig(String confKey) {
        QiniuOssBO qiniuOssBO = new QiniuOssBO();
        qiniuOssBO = (QiniuOssBO) configService.get(DEFAULT_SERVICE, confKey, qiniuOssBO);
        return qiniuOssBO;
    }

    /**
     * 从数据库读取七牛云配置信息（默认key）
     *
     * @return 七牛云配置信息实体
     */
    public QiniuOssBO getQiniuConfig() {
        return this.getQiniuConfig(DEFAULT_KEY);
    }

    /**
     * 从数据库读取七牛云配置信息，并脱敏（指定key）
     *
     * @param confKey 配置文件的key，不同的key对应不同的存储空间
     * @return 七牛云配置信息实体
     */
    public QiniuOssBO getQiniuConfigHide(String confKey) {
        QiniuOssBO qiniuOssBO = new QiniuOssBO();
        qiniuOssBO = (QiniuOssBO) configService.get(DEFAULT_SERVICE, confKey, qiniuOssBO);
        qiniuOssBO.setAccessKey(DesensitizedUtil.idCardNum(qiniuOssBO.getAccessKey(), 2, 2));
        qiniuOssBO.setSecretKey(DesensitizedUtil.idCardNum(qiniuOssBO.getSecretKey(), 2, 2));
        return qiniuOssBO;
    }

    /**
     * 从数据库读取七牛云配置信息，并脱敏（默认key）
     *
     * @return 七牛云配置信息实体
     */
    public QiniuOssBO getQiniuConfigHide() {
        return this.getQiniuConfigHide(DEFAULT_KEY);
    }

    /**
     * 获取全部的七牛云配置信息
     *
     * @param desensitized 是否脱敏：true：脱敏， false：不脱敏
     * @return 读取全部配置信息
     */
    public List<QiniuOssBO> getQiniuConfigList(Boolean desensitized) {
        QiniuOssBO qiniuOssBO = new QiniuOssBO();
        List<Object> objectList = configService.getList(DEFAULT_SERVICE, qiniuOssBO);
        ObjectMapper objectMapper = new ObjectMapper();
        List<QiniuOssBO> qiniuOssBOList = new ArrayList<>();
        for (Object o : objectList) {
            qiniuOssBO = objectMapper.convertValue(o, qiniuOssBO.getClass());
            if (desensitized) {
                qiniuOssBO.setAccessKey(DesensitizedUtil.idCardNum(qiniuOssBO.getAccessKey(), 2, 2));
                qiniuOssBO.setSecretKey(DesensitizedUtil.idCardNum(qiniuOssBO.getSecretKey(), 2, 2));
            }
            qiniuOssBOList.add(qiniuOssBO);
        }
        return qiniuOssBOList;
    }

    /**
     * 生成上传的Token（指定key）
     *
     * @param confKey       配置文件的key，不同的key对应不同的存储空间
     * @param fileKey       文件的key，又称文件名
     * @param expireSeconds 过期时间
     * @return 结果
     */
    public QiniuOssBO createUploadToken(String confKey, String fileKey, Long expireSeconds) {
        // 如果必填信息为空，则返回
        if (null == fileKey || "".equals(fileKey)) {
            throw new ServiceException("文件的key为空，请核实！");
        }
        // 读取配置信息
        QiniuOssBO qiniuOssBO = new QiniuOssBO();
        qiniuOssBO = (QiniuOssBO) configService.get(DEFAULT_SERVICE, confKey, qiniuOssBO);
        // 生成Token
        Auth auth = Auth.create(qiniuOssBO.getAccessKey(), qiniuOssBO.getSecretKey());
        String upToken = auth.uploadToken(qiniuOssBO.getBucket(), fileKey, expireSeconds, null);
        // 写入Token，并返回
        qiniuOssBO.setUploadToken(upToken);
        return qiniuOssBO;
    }

    /**
     * 生成上传的Token（默认key）
     *
     * @param fileKey       文件的key，又称文件名
     * @param expireSeconds 过期时间
     * @return 结果
     */
    public QiniuOssBO createUploadToken(String fileKey, Long expireSeconds) {
        return this.createUploadToken(DEFAULT_KEY, fileKey, expireSeconds);
    }

    /**
     * 后端上传文件（指定key）
     *
     * @param confKey 配置文件的key，不同的key对应不同的存储空间
     * @param file    文件
     * @return 结果
     */
    public QiniuFileVO uploadFile(String confKey, File file) {
        if (null == file) {
            throw new ServiceException("上传的文件为空，请检查！");
        }
        // 生成Token
        QiniuOssBO qiniuOssBO = this.createUploadToken(confKey, file.getName(), 3600L);
        // 设置区域：如果不为空
        Region region;
        // 华东[z0]、华北[z1]、华南[z2]、北美[na0]、东南亚（新加坡）[na1]，
        switch (qiniuOssBO.getRegion()) {
            case "z0":
                region = Region.huadong();
                break;
            case "z1":
                region = Region.huabei();
                break;
            case "z2":
                region = Region.huanan();
                break;
            case "na0":
                region = Region.beimei();
                break;
            case "na1":
                region = Region.xinjiapo();
                break;
            default:
                region = new Region.Builder().autoRegion("https://uc.qbox.me");
        }
        // 配置信息，
        Configuration configuration = new Configuration(region);
        // 构造上传管理类
        UploadManager uploadManager = new UploadManager(configuration);
        // 进行上传
        try {
            Response response = uploadManager.put(file, file.getName(), qiniuOssBO.getUploadToken());
            if (response.isOK()) {
                return new QiniuFileVO()
                        .setProtocol(qiniuOssBO.getProtocol())
                        .setDomain(qiniuOssBO.getDomain())
                        .setKey(qiniuOssBO.getKey());
            }
            return null;
        } catch (QiniuException ex) {
            throw new ServiceException(ex.response.toString());
        }
    }

    /**
     * 后端上传文件（默认key）
     *
     * @param file 文件
     * @return 七牛云配置信息实体
     */
    public QiniuFileVO uploadFile(File file) {
        return this.uploadFile(DEFAULT_KEY, file);
    }

    /**
     * 抓取网络资源到空间（指定key）
     *
     * @param confKey      配置文件的key，不同的key对应不同的存储空间
     * @param fileKey      文件名
     * @param remoteSrcUrl 远程链接
     * @return 文件信息
     */
    public QiniuFileVO uploadFileFromUrl(String confKey, String fileKey, String remoteSrcUrl) {
        QiniuOssBO qiniuOssBO = this.getQiniuConfig(confKey);
        if (qiniuOssBO == null) {
            throw new ServiceException("key为[" + confKey + "]的配置信息为空，请检查！");
        }
        BucketManager bucketManager = this.createBucketManager(qiniuOssBO);
        //抓取网络资源到空间
        try {
            FetchRet fetchRet = bucketManager.fetch(remoteSrcUrl, qiniuOssBO.getBucket(), fileKey);
            return new QiniuFileVO()
                    .setProtocol(qiniuOssBO.getProtocol())
                    .setDomain(qiniuOssBO.getDomain())
                    .setKey(fetchRet.key)
                    .setHash(fetchRet.hash)
                    .setMimeType(fetchRet.mimeType)
                    .setSize(fetchRet.fsize);
        } catch (QiniuException ex) {
            throw new ServiceException(ex.response.toString());
        }
    }


    /**
     * 获取七牛云空间的全部文件（指定key）
     *
     * @param confKey   配置文件的key，不同的key对应不同的存储空间
     * @param prefix    文件前缀
     * @param delimiter 文件分隔符
     * @return 文件信息列表
     */
    public List<FileInfo> getQiniuFileListAll(String confKey, String prefix, String delimiter) {
        QiniuOssBO qiniuOssBO = this.getQiniuConfig(confKey);
        if (qiniuOssBO == null) {
            throw new ServiceException("key为[" + confKey + "]的配置信息为空，请检查！");
        }
        BucketManager bucketManager = this.createBucketManager(qiniuOssBO);
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(qiniuOssBO.getBucket(), prefix, 1000, delimiter);
        // 记录返回值
        List<FileInfo> fileInfoList = new ArrayList<>();
        while (fileListIterator.hasNext()) {
            fileInfoList.addAll(Arrays.asList(fileListIterator.next()));
        }
        return fileInfoList;
    }

    /**
     * 获取七牛云空间的全部文件（默认key）
     *
     * @param prefix    文件前缀
     * @param delimiter 文件分隔符
     * @return 文件信息列表
     */
    public List<FileInfo> getQiniuFileListAll(String prefix, String delimiter) {
        return this.getQiniuFileListAll(DEFAULT_KEY, prefix, delimiter);
    }

    /**
     * 获取七牛云空间的分页文件列表（分页），如果页数超限，则返回第一页（指定key）
     *
     * @param confKey   配置文件的key，不同的key对应不同的存储空间
     * @param page      页号
     * @param limit     每页条数
     * @param prefix    前缀
     * @param delimiter 分隔符
     * @return 页面类Page
     */
    public Page<FileInfo> getQiniuFileListPage(String confKey, Integer page, Integer limit, String prefix, String delimiter) {
        // 定义返回Page实体
        Page<FileInfo> fileInfoPage = new Page<>();
        // 获取配置
        QiniuOssBO qiniuOssBO = this.getQiniuConfig(confKey);
        if (qiniuOssBO == null) {
            throw new ServiceException("key为[" + confKey + "]的配置信息为空，请检查！");
        }
        // 生成管理器
        BucketManager bucketManager = this.createBucketManager(qiniuOssBO);
        // 获取列表迭代器
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(qiniuOssBO.getBucket(), prefix, limit, delimiter);
        // 遍历直至找到指定页数
        long total = 0;
        while (fileListIterator.hasNext()) {
            page--;
            if (page == 0) {
                FileInfo[] items = fileListIterator.next();
                fileInfoPage.setRecords(Arrays.asList(items));
                total += items.length;
            } else {
                total += fileListIterator.next().length;
            }
        }
        if (fileInfoPage.getRecords().size() == 0) {
            fileInfoPage.setRecords(Arrays.asList(fileListIterator.next()));
        }
        fileInfoPage.setCurrent(page).setSize(limit).setTotal(total);
        return fileInfoPage;
    }

    /**
     * 获取七牛云空间的分页文件列表（分页），如果页数超限，则返回第一页（默认key）
     *
     * @param page      页号
     * @param limit     每页条数
     * @param prefix    前缀
     * @param delimiter 分隔符
     * @return 页面类Page
     */
    public Page<FileInfo> getQiniuFileListPage(Integer page, Integer limit, String prefix, String delimiter) {
        return this.getQiniuFileListPage(DEFAULT_KEY, page, limit, prefix, delimiter);
    }

    /**
     * 获取下载链接（一般针对私有资源使用）（指定key）
     *
     * @param confKey       配置文件的key，不同的key对应不同的存储空间
     * @param fileKey       下载资源在七牛云存储的key
     * @param attName       下载文件的名字。null：与存储的文件名相同
     * @param expireSeconds 有效期（秒），一般取3600（即1小时）
     * @return 下载链接
     */
    public String getDownloadUrl(String confKey, String fileKey, String attName, Long expireSeconds) {
        // 获取配置
        QiniuOssBO qiniuOssBO = this.getQiniuConfig(confKey);
        if (qiniuOssBO == null) {
            throw new ServiceException("key为[" + confKey + "]的配置信息为空，请检查！");
        }
        DownloadUrl url = new DownloadUrl(qiniuOssBO.getDomain(), "https".equals(qiniuOssBO.getProtocol()), fileKey);
        if (null != attName) {
            url.setAttname(attName);
        }
        Auth auth = Auth.create(qiniuOssBO.getAccessKey(), qiniuOssBO.getSecretKey());
        long deadline = expireSeconds + new Date().getTime();
        String downloadUrl = "";
        try {
            downloadUrl = url.buildURL(auth, deadline);
        } catch (QiniuException e) {
            throw new ServiceException(e.response.toString());
        }
        return downloadUrl;
    }

    /**
     * 获取下载链接（一般针对私有资源使用）（默认key）
     *
     * @param fileKey       下载资源在七牛云存储的key
     * @param attName       下载文件的名字。null：与存储的文件名相同
     * @param expireSeconds 有效期（秒），一般取3600（即1小时）
     * @return 下载链接
     */
    public String getDownloadUrl(String fileKey, String attName, Long expireSeconds) {
        return this.getDownloadUrl(DEFAULT_KEY, fileKey, attName, expireSeconds);
    }

    /**
     * 根据配置信息生成空间管理器
     *
     * @param qiniuOssBO 配置信息
     * @return 空间管理器
     */
    private BucketManager createBucketManager(QiniuOssBO qiniuOssBO) {
        // 设置区域：如果不为空
        Region region;
        if (null != qiniuOssBO.getRegion() && !"".equals(qiniuOssBO.getRegion())) {
            region = new Region.Builder().region(qiniuOssBO.getRegion()).build();
        } else {
            region = new Region.Builder().autoRegion("https://uc.qbox.me");
        }
        // 生成Auth授权类
        Auth auth = Auth.create(qiniuOssBO.getAccessKey(), qiniuOssBO.getSecretKey());
        // 配置信息，
        Configuration configuration = new Configuration(region);
        // 返回
        return new BucketManager(auth, configuration);
    }
}
