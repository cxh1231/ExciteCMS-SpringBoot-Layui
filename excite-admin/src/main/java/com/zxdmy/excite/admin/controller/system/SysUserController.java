package com.zxdmy.excite.admin.controller.system;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.framework.service.RedisService;
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
     * @return
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
     * 根据ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Operation(summary = "获取用户信息接口",
            description = "根据ID查询用户信息",
            responses = {
                    @ApiResponse(responseCode = "200", description = "获取成功", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "获取失败", content = @Content(mediaType = "application/json"))},
            security = @SecurityRequirement(name = "需要认证"))
    @GetMapping(value = "/get/{id}")
    @ResponseBody
    public String getOne(@PathVariable String id) {
        SysUser user = userService.getById(id);
        if (null != user) {
            return user.toString();
        } else {
            return "用户信息不存在";
        }
    }


    @GetMapping(value = "/demo/get/{id}")
    @ResponseBody
    public BaseResult demoGetById(@PathVariable String id) {
        SysUser user = userService.getById(id);
        if (null != user) {
            return success("查询成功", user);
        } else {
            // 传入 HttpStatus.BAD_REQUEST，则修改HTTP请求的状态码，浏览器内按F12可查看。
            return error(HttpStatus.BAD_REQUEST, "查询失败，用户不存在");
            // 传入 数值，HTTP请求的状态码为200
            // return error(400, "查询失败，用户不存在");
        }
    }


    @GetMapping(value = "/demo/getAll")
    @ResponseBody
    public BaseResult demoGetAll() {
        List<SysUser> userList = userService.list();
        if (null != userList) {
            return success("查询成功", userList, userList.size());
        } else {
            return error("查询失败，用户列表为空");
        }
    }


}
