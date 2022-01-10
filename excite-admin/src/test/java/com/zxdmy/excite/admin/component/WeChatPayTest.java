package com.zxdmy.excite.admin.component;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.zxdmy.excite.component.bo.WeChatPayBO;
import com.zxdmy.excite.component.wechat.WeChatPayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 微信支付测试类
 *
 * @author 拾年之璐
 * @since 2022/1/10 16:26
 */
@SpringBootTest
public class WeChatPayTest {

    @Autowired
    private WeChatPayService weChatPayService;

    @Test
    void saveTest() throws JsonProcessingException {
        WeChatPayBO weChatPayBO = new WeChatPayBO();
        weChatPayBO.setAppId("")
                .setMchId("")
                .setMchKey("")
                .setApiV3Key("")
                .setKeyPath("E:\\微信支付\\Cert\\apiclient_cert.p12")
                .setPrivateKeyPath("E:\\微信支付\\Cert\\apiclient_key.pem")
                .setPrivateCertPath("E:\\微信支付\\Cert\\apiclient_cert.pem")
                .setNotifyUrl("https://demo.zxdmy.com/notify");
        if (weChatPayService.saveConfig(weChatPayBO))
            System.out.println("成功！");

    }

    @Test
    void getTest() {
        System.out.println(weChatPayService.getConfig());
    }

    @Test
    void unifiedOrderTest2() throws WxPayException {
        WxPayUnifiedOrderV3Result wxPayUnifiedOrderV3Result = weChatPayService.unifiedOrder(
                null, "jsapi", "商品标题", IdUtil.simpleUUID(), 1, "od0Pf5yi7sdBXm95fT786tdqtLgA"
        );
        System.out.println(wxPayUnifiedOrderV3Result.getCodeUrl());
        System.out.println(wxPayUnifiedOrderV3Result.getPrepayId());
        System.out.println(wxPayUnifiedOrderV3Result.getH5Url());
    }

    @Test
    void queryTest() {
//        375f1b3a8f3f4f2aa6f086575f168096
        // 4200001322202201105842754907
        System.out.println(weChatPayService.queryOrder(null, "4200001322202201105842754907", null));
    }

    @Test
    void refundTest() throws WxPayException {
        System.out.println(weChatPayService.refund(null, "a4d798f07f8e4304bc0023e6ad4d3c5a", IdUtil.simpleUUID(), 1, 1));
    }
}
