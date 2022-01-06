package com.zxdmy.excite.component.aliyun;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.zxdmy.excite.component.bo.AliyunSmsBO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云短信服务（Short Message Service）实现
 *
 * @author 拾年之璐
 * @since 2022-01-02 0002 23:33
 */
@Service
@AllArgsConstructor
public class AliyunSmsService {

    IGlobalConfigService configService;

    private static final String DEFAULT_SERVICE = "aliyunSms";

    /**
     * 保存阿里云短信服务的开发者信息、消息模板相关信息
     * 注意：同一个系统的消息模板可能有多个，所以
     *
     * @param aliyunSmsBO 实体信息，其中KEY必填
     * @return 保存结果
     * @throws JsonProcessingException 异常
     */
    public boolean saveAliyunSmsConfig(AliyunSmsBO aliyunSmsBO) throws JsonProcessingException {
        // 如果必填项为空，则返回错误信息
        if (null == aliyunSmsBO.getKey() || null == aliyunSmsBO.getAccessKeyId() || null == aliyunSmsBO.getAccessKeySecret() || null == aliyunSmsBO.getSignName() || null == aliyunSmsBO.getTemplateCode()) {
            throw new ServiceException("部分必填信息为空，请检查！");
        }
        // 保存
        return configService.save(DEFAULT_SERVICE, aliyunSmsBO.getKey(), aliyunSmsBO, true);
    }

    /**
     * 通过配置KEY获取短信配置信息
     *
     * @param confKey 阿里云短信服务的配置key
     * @return 阿里云短信服务实体
     */
    public AliyunSmsBO getAliyunSmsConfig(String confKey) {
        AliyunSmsBO aliyunSmsBO = new AliyunSmsBO();
        return (AliyunSmsBO) configService.get(DEFAULT_SERVICE, confKey, aliyunSmsBO);
    }

    /**
     * 发送短信服务
     *
     * @param confKey       confKey，当前短信模板配置的key
     * @param phone         手机号数组
     * @param templateValue 消息模板中的参数值
     * @return 结果
     */
    public boolean sendSmsOne(String confKey, String phone, String[] templateValue) {
        // 从数据库读取配置信息
        AliyunSmsBO aliyunSmsBO = this.getAliyunSmsConfig(confKey);
        if (null == aliyunSmsBO) {
            throw new ServiceException("阿里云短信服务配置信息有误/不存在，请核实");
        }
        // 如果输入的参数个数和配置信息里的参数个数不相等，则返回错误信息
        if (templateValue.length != aliyunSmsBO.getTemplateParam().length) {
            throw new ServiceException("templateValue个数有误，请核实");
        }
        // 发起短信并获取发送结果
        SendSmsResponse response = this.sendSms(aliyunSmsBO, phone, templateValue, null);
        // 结果不为空，进一步判断
        if (response != null) {
            // 打印结果
//            System.out.println(response.body.code); // 发送成功为OK
//            System.out.println(response.body.message); // 发送成功为OK
//            System.out.println(response.body.requestId); // 请求ID
//            System.out.println(response.body.bizId); // 请求ID
            return "OK".equals(response.body.code);
        }
        return false;
    }

    /**
     * 批量发送短信
     * 其中官网中有批量发送短信的接口SendBatchSms。
     * 但我们这里还是逐个发送
     *
     * @param confKey 短信模板的key
     * @param smsMap  短信map，其中key为手机号，value为String[]数组，其大小与消息模板参数个数一致，并依次对应
     * @return 结果map
     */
    public Map<String, Boolean> sendSmsBatch(String confKey, Map<String, String[]> smsMap) {
        // 从数据库读取配置信息
        AliyunSmsBO aliyunSmsBO = this.getAliyunSmsConfig(confKey);
        if (null == aliyunSmsBO) {
            throw new ServiceException("阿里云短信服务配置信息有误，请核实");
        }
        int templateLength = aliyunSmsBO.getTemplateParam().length;
        // 发起短信并获取发送结果
        SendSmsResponse response;
        // 记录结果的map
        Map<String, Boolean> result = new HashMap<>();
        // 遍历map，逐个构造并发送短信
        for (String phone : smsMap.keySet()) {
            // 参数值对等，则可以发送
            if (smsMap.get(phone).length == templateLength) {
                response = this.sendSms(aliyunSmsBO, phone, smsMap.get(phone), null);
                // 发送成功
                if (response != null && "OK".equals(response.body.code)) {
                    result.put(phone, true);
                }
                // 发送失败
                else {
                    result.put(phone, false);
                }
            }
            // 参数值不对等，直接标记失败
            else {
                result.put(phone, false);
            }
        }
        return result;
    }

    /**
     * 配置并发送短信实现类
     *
     * @param aliyunSmsBO   配置信息
     * @param phone         接收手机号
     * @param templateValue 模板JSON格式的参数
     * @param outId         流水号
     * @return 发送结果
     */
    private SendSmsResponse sendSms(AliyunSmsBO aliyunSmsBO, String phone, String[] templateValue, String outId) {
        // 阿里云短信服务信息配置
        Config config = new Config()
                // 必填，开发者的 AccessKey ID
                .setAccessKeyId(aliyunSmsBO.getAccessKeyId())
                // 必填，开发者的 AccessKey Secret
                .setAccessKeySecret(aliyunSmsBO.getAccessKeySecret())
                // 默认，暂时不支持多Region，默认即可
                .setRegionId("cn-hangzhou")
                // 默认，产品域名，开发者无需替换
                .setEndpoint("dysmsapi.aliyuncs.com")
                // 可选，启用https协议
                .setProtocol("https");
        // 构造请求参数
        StringBuilder templateParam = new StringBuilder("{");
        for (int i = 0; i < aliyunSmsBO.getTemplateParam().length; i++) {
            templateParam.append("\"").append(aliyunSmsBO.getTemplateParam()[i]).append("\":\"").append(templateValue[i]).append("\",");
        }
        templateParam.append("}");
        // 短信请求信息配置
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                // 必填，接收短信的手机号
                .setPhoneNumbers(phone)
                // 必填，设置签名名称，应严格按"签名名称"填写，格式如：阿里云
                .setSignName(aliyunSmsBO.getSignName())
                // 必填，设置模板CODE，应严格按"模板CODE"填写，格式：SMS_88888888
                .setTemplateCode(aliyunSmsBO.getTemplateCode())
                // 可选，设置模板参数, 假如模板中存在变量需要替换则为必填项
                .setTemplateParam(templateParam.toString())
                // 可选，设置流水号
                //.setOutId("10086")
                ;
        // 发起请求
        SendSmsResponse response;
        try {
            response = new Client(config).sendSms(sendSmsRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return response;
    }
}
