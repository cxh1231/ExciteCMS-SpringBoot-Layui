package com.zxdmy.excite.base;

import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局请求错误返回结果控制类
 *
 * @author 拾年之璐
 * @since 2021-09-11 0011 23:27
 */
@Controller
@AllArgsConstructor
@RequestMapping("/error")
public class ErrorController extends BaseController {

    /**
     * 请求资源不存在返回结果
     *
     * @return 错误信息
     */
    @GetMapping(value = "/404")
    @ResponseBody
    public BaseResult error404() {
        return error(HttpStatus.NOT_FOUND, "请求的资源不存在");
    }

    /**
     * 服务器错误返回结果
     *
     * @return 错误信息
     */
    @GetMapping(value = "/500")
    @ResponseBody
    public BaseResult error500() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部错误");
    }
}
