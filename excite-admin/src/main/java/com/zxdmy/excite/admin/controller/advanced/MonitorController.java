package com.zxdmy.excite.admin.controller.advanced;

import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.framework.oshi.Server;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2022/1/22 21:18
 */
@Controller
@RequestMapping("/system/monitor")
public class MonitorController extends BaseController {


    @RequestMapping("server")
    public String goServerMonitor() {
        return "system/advanced/monitorServer";
    }

    @PostMapping("/server")
    @ResponseBody
    public BaseResult serverMonitor() {
        Server server = new Server();
        try {
            server.copyTo();
            return success("获取服务器信息成功！", server);
        } catch (Exception e) {
            return error("获取服务器信息失败！");
        }
    }
}
