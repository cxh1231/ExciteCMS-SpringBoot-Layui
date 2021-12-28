package com.zxdmy.excite.admin.controller.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.system.entity.SysLogLogin;
import com.zxdmy.excite.system.entity.SysLogRequest;
import com.zxdmy.excite.system.entity.SysMenu;
import com.zxdmy.excite.system.service.ISysLogLoginService;
import com.zxdmy.excite.system.service.ISysLogRequestService;
import lombok.AllArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.zxdmy.excite.common.base.BaseController;

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

    ISysLogLoginService logLoginService;

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
     * 请求日志列表
     *
     * @return
     */
    @RequestMapping("request/detail/{id}")
    public String requestLogDetailPage(@PathVariable String id, ModelMap map) {
        try {
            SysLogRequest logRequest = logRequestService.getById(Integer.parseInt(id));
            if (null != logRequest) {
                map.put("logRequest", logRequest);
            } else {
                return "error/404";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "system/log/requestDetail";
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
    public BaseResult requestList(Integer page, Integer limit) {
        try {
            Page<SysLogRequest> rolePage = logRequestService.getPage(page, limit);
            return success("获取日志列表成功", rolePage.getRecords(), (int) rolePage.getTotal());
        } catch (Exception e) {
            return error(500, "error:" + e.getMessage());
        }
    }    /**
     * 获取日志列表
     *
     * @return 结果
     */
    @GetMapping("/loginList")
    @ResponseBody
    public BaseResult loginList(Integer page, Integer limit) {
        try {
            Page<SysLogLogin> rolePage = logLoginService.getPage(page, limit);
            return success("获取日志列表成功", rolePage.getRecords(), (int) rolePage.getTotal());
        } catch (Exception e) {
            return error(500, "error:" + e.getMessage());
        }
    }
}
