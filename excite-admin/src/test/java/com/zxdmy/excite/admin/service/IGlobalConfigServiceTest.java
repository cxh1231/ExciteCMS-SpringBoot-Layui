package com.zxdmy.excite.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxdmy.excite.component.vo.QiniuVO;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void saveTest() {
        // 初始数据定义
        QiniuVO qiniuEntity = new QiniuVO();
        qiniuEntity.setAccessKey("8bI0GWOa8888888888");
        qiniuEntity.setSecretKey("WzYdBI8FL9999999999999999999");
        qiniuEntity.setDomain("666.hd-bkt.clouddn.com");
        qiniuEntity.setBucket("test-spring");
        qiniuEntity.setProtocol("http");

        try {
            componentConfigService.save("component", "qiniuOss", qiniuEntity, true);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTest() {
        // 初始数据定义
        QiniuVO qiniuEntity = new QiniuVO();

        qiniuEntity = (QiniuVO) componentConfigService.get("component", "qiniuOss", qiniuEntity);

        System.out.println(qiniuEntity);
    }
}
