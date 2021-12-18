package com.zxdmy.excite.admin.controller.system;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;

import com.zxdmy.excite.common.enums.ReturnCode;
import com.zxdmy.excite.common.enums.SystemCode;
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
     * 用户管理 - 列表页面
     *
     * @return 跳转至列表页面
     */
    @RequestMapping("index")
    public String index() {
        return "system/user/index";
    }

    /**
     * 用户管理 - 添加用户页面
     *
     * @return 跳转至添加用户页面
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        return "system/user/add";
    }

    /**
     * 用户管理 - 编辑用户信息页面
     *
     * @return 跳转至编辑用户页面
     */
    @RequestMapping("goEdit")
    public String goEdit() {
        return "system/user/edit";
    }

    /**
     * 用户管理 - 编辑用户信息页面
     *
     * @return 跳转至编辑用户页面
     */
    @RequestMapping("goSetRole/{id}")
    public String goSetRole(@PathVariable String id) {
        return "system/user/setRole";
    }


    /**
     * 接口功能：用户登录
     * 基本逻辑：校验前端信息-->校验验证码-->校验用户名、密码-->登录
     *
     * @param username 用户名
     * @param password 密码
     * @param captcha  验证码
     * @param remember 是否记住登录状态 1-记住 | 0-不记住（记住登录状态后，关闭浏览器，不退出。不记住登录状态，关闭浏览器后即退出登录。只有手动退出才可以）
     * @return JSON：登录结果
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public BaseResult login(String username, String password, String captcha, String remember) {
        // 如果提交的信息有空信息
        if (StrUtil.hasBlank(username, password, captcha)) {
            return error("用户名、密码或验证码不能为空！");
        }
        // 前后端同域，就用session
        HttpSession session = request.getSession();
        // 如果验证码session不为空，表示以获取验证码
        if (null != session.getAttribute("captcha")) {
            // 验证码正确
            if (session.getAttribute("captcha").toString().equals(captcha)) {
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
                    return error("登录失败：用户名或密码错误");
                }
            }
            // 验证码错误
            else {
                return error("验证码错误，请重新输入！");
            }
        }
        // 未获取验证码：提示重新获取
        else {
            return error("验证码已失效，请重新获取！");
        }
    }

    /**
     * 接口功能：退出登录
     *
     * @return JSON：退出登录结果
     */
    @PostMapping(value = "/logout")
    @ResponseBody
    public BaseResult logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
            return success("已退出登录！");
        }
        return error("请求失败，未登录任何账户");
    }

    /**
     * 接口功能：获取用户列表
     *
     * @param page     当前页面
     * @param limit    每页请求数
     * @param username 检索用户名
     * @param account  检索电话或邮箱
     * @return JSON：用户列表 | 错误信息
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public BaseResult getUserList(Integer page, Integer limit, String username, String account) {
        // TODO 加入功能：当前是否在线
        Page<SysUser> userPage = userService.getPage(page, limit, username, account);
        if (null != userPage) {
            return success("查询成功", userPage.getRecords(), (int) userPage.getTotal());
        } else {
            return error("查询失败，用户不存在");
        }
    }

    /**
     * 接口功能：添加用户
     * 基本逻辑：接收前端的用户信息-->校验与初始化新用户信息-->添加至[用户表]-->获取新添加用户的ID-->用户ID与角色ID添加至[用户角色关联表].
     *
     * @param user    用户信息
     * @param roleIds 用户分配的角色信息
     * @return JSON：成败结果
     * @apiNote 启用注解[@AnnotationSaveReLog]保存请求日志
     */
    @PostMapping(value = "/add")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult add(SysUser user, Integer[] roleIds) {
        // 新建用户：其邮箱或手机号、密码不能为空，因为这是登录凭证.
        if (null != user && null != user.getPassword() && (null != user.getEmail() || null != user.getPhone())) {
            if (userService.save(user, roleIds) > 0) {
                return success("新用户添加成功！");
            } else {
                return error("新用户添加失败，请重试！");
            }
        }
        return error("用户信息（邮箱或手机号、密码）不能为空，请重试！");
    }

    /**
     * 接口功能：更新用户基本信息
     *
     * @param user 用户信息
     * @return JSON：成败结果
     * @apiNote 启用注解[@AnnotationSaveReLog]保存请求日志
     */
    @PostMapping(value = "/update")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult update(SysUser user) {
        // 编辑用户基本信息：用户名、头像、性别、邮箱和手机号。
        if (null != user && null != user.getId()) {
            // 其他信息（状态、密码）不能通过此接口修改，故其为null.
            if (null == user.getStatus() && null == user.getPassword()) {
                if (userService.save(user, null) > 0) {
                    return success("用户信息编辑成功！");
                } else {
                    return error("用户信息编辑失败，请重试！");
                }
            } else {
                return error("接口请求信息错误！");
            }
        } else {
            return error("用户信息不能为空，请重试！");
        }
    }

    /**
     * 接口功能：删除用户
     * 基本逻辑：用户踢下线-->删除该用户的角色信息[用户-角色关联表]-->删除用户
     *
     * @param id 用户ID
     * @return JSON：成败
     * @apiNote 启用注解[@AnnotationSaveReLog]保存请求日志
     */
    @PostMapping(value = "/delete/{id}")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult delete(@PathVariable String id) {
        // TODO 删除用户的同时，从用户-角色关联表中删除相关信息，同时把该用户踢下线，清除Redis缓存
        StpUtil.kickout(id);
        
        if (userService.removeById(id)) {
            return success("用户删除成功");
        }
        return error("添加失败，请重试！");
    }

    @PostMapping(value = "/setRole")
    @ResponseBody
    public BaseResult setRole() {
        return error("添加失败，请重试！");
    }

    @PostMapping(value = "/changeStatus/{status}")
    @ResponseBody
    public BaseResult changeStatus(@PathVariable String status) {
        return error("添加失败，请重试！");
    }

    @PostMapping(value = "/resetPassword")
    @ResponseBody
    public BaseResult resetPassword(String userId, String newPassword, String newPassword2) {
        return error("添加失败，请重试！");
    }
}
