package com.zxdmy.excite.common.utils;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 验证码随机文本生成器之：两位数的加减乘除
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-10-04 0004 20:26
 */
public class KaptchaMathTwoTextCreator extends DefaultTextCreator {

    @Override
    public String getText() {
//        Random random = new SecureRandom();
        SecureRandom random = new SecureRandom();
        // 保存计算结果
        Map<String, String> result = MyCaptchaUtil.mathTextCreator(random.nextInt(100), random.nextInt(100));
        // 生成两个随机数，随机数范围：[0,100)，并返回结果
        return result.get("resultString");
    }
}
