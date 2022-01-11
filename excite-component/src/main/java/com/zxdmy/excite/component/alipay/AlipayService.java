package com.zxdmy.excite.component.alipay;

import cn.hutool.core.util.IdUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.zxdmy.excite.component.bo.AlipayBO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 支付宝支付服务实现类
 * https://github.com/alipay/alipay-easysdk/tree/master/java
 *
 * @author 拾年之璐
 * @since 2022-01-02 0002 23:31
 */
@Service
@AllArgsConstructor
public class AlipayService {

    private IGlobalConfigService configService;

    private static final String DEFAULT_SERVICE = "alipay";

    private static final String DEFAULT_KEY = "alipay";

    /**
     * 保存信息至数据库
     *
     * @param alipayBO 支付宝支付培配置信息
     * @return 保存结果：T|F
     */
    public boolean saveConfig(AlipayBO alipayBO) {
        // 必填信息不能为空
        if (null == alipayBO.getAppId() || null == alipayBO.getMerchantPrivateKey()) {
            throw new ServiceException("APPID和应用私钥不能为空，请检查！");
        }
        // 如果支付宝公钥为空，则三个证书不能为空
        if (null == alipayBO.getAlipayPublicKey())
            if (null == alipayBO.getMerchantCertPath() || null == alipayBO.getAlipayCertPath() || null == alipayBO.getAlipayRootCertPath())
                throw new ServiceException("证书模式下，[应用公钥证书][支付宝公钥证书][支付宝根证书]不能为空，请检查！");
        // 保存信息至数据库
        try {
            return configService.save(DEFAULT_SERVICE, alipayBO.getKey() != null ? alipayBO.getKey() : DEFAULT_KEY, alipayBO, true);
        } catch (JsonProcessingException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 从数据库读取配置信息（指定KEY）
     *
     * @param confKey 本条支付信息的KEY，如果为null，读取默认KEY
     * @return 支付宝支付配置信息
     */
    public AlipayBO getConfig(String confKey) {
        if (null == confKey) {
            return this.getConfig();
        }
        return (AlipayBO) configService.get(DEFAULT_SERVICE, confKey, new AlipayBO());
    }

    /**
     * 从数据库读取配置信息（默认KEY）
     *
     * @return 支付宝支付配置信息
     */
    public AlipayBO getConfig() {
        return this.getConfig(DEFAULT_KEY);
    }

    /**
     * 统一支付服务接口（当面付、电脑网站支付、手机网站支付、APP支付）
     *
     * @param confKey     支付宝配置信息的KEY。如果加载默认的KEY，输入null
     * @param payType     支付类型：当面付（faceToFace），电脑网站支付（page），手机网站支付（wap），APP支付（app）
     * @param subject     商品名称
     * @param outTradeNo  商户订单号：商户内唯一
     * @param totalAmount 总金额（单位：元），实例：12.34
     * @param returnUrl   支付成功后跳转页面（只针对网站支付有效）
     * @param quitUrl     支付取消跳转页面（只针对手机网站支付有效）
     * @return 返回结果：
     */
    public String pay(String confKey, String payType, String subject, String outTradeNo, String totalAmount, String returnUrl, String quitUrl) {
        // 必填信息不能为空
        if (null == subject || null == outTradeNo || null == totalAmount) {
            throw new ServiceException("商品名称、商户订单号、商品价格不能为空！");
        }
        // 1. 设置参数
        Factory.setOptions(this.getOptions(confKey));
        try {
            // 2. 调用API发起创建支付
            switch (payType.toLowerCase()) {
                // 当面付
                case "facetoface":
                    // 创建订单
                    AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate(subject, outTradeNo, totalAmount);
                    // 成功？
                    if (ResponseChecker.success(response))
                        return response.getQrCode();
                    break;
                // 电脑网站支付
                case "page":
                    if (null == returnUrl) {
                        throw new ServiceException("电脑网站支付的跳转地址（returnUrl）不能为空！");
                    }
                    // 调用接口
                    AlipayTradePagePayResponse response1 = Factory.Payment.Page().pay(subject, outTradeNo, totalAmount, returnUrl);
                    // 成功？
                    if (ResponseChecker.success(response1))
                        return response1.getBody();
                    break;
                // 手机网站支付
                case "wap":
                    if (null == returnUrl || null == quitUrl) {
                        throw new ServiceException("手机网站支付的失败退出地址（quitUrl）、成功跳转地址（returnUrl）不能为空！");
                    }
                    AlipayTradeWapPayResponse response2 = Factory.Payment.Wap().pay(subject, outTradeNo, totalAmount, quitUrl, returnUrl);
                    // 成功
                    if (ResponseChecker.success(response2))
                        return response2.getBody();
                    break;
                // APP支付
                case "app":
                    AlipayTradeAppPayResponse response3 = Factory.Payment.App().pay(subject, outTradeNo, totalAmount);
                    // 成功
                    if (ResponseChecker.success(response3))
                        return response3.getBody();
                    break;
                // 其他类型：错误
                default:
                    throw new ServiceException("输入的支付类型[" + payType + "]不在许可支付类型范围内。许可类型：当面付（faceToFace），电脑网站支付（page），手机网站支付（wap），APP支付（app）");
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return null;
    }

    /**
     * 查询支付订单接口
     *
     * @param confKey    可选：支付宝配置信息的KEY。如果加载默认的KEY，输入null
     * @param tradeNo    特殊可选：支付宝交易号（订单号）
     * @param outTradeNo 特殊可选：商家订单号
     * @return 查询成功：{ Y，支付宝交易号，商家订单号，交易状态，订单金额，买家ID，买家支付宝账号 } <br> 查询失败：{ E，错误代码，错误描述 }
     * @apiNote tradeNo 和 outTradeNo 不能同时为空。同时存在优先取 tradeNo。
     */
    public String[] queryPay(String confKey, String tradeNo, String outTradeNo) {
        // 判断
        if (null == tradeNo && null == outTradeNo) {
            throw new ServiceException("tradeNo 和 outTradeNo 不能同时为空！");
        }
        // 设置参数
        Factory.setOptions(this.getOptions(confKey));
        try {
            // 执行查询
            AlipayTradeQueryResponse response = Factory.Payment.Common().optional("trade_no", tradeNo).query(outTradeNo);
            // 请求成功（即返回信息中没有sub_code）
            if (ResponseChecker.success(response)) {
                return new String[]{
                        "Y",
                        response.tradeNo,
                        response.outTradeNo,
                        response.tradeStatus,
                        response.totalAmount,
                        response.buyerUserId,
                        response.buyerLogonId
                };
            } else {
                return new String[]{"E", response.subCode, response.subMsg};
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 退款接口（支持部分退款）
     *
     * @param confKey      可选：支付宝配置信息的KEY。如果加载默认的KEY，输入null
     * @param tradeNo      特殊可选：商户订单号
     * @param outTradeNo   特殊可选：商户订单号
     * @param refundAmount 必填：退款金额
     * @param reason       可选：退款原因
     * @return 本次请求退款成功：{ Y，支付宝交易号，商家订单号，退款请求号，总退款金额 } <br> 历史请求退款成功：{ N，支付宝交易号，商家订单号，退款请求号，退款金额 } <br> 退款发生错误：{ E，错误代码，错误描述 }
     * @apiNote tradeNo 和 outTradeNo 不能同时为空。同时存在优先取 tradeNo。
     */
    public String[] refund(String confKey, String tradeNo, String outTradeNo, String refundAmount, String reason) {
        // 判断
        if (null == tradeNo && null == outTradeNo) {
            throw new ServiceException("tradeNo 和 outTradeNo 不能同时为空！");
        }
        // 设置参数
        Factory.setOptions(this.getOptions(confKey));
        try {
            // 生成唯一的款请求号
            String outRequestNo = IdUtil.simpleUUID();
            // 发起请求
            AlipayTradeRefundResponse response = Factory.Payment.Common()
                    // 支付宝交易号
                    .optional("trade_no", tradeNo)
                    // 退款原因
                    .optional("refund_reason", reason)
                    // 退款请求号
                    .optional("out_request_no", outRequestNo)
                    // 执行退款
                    .refund(outTradeNo, refundAmount);
            // 如果请求成功（即返回信息中没有sub_code）
            if (ResponseChecker.success(response)) {
                return new String[]{
                        // 本次请求退款状态（即资金有改变，详情：https://opensupport.alipay.com/support/knowledge/27585/201602348776 ）
                        response.fundChange,
                        response.tradeNo,
                        response.outTradeNo,
                        outRequestNo,
                        response.refundFee
                };
            } else {
                return new String[]{"E", response.subCode, response.subMsg};
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 查询退款
     *
     * @param confKey      可选：支付宝配置信息的KEY。如果加载默认的KEY，输入null
     * @param outTradeNo   必填：商户订单号
     * @param outRequestNo 必填：退款请求号
     * @return 已退款：{ Y，支付宝交易号，商家订单号，退款请求号，订单金额，退款金额，退款原因 } <br> 未退款：{ N，描述 } <br> 发生错误：{ E，错误代码，错误描述 }
     */
    public String[] queryRefund(String confKey, String outTradeNo, String outRequestNo) {
        // 设置参数
        Factory.setOptions(this.getOptions(confKey));
        // 如果请求号为空，则表示全额退款，设置请求号位商家订单号
        if (null == outRequestNo) {
            outRequestNo = outTradeNo;
        }
        try {
            // 发起请求
            AlipayTradeFastpayRefundQueryResponse response = Factory.Payment.Common().queryRefund(outTradeNo, outRequestNo);
            // 如果请求成功（即返回信息中没有sub_code）
            if (ResponseChecker.success(response)) {
                // 如果该接口返回了查询数据，则代表退款成功（详情：https://opensupport.alipay.com/support/knowledge/27585/201602348776 ）
                if (null != response.refundAmount) {
                    return new String[]{
                            "Y",
                            response.tradeNo,
                            response.outTradeNo,
                            response.outRequestNo,
                            response.totalAmount,
                            response.refundAmount,
                            response.refundReason
                    };
                } else {
                    return new String[]{
                            "N",
                            "该订单未退款或输入的退款请求号有误，请检查！"
                    };
                }
            } else {
                return new String[]{"E", response.subCode, response.subMsg};
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 下载对账单（不能查询当天或当月的)
     * https://developer.aliyun.com/article/710922
     *
     * @param confKey 可选：支付宝配置信息的KEY。如果加载默认的KEY，输入null
     * @param date    必填：交易的具体日期（如2022-01-01）或月份（2021-12）
     * @return 获取成功：{Y，下载URL} <br> 发生错误：{ E，错误代码，错误描述 }
     */
    public String[] downloadBill(String confKey, String date) {
        // 设置参数
        Factory.setOptions(this.getOptions(confKey));
        try {
            // 发送请求
            AlipayDataDataserviceBillDownloadurlQueryResponse response = Factory.Payment.Common().downloadBill("trade", date);

            // 如果请求成功（即返回信息中没有sub_code）
            if (ResponseChecker.success(response)) {
                return new String[]{
                        "Y",
                        response.billDownloadUrl
                };
            } else {
                return new String[]{"E", response.subCode, response.subMsg};
            }

        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }


    /**
     * 读取配置信息
     *
     * @param confKey 支付宝配置信息的KEY。如果加载默认的KEY，输入null
     * @return 配置信息
     */
    private Config getOptions(String confKey) {
        // 从数据库中读取配置信息
        AlipayBO alipayBO = this.getConfig(confKey);
        // 将配置信息写入支付宝支付配置类中
        Config config = new Config();
        config.protocol = alipayBO.getProtocol();
        config.gatewayHost = alipayBO.getGatewayHost();
        config.signType = alipayBO.getSignType();

        config.appId = alipayBO.getAppId();

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = alipayBO.getMerchantPrivateKey();
        // 如果三个证书不为空，则优先设置三个证书
        if (null != alipayBO.getMerchantCertPath() && null != alipayBO.getAlipayCertPath() && null != alipayBO.getAlipayRootCertPath()) {
            //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
            config.merchantCertPath = alipayBO.getMerchantCertPath();
            config.alipayCertPath = alipayBO.getAlipayCertPath();
            config.alipayRootCertPath = alipayBO.getAlipayRootCertPath();
        } else if (null != alipayBO.getAlipayPublicKey()) {
            //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
            config.alipayPublicKey = alipayBO.getAlipayPublicKey();
        } else {
            throw new ServiceException("配置信息有误：证书模式配置不完整或未配置公钥模式，请检查！");
        }

        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = alipayBO.getNotifyUrl();

        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        config.encryptKey = alipayBO.getEncryptKey();

        return config;
    }
}
