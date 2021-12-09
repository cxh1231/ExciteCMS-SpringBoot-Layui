package com.zxdmy.excite.admin.controller.system;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;

import com.zxdmy.excite.common.service.RedisService;
import com.zxdmy.excite.framework.aop.AnnotationSaveReLog;
import com.zxdmy.excite.system.entity.SysUser;
import com.zxdmy.excite.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 系统用户表 前端控制器
 *
 * @author 拾年之璐
 * @since 2021-09-01
 */
@Tag(name = "后台用户控制器")
@Controller
@AllArgsConstructor
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    private ISysUserService userService;

    private RedisService redisUtils;


    /**
     * 角色管理 列表页面
     *
     * @return 列表页面
     */
    @RequestMapping("index")
    public String index() {
        return "system/user/index";
    }

    /**
     * 用户登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @param captcha  验证码
     * @param remember 是否记住密码 1-记住 | 0-不记住（仅在非前后端分离模式下生效）
     * @return 登录结果
     */
    @PostMapping(value = "/login")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult login(String username, String password, String captcha, String remember) {
        // 如果提交的信息有空信息
        if (StrUtil.hasBlank(username, password, captcha)) {
            return error(400, "用户名、密码或验证码为空");
        }
        // 前后端同域，就用session
        HttpSession session = request.getSession();
        if (null != session.getAttribute("captcha") && session.getAttribute("captcha").toString().equals(captcha)) {
            // 使用用户名和密码登录
            SysUser user = userService.login(username, password);
            // 如果用户不为空，则登录成功
            if (null != user) {
                // 保持登录与否
                StpUtil.login(user.getId(), "1".equals(remember));
                // 获取Token等信息
                SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
                return success("登录成功！", tokenInfo);
            }
            // 登录失败
            else {
                return error(400, "登录失败：用户名或密码错误");
            }
        }
        // 验证码错误
        else {
            return error(400, "验证码已失效或验证码错误，请重试");
        }

    }

    /**
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public BaseResult getUserList(Integer page, Integer limit, String username, String account) {
        Page<SysUser> userPage = userService.getPage(page, limit, username, account);
        if (null != userPage) {
            return success("查询成功", userPage.getRecords(), (int) userPage.getTotal());
        } else {
            return error(400, "查询失败，用户不存在");
        }
    }


}
