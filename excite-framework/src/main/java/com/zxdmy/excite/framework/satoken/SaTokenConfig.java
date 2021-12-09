package com.zxdmy.excite.framework.satoken;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;

import com.zxdmy.excite.system.entity.SysMenu;
import com.zxdmy.excite.system.service.ISysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Sa-Token 配置文件
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-10-12 0012 19:16
 */
@Configuration
@AllArgsConstructor
public class SaTokenConfig implements WebMvcConfigurer {

    private ISysMenuService menuService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 方案1：注册启用【注解拦截器】
        registry.addInterceptor(new SaAnnotationInterceptor())
                // 添加：以system开头的路由都需要鉴权
                .addPathPatterns("/system/**")
                // 排除：登录请求路由
                .excludePathPatterns("/system/user/login")
                // 排除：登录页面路由
                .excludePathPatterns("/system/login");

        // 方案2：注册启用【路由拦截器】
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            // 请求的完整链接
            System.out.println(request.getUrl());

            // 可以在此处直接[批量]指定哪些路由需要拦截/排除[登录]
            // SaRouter.match(Arrays.asList("/system/**", ""), Arrays.asList("/system/login", "/system/user/login"), StpUtil::checkLogin);
            SaRouter.match(Arrays.asList("/system/**", "")).notMatch(Arrays.asList("/system/login", "/system/user/login")).check(StpUtil::checkLogin);

            // 可以在此处为[某个路由]指定需要的[权限]
            // SaRouter.match("/system/menu/get2", () -> StpUtil.checkPermission("system:menu:get2"));
            // SaRouter.match("/system/permission/get", () -> StpUtil.checkPermission("system:permission:get"));

            // 也可以从数据库中读取需要拦截的路由列表
            // getMenuList方法会访问很多次，建议使用Redis开启缓存
            List<SysMenu> menuList = menuService.getMenuListForSaToken();
            for (SysMenu menu : menuList) {
                // 如果权限标识符不为空，则添加至拦截器中
                if (null != menu.getPermission()) {
                    // 将访问路径Path，与对应的权限permission，添加至路由拦截器
                    SaRouter.match(menu.getPath(), () -> StpUtil.checkPermission(menu.getPermission()));
                }
            }

        }));

    }
}
