package com.zxdmy.excite.admin.controller.advanced;

import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.framework.oshi.Server;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/server")
    @ResponseBody
    public BaseResult serverMonitor() throws Exception {
        Server server = new Server();
        server.copyTo();
        return success(server);
    }
}
