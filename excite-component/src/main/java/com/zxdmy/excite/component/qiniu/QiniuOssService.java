package com.zxdmy.excite.component.qiniu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.zxdmy.excite.component.vo.QiniuVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 七牛云对象存储服务实现类
 *
 * @author 拾年之璐
 * @since 2022-01-02 0002 23:33
 */
@Service
@AllArgsConstructor
public class QiniuOssService {

    IGlobalConfigService configService;

    /**
     * 将七牛云的信息保存至数据库
     *
     * @param qiniuVO 七牛云信息实体
     * @return 结果
     */
    public boolean saveQiniuConfig(QiniuVO qiniuVO) throws JsonProcessingException {
        // 如果必填信息为空，则返回错误
        if (null == qiniuVO.getSecretKey() || null == qiniuVO.getAccessKey() || null == qiniuVO.getBucket() || null == qiniuVO.getDomain()) {
            throw new ServiceException("AK、SK、空间名称或者域名为空，请核实！");
        }
        // 核实无误，填充数据
        if (null == qiniuVO.getProtocol()) {
            qiniuVO.setProtocol("http");
        }
        return configService.save("qiniu", "qiniuOss", qiniuVO, false);
    }

    /**
     * 生成上传的Token
     *
     * @param fileKey       文件的key，又称文件名
     * @param expireSeconds 过期时间
     * @return 结果
     */
    public QiniuVO createUploadToken(String fileKey, Long expireSeconds) {
        // 如果必填信息为空，则返回
        if (null == fileKey || "".equals(fileKey)) {
            throw new ServiceException("文件的key为空，请核实！");
        }
        // 读取配置信息
        QiniuVO qiniuVO = new QiniuVO();
        qiniuVO = (QiniuVO) configService.get("qiniu", "qiniuOss", qiniuVO);
        // 生成Token
        Auth auth = Auth.create(qiniuVO.getAccessKey(), qiniuVO.getSecretKey());
        String upToken = auth.uploadToken(qiniuVO.getBucket(), fileKey, expireSeconds, null);
        // 写入Token，并返回
        qiniuVO.setUploadToken(upToken);
        return qiniuVO;
    }

    /**
     * 后端上传文件
     *
     * @param file 文件
     * @return 结果
     * @throws QiniuException 异常
     */
    public QiniuVO uploadFile(File file) throws QiniuException {
        if (null == file) {
            throw new ServiceException("上传的文件为空，请检查！");
        }
        // 生成Token
        QiniuVO qiniuVO = this.createUploadToken(file.getName(), 3600L);
        // 设置区域：如果不为空
        Region region;
        if (null != qiniuVO.getRegion() && !"".equals(qiniuVO.getRegion())) {
            region = new Region.Builder().region(qiniuVO.getRegion()).build();
        } else {
            region = new Region.Builder().autoRegion("https://uc.qbox.me");
        }
        // 配置信息，
        Configuration configuration = new Configuration(region);
        // 构造上传管理类
        UploadManager uploadManager = new UploadManager(configuration);
        // 进行上传
        Response response = uploadManager.put(file, file.getName(), qiniuVO.getUploadToken());
        if (response.isOK()) {
            return qiniuVO;
        }
        return null;
    }
}
