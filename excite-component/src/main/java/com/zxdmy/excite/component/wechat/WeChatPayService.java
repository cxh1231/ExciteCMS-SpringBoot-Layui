package com.zxdmy.excite.component.wechat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.*;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.zxdmy.excite.common.utils.HttpServletRequestUtil;
import com.zxdmy.excite.component.bo.WeChatPayBO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 微信支付服务实现类
 * 只实现V3版本的接口（文档地址：https://pay.weixin.qq.com/wiki/doc/apiv3/apis/index.shtml ）
 *
 * @author 拾年之璐
 * @since 2022-01-02 0002 23:34
 */
@Service
@AllArgsConstructor
public class WeChatPayService {

    private IGlobalConfigService configService;

    private static final String DEFAULT_SERVICE = "wechatPay";

    private static final String DEFAULT_KEY = "wechatPay";

    // 千万不要使用这个垃圾的微信支付沙盒环境.....
    private static final Boolean SAND_BOX_ENV = false;

    /**
     * 保存微信支付配置信息至数据库
     *
     * @param weChatPayBO 微信支付实体
     * @return 结果：T|F
     * @throws JsonProcessingException 异常
     */
    public boolean saveConfig(WeChatPayBO weChatPayBO) throws JsonProcessingException {
        // 必填信息不能为空
        if (null == weChatPayBO.getAppId() || null == weChatPayBO.getMchId() || null == weChatPayBO.getMchKey()) {
            throw new ServiceException("APPID、商户号、商户秘钥不能为空！");
        }
        // 保存并返回结果
        return configService.save(DEFAULT_SERVICE, null != weChatPayBO.getKey() ? weChatPayBO.getKey() : DEFAULT_KEY, weChatPayBO, true);
    }

    /**
     * 从数据库中读取配置信息
     *
     * @param confKey 微信支付的配置KEY
     * @return 微信支付配置信息
     */
    public WeChatPayBO getConfig(String confKey) {
        return (WeChatPayBO) configService.get(DEFAULT_SERVICE, confKey, new WeChatPayBO());
    }

    /**
     * 从数据库中读取配置信息（默认KEY）
     *
     * @return 微信支付配置信息
     */
    public WeChatPayBO getConfig() {
        return this.getConfig(DEFAULT_KEY);
    }

