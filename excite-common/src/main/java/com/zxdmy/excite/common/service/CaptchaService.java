package com.zxdmy.excite.common.service;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import com.google.code.kaptcha.Producer;

import com.zxdmy.excite.common.entity.CaptchaDomain;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * 验证码服务接口的实现
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-10-15 0015 10:18
 */
@Service
@AllArgsConstructor
public class CaptchaService {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMathOne")
    private Producer captchaProducerMathOne;

    @Resource(name = "captchaProducerMathTwo")
    private Producer captchaProducerMathTwo;

    private static final String TYPE_CHAR = "char";
    private static final String TYPE_MATH_ONE = "math";
    private static final String TYPE_MATH_TWO = "math2";

    /**
     * Kaptcha生成验证码实体
     *
     * @param type 类型，char - 字符(缺省) | math - 一位数算式 | math2 - 两位数算式
     * @return 验证码实体
     */
    public CaptchaDomain createGoogleCaptcha(String type) {
        // 定义验证码实体
        CaptchaDomain captchaDomain = new CaptchaDomain();
        // 一位数加减乘除
        if (TYPE_MATH_ONE.equals(type)) {
            // 生成文本
            String producerText = captchaProducerMathOne.createText();
            // 设置验证码字符
            captchaDomain.setText(producerText.substring(0, producerText.indexOf("@")));
            // 设置验证码答案码
            captchaDomain.setCode(producerText.substring(producerText.indexOf("@") + 1));
            // 设置验证码图片
            captchaDomain.setImage(captchaProducerMathOne.createImage(captchaDomain.getText()));
        }
        // 两位数加减乘除
        else if (TYPE_MATH_TWO.equals(type)) {
            String producerText = captchaProducerMathTwo.createText();
            captchaDomain.setText(producerText.substring(0, producerText.indexOf("@")));
            captchaDomain.setCode(producerText.substring(producerText.indexOf("@") + 1));
            captchaDomain.setImage(captchaProducerMathTwo.createImage(captchaDomain.getText()));
        }
        // 缺省情况：字符
        else {
            captchaDomain.setText(captchaProducer.createText());
            captchaDomain.setCode(captchaDomain.getText());
            captchaDomain.setImage(captchaProducer.createImage(captchaDomain.getText()));
        }
        // 生成base64
        try {
            // 定义字节数组输出流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 将图像以 jpg 的形式，写到字节数组输出流中
            ImageIO.write(captchaDomain.getImage(), "jpg", outputStream);
            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            // 写入base64格式
            captchaDomain.setBase64("data:image/jpg;base64," + encoder.encode(outputStream.toByteArray()));
            // 写入唯一Token
            captchaDomain.setToken(UUID.randomUUID().toString());
            // 返回结果
            return captchaDomain;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public CaptchaDomain createHutoolCaptcha(Integer width, Integer height) {
        CaptchaDomain captchaDomain = new CaptchaDomain();
        // 生成 0、1、2 三个整数其一，随机对应下面的三种验证码类型
        // 注意：这里只是尽可能多的展示每种验证码的使用方法，实际项目中选择一种即可。
        Random random = new SecureRandom();
        int type = random.nextInt(3);
        AbstractCaptcha captcha = null;
        // 【0】生成 线段干扰验证码
        if (type == 0) {
            //定义图形验证码的长和宽
            captcha = CaptchaUtil.createLineCaptcha(width, height);
        }
        // 【1】生成 圆圈干扰验证码
        else if (type == 1) {
            //定义图形验证码的长、宽。还可以设置两个参数：验证码字符数、干扰元素个数
            captcha = CaptchaUtil.createCircleCaptcha(width, height);
        }
        // 【2】生成 扭曲干扰验证码
        else {
            captcha = CaptchaUtil.createShearCaptcha(width, height);
        }
        // 信息配置
        captchaDomain.setText(captcha.getCode());
        captchaDomain.setCode(captcha.getCode());
        captchaDomain.setBase64(captcha.getImageBase64Data());
        captchaDomain.setImage(captcha.getImage());
        captchaDomain.setToken(UUID.randomUUID().toString());

        return captchaDomain;
    }

}
