package com.zxdmy.excite.admin.controller.component;

import cn.hutool.core.util.IdUtil;
import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.component.alipay.AlipayService;
import lombok.AllArgsConstructor;
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
 * @since 2022/1/11 20:45
 */
@Controller
@AllArgsConstructor
@RequestMapping("/component/alipay")
public class AlipayController extends BaseController {

    AlipayService alipayService;

    @RequestMapping("index")
    public String index() {
        return "component/alipay/index";
    }


    @PostMapping(value = "/pay")
    @ResponseBody
    public BaseResult pay(String title, String price) {
        String outTradeNo = IdUtil.simpleUUID();
        String qrcode = alipayService.pay(null, "facetoface", title, outTradeNo, price, null, null);

        return success(200, "获取支付二维码成功！")
                .put("qrcode", qrcode)
                .put("outTradeNo", outTradeNo);
    }

    @GetMapping(value = "/page")
    @ResponseBody
    public String page(String title, String price,String returnUrl) {
        String outTradeNo = IdUtil.simpleUUID();
        String qrcode = alipayService.pay(null, "page", title, outTradeNo, price, returnUrl, null);
        return qrcode;
//        return success(200, "获取支付二维码成功！")
//                .put("qrcode", qrcode)
//                .put("outTradeNo", outTradeNo);
    }

    @PostMapping(value = "/refund")
    @ResponseBody
    public BaseResult refund(String outTradeNo, String amount) {
        String[] result = alipayService.refund(null, null, outTradeNo, amount, "用户取消退款");
        if ("Y".equals(result[0])) {
            return success(200, "退款成功，退款金额：" + result[4]);
        } else if ("N".equals(result[0])) {
            return success(200, "该订单早已退款成功！退款金额：" + result[4]);
        } else
            return error("退款错误：" + result[1] + result[2]);
    }

    @PostMapping(value = "/query")
    @ResponseBody
    public BaseResult query(String outTradeNo) {
        String[] result = alipayService.queryPay(null, null, outTradeNo);
        if ("Y".equals(result[0])) {
            return success(200, "查询成功！" +
                    " 支付宝交易号：" + result[1] +
                    " 交易状态：" + result[3] +
                    " 订单金额：" + result[4] +
                    " 买家账号：" + result[6]);
        }
        return error("查询失败：" + result[1] + result[2]);
    }

}
