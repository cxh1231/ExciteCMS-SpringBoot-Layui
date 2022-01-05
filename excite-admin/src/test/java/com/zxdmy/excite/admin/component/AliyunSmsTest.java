package com.zxdmy.excite.admin.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxdmy.excite.component.aliyun.AliyunSmsService;
import com.zxdmy.excite.component.vo.AliyunSmsVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 阿里云短信服务测试类
 * </p>
 *
 * @author 拾年之璐
 * @since 2022/1/3 16:40
 */
@SpringBootTest
public class AliyunSmsTest {


    @Autowired
    private AliyunSmsService aliyunSmsService;

    /**
     * 保存配置信息测试
     *
     * @throws JsonProcessingException 异常
     */
    @Test
    void saveTest() throws JsonProcessingException {
        // 初始数据定义
        AliyunSmsVO aliyunSmsVO = new AliyunSmsVO()
                .setKey("aliyunSms01")
                .setAccessKeyId("********")
                .setAccessKeySecret("*******")
                .setSignName("中国移动")
                .setTemplateCode("SMS_18*****22")
                .setTemplateParam(new String[]{"username", "filename", "password"});
        aliyunSmsService.saveAliyunSmsConfig(aliyunSmsVO);
    }

    /**
     * 读取配置信息测试
     */
    @Test
    void getTest() {
        AliyunSmsVO aliyunSmsVO = aliyunSmsService.getAliyunSmsConfig("aliyunSms01");
        System.out.println(aliyunSmsVO);

    }

    /**
     * 单个发送测试
     */
    @Test
    void sendTest() {
        Boolean result = aliyunSmsService.sendSmsOne("aliyunSms01", "15900889977", new String[]{"张三", "9月发票", "123456"});
    }

    /**
     * 批量发送测试
     */
    @Test
    void sendBatchTest() {
        Map<String, String[]> map = new HashMap<>();
        map.put("15900889977", new String[]{"张三", "9月发票", "123456"});
        map.put("18800889977", new String[]{"李四", "10月发票", "234567"});
        map.put("18900889977", new String[]{"王五", "11月发票", "345678"});
        Map<String, Boolean> mapResult = aliyunSmsService.sendSmsBatch("aliyunSms01", map);
    }


}
