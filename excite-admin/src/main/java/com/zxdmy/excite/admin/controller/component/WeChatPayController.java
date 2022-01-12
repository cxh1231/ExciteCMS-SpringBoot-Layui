package com.zxdmy.excite.admin.controller.component;

import cn.hutool.core.util.IdUtil;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.component.wechat.WeChatPayService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Timer;

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
@RequestMapping("/component/wechatPay")
public class WeChatPayController extends BaseController {

    WeChatPayService weChatPayService;


    @RequestMapping("index")
    public String requestLogPage() {
        return "component/wechatPay/index";
    }

    @PostMapping(value = "/pay")
    @ResponseBody
    public BaseResult pay(String title, Float price) {
        System.out.println(title);
        System.out.println(price);
        int total = (int) (price * 100);
        String outTradeNo = IdUtil.simpleUUID();
        String result = weChatPayService.pay(null, "native", title, outTradeNo, total, null);

        return success(200, "获取支付二维码成功！")
                .put("qrcode", result)
                .put("outTradeNo", outTradeNo);
    }

    @PostMapping(value = "/refund")
    @ResponseBody
    public BaseResult refund(String outTradeNo, Float amount) {
        int refund = (int) (amount * 100);
        // 查询该订单的实际支付金额
        int total = weChatPayService.query(null, null, outTradeNo).getAmount().getTotal();
        if (refund > total) {
            return error("退款金额大于支付金额！");
        }
        WxPayRefundV3Result wxPayRefundV3Result = weChatPayService.refund(null, outTradeNo, IdUtil.simpleUUID(), total, refund);
        if ("SUCCESS".equals(wxPayRefundV3Result.getStatus())) {
            return success(200, "退款成功！");
        } else if ("PROCESSING".equals(wxPayRefundV3Result.getStatus())) {
            return success(200, "退款处理中...稍后到账，请注意查收微信通知！");
        }
        return error("退款失败！");
    }

    @PostMapping(value = "/query")
    @ResponseBody
    public BaseResult query(String outTradeNo) {
        WxPayOrderQueryV3Result wxPayOrderQueryV3Result = weChatPayService.query(null, null, outTradeNo);
        if (wxPayOrderQueryV3Result.getTradeState().equals("SUCCESS")) {
            return success(200, "支付成功！，交易成功时间：" + wxPayOrderQueryV3Result.getSuccessTime());
        } else if (wxPayOrderQueryV3Result.getTradeState().equals("NOTPAY")) {
            return error("未支付！");
        } else if (wxPayOrderQueryV3Result.getTradeState().equals("CLOSED")) {
            return error("订单已关闭！");
        } else if (wxPayOrderQueryV3Result.getTradeState().equals("USERPAYING")) {
            return success(200, "用户支付中（付款码支付）！");
        } else if (wxPayOrderQueryV3Result.getTradeState().equals("PAYERROR")) {
            return error("支付失败(其他原因，如银行返回失败)！");
        } else if (wxPayOrderQueryV3Result.getTradeState().equals("REFUND")) {
            return error("订单已退款！");
        } else
            return error(wxPayOrderQueryV3Result.getTradeState() + "：订单不存在");
    }

}