    /**
     * 获取微信支付相关接口的服务
     * （后续的几个服务方法，实现了基本的实例）
     * （此接口也可以直接在controller中使用）
     *
     * @return 微信支付服务接口
     */
    public WxPayService getWxPayService(String confKey) {
        if (null == confKey) {
            return this.getWxPayService();
        }
        // 读取配置信息
        WeChatPayBO weChatPayBO = this.getConfig(confKey);
        // 生成配置
        WxPayConfig payConfig = new WxPayConfig();
        // 填充基本配置信息
        payConfig.setAppId(StringUtils.trimToNull(weChatPayBO.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(weChatPayBO.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(weChatPayBO.getMchKey()));
        payConfig.setApiV3Key(StringUtils.trimToNull(weChatPayBO.getApiV3Key()));
        payConfig.setSubAppId(StringUtils.trimToNull(weChatPayBO.getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(weChatPayBO.getSubMchId()));
        payConfig.setKeyPath(StringUtils.trimToNull(weChatPayBO.getKeyPath()));
        payConfig.setPrivateCertPath(StringUtils.trimToNull(weChatPayBO.getPrivateCertPath()));
        payConfig.setPrivateKeyPath(StringUtils.trimToNull(weChatPayBO.getPrivateKeyPath()));
        payConfig.setNotifyUrl(StringUtils.trimToNull(weChatPayBO.getNotifyUrl()));
        // 创建配置服务
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(SAND_BOX_ENV);
        if (SAND_BOX_ENV) {
            try {
                payConfig.setMchKey(wxPayService.getSandboxSignKey());
                wxPayService.setConfig(payConfig);
            } catch (WxPayException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        // 返回结果
        return wxPayService;
    }

    /**
     * 获取微信支付相关接口的服务（默认KEY）
     *
     * @return 微信支付服务接口
     */
    public WxPayService getWxPayService() {
        return this.getWxPayService(DEFAULT_KEY);
    }

    /**
     * 统一下单服务接口（只设置了必填信息）（V3版本）
     *
     * @param confKey     必填：微信支付配置信息的KEY。如果使用默认KEY，输入null
     * @param tradeType   必填：交易类型：jsapi（含小程序）、app、h5、native
     * @param description 必填：商品描述（商品标题）
     * @param outTradeNo  必填：商家订单号
     * @param total       必填：商品金额（单位：分）
     *                    //     * @param notifyUrl   必填：通知回调地址
     * @param openId      支付用户的OpenId，JSAPI支付时必填。
     * @return 获取"预支付交易会话标识"返回的结果
     */
    public WxPayUnifiedOrderV3Result unifiedOrder(String confKey, String tradeType, String description, String outTradeNo, Integer total, String openId) {
        // 构建统一下单请求参数对象
        WxPayUnifiedOrderV3Request wxPayUnifiedOrderV3Request = new WxPayUnifiedOrderV3Request();
        // 对象中写入数据
        wxPayUnifiedOrderV3Request
                // 【1】必填信息
                // 应用ID：必填，可不填，通过配置信息读取
                // .setAppid(weChatPayBO.getAppId())
                // 直连商户号：必填，可不填，通过配置信息读取
                // .setMchid(weChatPayBO.getMchId())
                // 商品描述：必填
                .setDescription(description)
                // 商户订单号：必填，同一个商户号下唯一
                .setOutTradeNo(outTradeNo)
                // 通知地址：必填，公网域名必须为https，外网可访问。
                // 可不填，通过配置信息读取
                .setNotifyUrl(this.getConfig(confKey).getNotifyUrl())
                // 订单金额：单位（分）
                .setAmount(new WxPayUnifiedOrderV3Request.Amount().setTotal(total))
                // 【2】选填信息
                // 附加信息
                .setAttach("附加信息")
                // 订单优惠标记
                .setGoodsTag("ABCD")
        // ...
        ;

        try {
            // 根据请求类型，返回指定类型，其中包含：【3】条件必填信息
            switch (tradeType.toLowerCase()) {
                // Native支付
                case "native":
                    return this.getWxPayService(confKey).unifiedOrderV3(TradeTypeEnum.NATIVE, wxPayUnifiedOrderV3Request);
                // JSAPI支付
                case "jsapi":
                    // 用户在直连商户appid下的唯一标识。 下单前需获取到用户的Openid
                    wxPayUnifiedOrderV3Request.setPayer(new WxPayUnifiedOrderV3Request.Payer().setOpenid(openId));
                    return this.getWxPayService(confKey).unifiedOrderV3(TradeTypeEnum.JSAPI, wxPayUnifiedOrderV3Request);
                // H5支付
                case "h5":
                    wxPayUnifiedOrderV3Request.setSceneInfo(
                            new WxPayUnifiedOrderV3Request.SceneInfo()
                                    // 用户终端IP
                                    .setPayerClientIp(HttpServletRequestUtil.getRemoteIP())
                                    .setH5Info(
                                            new WxPayUnifiedOrderV3Request.H5Info()
                                                    // 场景类型
                                                    .setType("wechat")
                                    )
                    );
                    return this.getWxPayService(confKey).unifiedOrderV3(TradeTypeEnum.H5, wxPayUnifiedOrderV3Request);
                // APP支付
                case "app":
                    return this.getWxPayService(confKey).unifiedOrderV3(TradeTypeEnum.APP, wxPayUnifiedOrderV3Request);
                default:
                    throw new ServiceException("输入的[" + tradeType + "]不合法，只能为native、jsapi、h5、app其一，请核实！");
            }
        } catch (WxPayException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 订单查询接口（新版V3）
     *
     * @param confKey       必填：微信支付配置信息的KEY。如果使用默认KEY，输入null
     * @param transactionId 微信订单号
     * @param outTradeNo    商户系统内部的订单号，当没提供transactionId时需要传这个。
     * @return 查询订单 返回结果对象
     */
    public WxPayOrderQueryV3Result queryOrder(String confKey, String transactionId, String outTradeNo) {
        try {
            return this.getWxPayService(confKey).queryOrderV3(transactionId, outTradeNo);
        } catch (WxPayException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 退款接口
     *
     * @param confKey     必填：微信支付配置信息的KEY。如果使用默认KEY，输入null
     * @param outTradeNo  商户订单号
     * @param outRefundNo 商户退款单号
     * @param total       订单总金额（单位：分）
     * @param refund      退款金额（单位：分）
     */
    public WxPayRefundV3Result refund(String confKey, String outTradeNo, String outRefundNo, int total, int refund){
        WxPayRefundV3Request wxPayRefundV3Request = new WxPayRefundV3Request();
        wxPayRefundV3Request
                .setOutTradeNo(outTradeNo)
                .setOutRefundNo(outRefundNo)
                .setAmount(new WxPayRefundV3Request.Amount()
                        .setTotal(total)
                        .setRefund(refund)
                        .setCurrency("CNY")
                );
        try {
            return this.getWxPayService(confKey).refundV3(wxPayRefundV3Request);
        } catch (WxPayException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    

}
