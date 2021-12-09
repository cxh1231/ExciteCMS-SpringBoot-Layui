package com.zxdmy.excite.common.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 全局错误访问返回结果
 *
 * @author 拾年之璐
 * @since 2021-09-08 0008 21:00
 */
@Component
public class ErrorPageConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
        errorPageRegistry.addErrorPages(
                // 当遇到 NOT_FOUND 错误时，请求 /error/404 路由。
                new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"),
                // 当遇到 INTERNAL_SERVER_ERROR 错误时，请求 /error/500 路由。
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500")
        );
    }
}
