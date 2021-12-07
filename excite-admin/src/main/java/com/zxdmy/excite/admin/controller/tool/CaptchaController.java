package com.zxdmy.excite.admin.controller.tool;


import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;


import com.zxdmy.excite.common.entity.CaptchaDomain;
import com.zxdmy.excite.common.service.CaptchaService;
import com.zxdmy.excite.common.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * <p>
 * 生成验证码接口
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-10-04 0004 21:16
 */
@Controller
@AllArgsConstructor
@RequestMapping("/tool/captcha")
public class CaptchaController extends BaseController {

    private CaptchaService captchaService;

    private RedisService redisUtils;

    /**
     * 获取验证码接口
     *
     * @param type 类型，char - 字符(默认) | math - 一位数算式 | math2 - 两位数算式
     * @return 结果
     */
    @GetMapping("/get")
    @ResponseBody
    public BaseResult getCaptcha(@RequestParam(value = "type", required = false, defaultValue = "char") String type) {
        // 生成验证码实体
        CaptchaDomain captchaDomain = captchaService.createGoogleCaptcha(type);
        if (null != captchaDomain) {
            // 将验证码保存至redis，包含前缀
            redisUtils.set(captchaDomain.getToken(), captchaDomain.getCode(), 300L);
            // 无用信息设空
            captchaDomain.setText(null);
            captchaDomain.setCode(null);
            // 返回前端信息
            return success("获取验证码成功", captchaDomain);
        } else {
            return error(400, "获取验证码失败，请重试。");
        }

    }

    /**
     * 直接输出图片
     *
     * @param type 类型，char - 字符(默认) | math - 一位数算式 | math2 - 两位数算式
     */
    @GetMapping("/get/image")
    public void getCaptchaImage(@RequestParam(value = "type", required = false, defaultValue = "char") String type) {
        ServletOutputStream out = null;
        Random random = new SecureRandom();
        int rand = random.nextInt(2);
        CaptchaDomain captchaDomain = null;
        if (rand == 0) {
            // 生成验证码实体
            captchaDomain = captchaService.createGoogleCaptcha(type);
        } else {
            captchaDomain = captchaService.createHutoolCaptcha(160, 60);
        }
        // 将验证码保存至session
        HttpSession session = request.getSession();
        session.setAttribute("captcha",captchaDomain.getCode());
        // 输出图片
        try {
            response.setContentType("image/jpeg");
            out = response.getOutputStream();
            ImageIO.write(captchaDomain.getImage(), "jpg", out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
