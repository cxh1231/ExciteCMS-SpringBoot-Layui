package com.zxdmy.excite.admin.component;

import cn.hutool.core.util.IdUtil;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxdmy.excite.component.alipay.AlipayService;
import com.zxdmy.excite.component.bo.AlipayBO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2022/1/11 11:42
 */
@SpringBootTest
public class AlipayTest {


    @Autowired
    private AlipayService alipayService;

    @Test
    void saveTest() {
        AlipayBO alipayBO = new AlipayBO()
                // 基本信息
                .setAppId("20210058")
                .setAppName("沙箱测试应用")
                .setMerchantPrivateKey("MIIEvAUpFY8wf6zJVf22u2kNomfr6+amzTzYE31j47jpOSNnB01zv8jiEFBWvGHLqeFU3eNpsen2HUbbP7drl58z9RhliJIamZ1+UXR4vV+3pzzd32oXUSUId35fjEYGiwPwy0LAqXn2kFD7Txk4kiw5sP1xdv4YIEEGWHrOtN9fznt6vNqY9BWyZCDMJ46SqWBqHY7XVemP991JgDa1HdFVsy6SnTMChhDLGcR3ND0+EdsYYv6MeNV89xdeakmXNQUFZ3KPFYnBhMnY01u4Gb6lZAZQA2zrhIS1CSERCsRLyqd/DmX5hX3U7JAgMBAAECggEAXxat7xpuR9XhoHHuCvyA1AhHLlu3Zvsz9xbzzi0G8gpcYDgno+vc86yrwtPgWb00Ef5ajhOL0fiFkn8Hpr3S4QqaxFSgnu5i5epV8FhAJg2xTuzzeiN7hFg64UG2ZH0bBW32qCPaPl0kS6LrlFkhKNh6LmUFotD6yzyBeK8U6BWEIq07KOKB0nW16duQ8BDj9BwLcyOjYLCIUccK0Z3+N5KVr2aJev6LZcNwZuXhdk4s1tGnX2dn9ATGi5xNFBDBS0nnCdAYnIYddNbozWJvSqqHJ0igaSOmO14dZBnpElY4vA3zXTsglV3A6BjMq4ahjG2crHA3ZQvgFXZ2QCY7gQKBgQDc19nWcQHJB3mvEg1DAdXZ5VBoy1Yo2sRAsjzffS7dUcPfEEod1VTgpxolv8awNxmge1wUYhZfGEb+cJacmYFTjyxKzUQreCKaIwqlKd+TsCLsviDvGzsMcKmhWe+JAAXZnTtsv/aVCGzN8ziOulDAkD7KRbyp2Uf2ekOWTEDAOQKBgQDBb8DNCY7pDU9/Dz11RMA0QOdhpWfvmv5ysYhq9wnTcmad3ySJvj83+Bf/OsASqcDY9w8mVKoVSVFhIfAH5ML1f5jSWkGSxyZQnQoh1pBBbG7bUIanTnsDy7hch/iGk/N9UN/kFak6fLBHVlnI+qfniAcJiNy03FaxwclMKg7jEQKBgCxybO9R0zAohv8LPQwNZIL0Ohi9Q9v5G6KBvOqmATad7DQKzT/v3aNRPlv2mwCANnIsIb4gd6wv8Kno8wcVhgfROvLbGSs+hIhNISlYohzRSFYpdetpqZq5WgqVVTZXgNXpZTpf8DrSdUOF/g4LxZDb9ycyneP5TRh4Rv4K3sVRAoGATVoB8Dv1QO6IrpeKjP1cGsklfZ+mK2OAgp7JnXSCImLp9BGKS+ae4yO7fN2idxQYwOoyzbInfXGfMEdg89cfuwo2M0/STv6CLNRPe+6QKwlQXzUZU4gHmyH47E+XK0G4qZEQpuWekXvRBgXay4qoX+a+YaqwD0bZCCYk9+cNovECgYBnJ+ez1B4YcADmNruPhKLiQ0/RkkuaGl8+CqslGhXJ4rqlS9u7uA66EDD3A5Fn1DX89i3wMYIVU0M8PCC2VBz8q4KdIPzRVe+NcFVTdOvDqr4dxrDhpu//HKQJFpwtildby2/MArP7HGDDI6oVhzd70ij/TzprmsRMvxC315+TJA==")
                // 证书模式
                .setMerchantCertPath("E:\\支付宝支付\\appCertPublicKey_2021000117696058.crt")
                .setAlipayCertPath("E:\\支付宝支付\\alipayCertPublicKey_RSA2.crt")
                .setAlipayRootCertPath("E:\\支付宝支付\\alipayRootCert.crt")
                // 接口内容加密方式
                .setEncryptKey("cAikHtKWeTYvCvw==")
                // 注意：沙箱环境下，请配置此网关！正式环境可忽略！
                .setGatewayHost("openapi.alipaydev.com");
        alipayService.saveConfig(alipayBO);
    }

    /**
     * 支付测试
     */
    @Test
    void payTest() {
        String outTradeNo = IdUtil.simpleUUID();
        System.out.println(outTradeNo);
        System.out.println(alipayService.pay(null, "page", "测试商品名称", outTradeNo, "6666", "https://www.zxdmy.com", "https://www.zxdmy.com"));
    }

    /**
     * 查询测试
     */
    @Test
    void queryTest() {
        System.out.println(Arrays.toString(alipayService.queryPay(null, "2022011122001428220502263620", "f9bd5856549b47b684284816ac324f8c")));
        // [Y, 2022011122001428220502263620, f9bd5856549b47b684284816ac324f8c, TRADE_SUCCESS, 666.00, 2088622956328227, aev***@sandbox.com]
    }

    /**
     * 退款测试
     */
    @Test
    void refundTest() {
        System.out.println(
                Arrays.toString(alipayService
                        .refund(null, "2022011122001428220502263620", null, "20.00", "不想要了")));
        // [Y, 2022011122001428220502263620, f9bd5856549b47b684284816ac324f8c, da7486bdbfe644c48c815af84af423cd, 10.00]
        // [Y, 2022011122001428220502263620, f9bd5856549b47b684284816ac324f8c, 1dde059885134947948197f3b3268cf0, 30.00]
    }

    /**
     * 查询退款测试
     */
    @Test
    void queryRefundTest() {
        System.out.println(Arrays.toString(alipayService.queryRefund(
                null,
                "f9bd5856549b47b684284816ac324f8c0",
                null)));
        // [Y, 2022011122001428220502263620, f9bd5856549b47b684284816ac324f8c, da7486bdbfe644c48c815af84af423cd, 666.00, 10.00, null]
        // [Y, 2022011122001428220502263620, f9bd5856549b47b684284816ac324f8c, 1dde059885134947948197f3b3268cf0, 666.00, 20.00, null]
    }

    @Test
    void downLoadTest() {
        System.out.println(Arrays.toString(alipayService.downloadBill(null, "2021-12")));
    }
}
