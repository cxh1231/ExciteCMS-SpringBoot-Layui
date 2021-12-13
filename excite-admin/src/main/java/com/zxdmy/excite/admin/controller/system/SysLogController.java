package com.zxdmy.excite.admin.controller.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.system.entity.SysLogRequest;
import com.zxdmy.excite.system.service.ISysLogRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.zxdmy.excite.common.base.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-12-09
 */
@Controller
@AllArgsConstructor
@RequestMapping("/system/log")
public class SysLogController extends BaseController {

    ISysLogRequestService logRequestService;

    /**
     * 请求日志列表
     *
     * @return
     */
    @RequestMapping("request")
    public String requestLogPage() {
        return "system/log/request";
    }

    /**
     * 登录日志列表
     *
     * @return
     */
    @RequestMapping("login")
    public String loginLogPage() {
        return "system/log/login";
    }

    /**
     * 获取日志列表
     *
     * @return 结果
     */
    @GetMapping("/requestList")
    @ResponseBody
    public BaseResult addRole(Integer page, Integer limit) {
        try {
            Page<SysLogRequest> rolePage = logRequestService.getPage(page, limit);
            return success("获取日志列表成功", rolePage.getRecords(), (int) rolePage.getTotal());
        } catch (Exception e) {
            return error(500, "error:" + e.getMessage());
        }
    }
}
