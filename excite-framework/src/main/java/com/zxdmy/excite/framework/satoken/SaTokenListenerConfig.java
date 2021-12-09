package com.zxdmy.excite.framework.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 接口SaTokenListener是Sa-Token的全局侦听器，通过实现此接口，你可以在用户登陆、退出、被踢下线等关键性操作时进行一些AOP操作，比如保存登录/退出日志等信息。
 * 详情：https://sa-token.dev33.cn/doc/index.html#/up/global-listener
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-10-14 0014 17:22
 */
@Component
public class SaTokenListenerConfig implements SaTokenListener {

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, SaLoginModel loginModel) {
        System.out.println(loginType + loginId + loginModel);
        // TODO 登录日志保存至数据集
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        // ...
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        // ...
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        // ...
    }

    /**
     * 每次被封禁时触发
     */
    @Override
    public void doDisable(String loginType, Object loginId, long disableTime) {
        // ...
    }

    /**
     * 每次被解封时触发
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId) {
        // ...
    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
        // ...
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
        // ...
    }

}
