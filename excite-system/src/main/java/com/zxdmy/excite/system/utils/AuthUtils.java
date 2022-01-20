package com.zxdmy.excite.system.utils;

import com.zxdmy.excite.common.config.ExciteConfig;
import com.zxdmy.excite.common.service.RedisService;
import com.zxdmy.excite.system.entity.SysMenu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * 授权工具类（权限清理工具）
 *
 * @author 拾年之璐
 * @since 2022/1/20 17:34
 */
@Component
@AllArgsConstructor
public class AuthUtils {

    private RedisService redisService1;

    private ExciteConfig exciteConfig1;

    private static RedisService redisService;

    private static ExciteConfig exciteConfig;

    @PostConstruct
    public void init() {
        redisService = redisService1;
        exciteConfig = exciteConfig1;
    }

    /**
     * 系统权限列表 默认 KEY
     */
    private static final String SYSTEM_MENUS_KEY = "menu:systemMenuList";

    /**
     * 用户权限列表 默认 KEY的 前缀
     */
    private static final String USER_MENU_KEY = "menu:userMenuList:";


    /**
     * 保存【系统权限列表】至缓存
     *
     * @param isSuccess 执行条件
     * @param menuList  菜单列表
     */
    public static void setSystemMenuListCache(boolean isSuccess, List<SysMenu> menuList) {
        if (exciteConfig.getAllowRedis() && isSuccess) {
            redisService.set(SYSTEM_MENUS_KEY, (Serializable) menuList);
        }
    }

    /**
     * 保存【用户权限列表】至缓存
     *
     * @param isSuccess 执行条件
     * @param userId    用户ID
     * @param menuList  权限列表
     */
    public static void setUserMenuListCache(boolean isSuccess, Integer userId, List<SysMenu> menuList) {
        if (exciteConfig.getAllowRedis() && isSuccess) {
            redisService.set(USER_MENU_KEY + userId, (Serializable) menuList, 7200L);
        }
    }

    /**
     * 从缓存读取【系统权限列表】
     *
     * @param isSuccess 执行条件
     * @return 结果：null | List<SysMenu>
     */
    public static List<SysMenu> getSystemMenuListCache(boolean isSuccess) {
        if (exciteConfig.getAllowRedis() && isSuccess) {
            return (List<SysMenu>) redisService.get(SYSTEM_MENUS_KEY);
        }
        return null;
    }

    /**
     * 从缓存读取【用户权限列表】
     *
     * @param isSuccess 执行条件
     * @param userId    用户ID
     * @return 结果：null | List<SysMenu>
     */
    public static List<SysMenu> getUserMenuListCache(boolean isSuccess, Integer userId) {
        if (exciteConfig.getAllowRedis() && isSuccess) {
            return (List<SysMenu>) redisService.get(USER_MENU_KEY + userId);
        }
        return null;
    }

    /**
     * 清除【系统权限列表】的缓存
     *
     * @param isSuccess 执行条件，为真则执行
     */
    public static void clearSystemMenuListCache(boolean isSuccess) {
        if (exciteConfig.getAllowRedis() && isSuccess) {
            redisService.remove(SYSTEM_MENUS_KEY);
        }
    }

    /**
     * 清除全部【用户权限列表】的缓存
     *
     * @param isSuccess 执行条件，为真则执行
     */
    public static void clearUserMenuListCache(boolean isSuccess) {
        if (exciteConfig.getAllowRedis() && isSuccess) {
            redisService.removeByPrefix(USER_MENU_KEY);
        }
    }

    /**
     * 清除指定【用户权限列表】的缓存
     *
     * @param isSuccess 执行条件，为真则执行
     * @param userId    要清除的用户ID
     */
    public static void clearUserMenuListCacheById(boolean isSuccess, Integer userId) {
        if (exciteConfig.getAllowRedis() && isSuccess) {
            redisService.remove(USER_MENU_KEY + userId);
        }
    }

    /**
     * 清除指定【用户权限列表】的缓存（批量删除）
     *
     * @param isSuccess 执行条件，为真则执行
     * @param userIds   要清除的用户ID列表
     */
    public static void clearUserMenuListCacheById(boolean isSuccess, Integer[] userIds) {
        if (exciteConfig.getAllowRedis() && isSuccess && userIds.length > 0) {
            for (int userId : userIds) {
                redisService.remove(USER_MENU_KEY + userId);
            }
        }
    }
}
