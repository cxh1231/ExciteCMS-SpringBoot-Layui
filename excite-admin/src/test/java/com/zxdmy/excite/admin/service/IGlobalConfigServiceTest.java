package com.zxdmy.excite.admin.service;

import com.qiniu.storage.model.FileInfo;
import com.zxdmy.excite.component.qiniu.QiniuOssService;
import com.zxdmy.excite.component.po.QiniuOssPO;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2022/1/3 16:40
 */
@SpringBootTest
public class IGlobalConfigServiceTest {

    @Autowired
    private IGlobalConfigService componentConfigService;

    @Autowired
    private QiniuOssService qiniuOssService;

//    @Test
//    void saveTest() {
//        // 初始数据定义
//        QiniuVO qiniuEntity = new QiniuVO();
//        qiniuEntity.setAccessKey("8bI0GWOa8888888888");
//        qiniuEntity.setSecretKey("WzYdBI8FL9999999999999999999");
//        qiniuEntity.setDomain("666.hd-bkt.clouddn.com");
//        qiniuEntity.setBucket("test-spring");
//        qiniuEntity.setRegion("z0");
//        qiniuEntity.setProtocol("http");
//
//        try {
//            componentConfigService.save("qiniu", "qiniuOss", qiniuEntity, true);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    void getTest() {
        // 初始数据定义s
        QiniuOssPO qiniuEntity = new QiniuOssPO();

        qiniuEntity = (QiniuOssPO) componentConfigService.get("qiniu", "qiniuOss", qiniuEntity);

        System.out.println(qiniuEntity);
    }

    @Test
    void getList() {
        List<FileInfo> fileInfoList = qiniuOssService.getQiniuFileListAll("", "");
        for (FileInfo fileInfo : fileInfoList) {
            System.out.println(fileInfo);
        }
    }

    @Test
    void testtt() {
        String url = qiniuOssService.getDownloadUrl("audio/song.mp3", "这是一首歌曲.mp3", 3600L);
        System.out.println(url);
    }
}
