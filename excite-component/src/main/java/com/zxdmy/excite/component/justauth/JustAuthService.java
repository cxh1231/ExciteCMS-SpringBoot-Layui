package com.zxdmy.excite.component.justauth;

import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.zxdmy.excite.component.bo.JustAuthBO;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthBaiduScope;
import me.zhyd.oauth.enums.scope.AuthGiteeScope;
import me.zhyd.oauth.enums.scope.AuthWeiboScope;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthScopeUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2022/1/6 12:02
 */
@Service
@AllArgsConstructor
public class JustAuthService {

    IGlobalConfigService configService;

    private static final String DEFAULT_SERVICE = "justAuth";

    private static final Set<String> CONF_KEY_SET = new HashSet<String>() {{
        add("qq");
        add("weibo");
        add("gitee");
        add("wechat_open");
        add("wechat_mp");
        add("wechat_enterprise");
        add("baidu");
    }};


    public boolean saveConfig(JustAuthBO authBO) {
        if (CONF_KEY_SET.contains(authBO.getKey())) {
            if (null == authBO.getAppID() || null == authBO.getAppSecret() || null == authBO.getRedirectUri()) {

            }
            return false;
        } else {
            throw new ServiceException("当前输入的配置KEY[" + authBO.getKey() + "]不在许可范围内，请检查！");
        }
    }

    public JustAuthBO getConfig(String confKey) {
        JustAuthBO justAuthBO = new JustAuthBO();
        return (JustAuthBO) configService.get(DEFAULT_SERVICE, confKey, justAuthBO);
    }

    /**
     * @param confKey 配置的KEY，也即授权来源（如qq、weibo等）
     * @param confKey 配置的KEY
     * @param state   状态码（字符串）
     * @return 登录的链接
     */
    public String createAuthorizeUrl(String confKey, String state) {
        return this.createAuthRequest(confKey).authorize(state);
    }

    /**
     * 根据具体地授权来源，获取授权请求工具类
     *
     * @param confKey 配置的KEY，也即授权来源（如qq、weibo等）
     * @return
     */
    public AuthRequest createAuthRequest(String confKey) {
        JustAuthBO authBO = this.getConfig(confKey);
        AuthRequest authRequest = null;
        switch (confKey) {
            // QQ登录
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .build());
                break;
            // 微博登录
            case "weibo":
                authRequest = new AuthWeiboRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .scopes(Arrays.asList(
                                AuthWeiboScope.EMAIL.getScope(),
                                AuthWeiboScope.FRIENDSHIPS_GROUPS_READ.getScope(),
                                AuthWeiboScope.STATUSES_TO_ME_READ.getScope()
                        ))
                        .build());
                break;
            // Gitee登录
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .scopes(AuthScopeUtils.getScopes(AuthGiteeScope.values()))
                        .build());
                break;
            // 微信开放平台登录
            case "wechat_open":
                authRequest = new AuthWeChatOpenRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .build());
                break;
            // 微信公众号登录
            case "wechat_mp":
                authRequest = new AuthWeChatMpRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .build());
                break;
            // 微信企业号登录
            case "wechat_enterprise":
                authRequest = new AuthWeChatEnterpriseQrcodeRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .agentId("1000003")
                        .build());
                break;
            // 百度登录
            case "baidu":
                authRequest = new AuthBaiduRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .scopes(Arrays.asList(
                                AuthBaiduScope.BASIC.getScope(),
                                AuthBaiduScope.SUPER_MSG.getScope(),
                                AuthBaiduScope.NETDISK.getScope()
                        ))
                        .build());
                break;
            default:
                throw new ServiceException("配置的KEY不存在，请检查！");
        }
        return authRequest;
    }
}
