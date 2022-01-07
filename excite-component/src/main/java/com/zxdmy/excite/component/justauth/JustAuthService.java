package com.zxdmy.excite.component.justauth;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxdmy.excite.common.exception.ServiceException;
import com.zxdmy.excite.common.service.IGlobalConfigService;
import com.zxdmy.excite.component.bo.JustAuthBO;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthGiteeScope;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthScopeUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 第三方登录服务接口
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
        add("wechat_open");
        add("wechat_mp");
        add("wechat_enterprise");
        add("baidu");
        add("dingtalk");
        add("aliyun");
        add("alipay");
        add("huawei");
        add("feishu");
        add("gitee");
        add("github");
    }};


    /**
     * 保存配置信息
     *
     * @param authBO 配置信息类
     * @return 保存结果
     */
    public boolean saveConfig(JustAuthBO authBO) throws JsonProcessingException {
        // 配置信息的key必须存在于当前的库中
        if (null != authBO.getKey() && CONF_KEY_SET.contains(authBO.getKey())) {
            // 三大必填信息不能为空
            if (null == authBO.getAppID() || null == authBO.getAppSecret() || null == authBO.getRedirectUri()) {
                throw new ServiceException("APPID、APPSecret和RedirectUri不能为空！请检查！");
            } else {
                return configService.save(DEFAULT_SERVICE, authBO.getKey(), authBO, true);
            }
        } else {
            throw new ServiceException("当前输入的配置KEY[" + authBO.getKey() + "]不在许可范围内，请检查！");
        }
    }

    /**
     * 根据配置的KEY获取一条配置信息
     *
     * @param confKey 配置的KEY
     * @return 指定KEY的配置信息
     */
    public JustAuthBO getConfig(String confKey) {
        JustAuthBO justAuthBO = new JustAuthBO();
        return (JustAuthBO) configService.get(DEFAULT_SERVICE, confKey, justAuthBO);
    }

    /**
     * 获取所有的登录配置信息
     *
     * @param desensitized 是否脱敏
     * @return 配置列表
     */
    public List<JustAuthBO> getConfigList(Boolean desensitized) {
        JustAuthBO justAuthBO = new JustAuthBO();
        List<Object> objectList = configService.getList(DEFAULT_SERVICE, justAuthBO);
        ObjectMapper objectMapper = new ObjectMapper();
        List<JustAuthBO> justAuthBOList = new ArrayList<>();
        for (Object o : objectList) {
            justAuthBO = objectMapper.convertValue(o, justAuthBO.getClass());
            if (desensitized) {
                justAuthBO.setAppSecret(DesensitizedUtil.idCardNum(justAuthBO.getAppSecret(), 2, 2));
                justAuthBO.setPublicKey(DesensitizedUtil.idCardNum(justAuthBO.getPublicKey(), 2, 2));
            }
            justAuthBOList.add(justAuthBO);
        }
        return justAuthBOList;
    }

    /**
     * 生成登录授权的URL
     *
     * @param confKey 配置的KEY，也即授权来源（如qq、weibo等）
     * @param state   状态码（字符串）
     * @return 登录的链接
     */
    public String createAuthorizeUrl(String confKey, String state) {
        return this.createAuthRequest(confKey).authorize(state);
    }

    /**
     * 根据回调信息获取登录用户的信息
     *
     * @param confKey  配置的KEY，也即授权来源（如qq、weibo等）
     * @param callback 接口回调信息
     * @return 授权成功后的用户信息，根据授权平台的不同，获取的数据完整性也不同
     */
    public AuthUser getAuthUser(String confKey, AuthCallback callback) {
        AuthRequest authRequest = this.createAuthRequest(confKey);
        // 根据返回的参数，执行登录请求（获取用户信息）
        AuthResponse<AuthUser> response = authRequest.login(callback);
        // 判断结果
        if (response.ok()) {
            return response.getData();
        } else {
            return null;
        }
    }

    /**
     * 根据具体地授权来源，获取授权请求工具类
     *
     * @param confKey 配置的KEY，也即授权来源（如qq、weibo等）
     * @return 授权工具类
     */
    public AuthRequest createAuthRequest(String confKey) {
        JustAuthBO authBO = this.getConfig(confKey);
        AuthRequest authRequest;
        switch (confKey) {
            // QQ登录
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .unionId(authBO.getUnionId())
                        .build());
                break;
            // 微博登录
            case "weibo":
                authRequest = new AuthWeiboRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
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
                        .agentId(authBO.getAgentId())
                        .build());
                break;
            // 百度登录
            case "baidu":
                authRequest = new AuthBaiduRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .build());
                break;
            // 钉钉登录
            case "dingtalk":
                authRequest = new AuthDingTalkRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .build());
                break;
            // 阿里云登录
            case "aliyun":
                authRequest = new AuthAliyunRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .alipayPublicKey(authBO.getPublicKey())
                        .build());
                break;
            // 支付宝登录
            case "alipay":
                authRequest = new AuthAlipayRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .alipayPublicKey(authBO.getPublicKey())
                        .build());
                break;
            // 华为登录
            case "huawei":
                authRequest = new AuthHuaweiRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .build());
                break;
            // 飞书登录
            case "feishu":
                authRequest = new AuthFeishuRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
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
            // GitHub登录
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId(authBO.getAppID())
                        .clientSecret(authBO.getAppSecret())
                        .redirectUri(authBO.getRedirectUri())
                        .build());
                break;
            default:
                throw new ServiceException("配置的KEY不存在，请检查！");
        }
        return authRequest;
    }

    /**
     * 读取当前auth服务支持的KEY的集合
     *
     * @return 支持的KEY的集合
     */
    public Set<String> getConfKeySet() {
        return CONF_KEY_SET;
    }
}
