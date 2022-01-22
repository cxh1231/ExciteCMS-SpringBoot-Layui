package com.zxdmy.excite.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2021/12/9 16:54
 */
public class HttpServletRequestUtil {

    /**
     * 获取request对象
     *
     * @return request对象
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取请求的URI（非URL）
     *
     * @return 格式如：/system/user/list?size=10&page=2
     */
    public static String getRequestURI() {
        HttpServletRequest request = getRequest();
        // URI中的查询字符串
        String queryString = request.getQueryString();
        // 请求URL中不含字符串
        if (!StringUtils.hasText(queryString)) {
            // 只返回URI
            return request.getRequestURI();
        }
        // 否则返回包含查询的字符串
        return request.getRequestURI() + "?" + queryString;
    }

    /**
     * 获取浏览器的UA
     *
     * @return UA对象（Hutool工具箱中的）
     */
    public static UserAgent getRequestUserAgent() {
        HttpServletRequest request = getRequest();
        return UserAgentUtil.parse(request.getHeader("User-Agent"));
    }

    /**
     * 获取请求用户的IP
     *
     * @return IP字符串
     */
    public static String getRemoteIP() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多次反向代理后会有多个IP值，第一个为真实IP。
        int index = ip.indexOf(',');
        if (index != -1) {
            ip = ip.substring(0, index);
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 取得请求头信息 name:value
     */
    public static Map getHeaders() {
        HttpServletRequest request = getRequest();
        Map<String, String> map = new HashMap<>(32);
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取请求体信息
     */
    public static String getBody() {
        HttpServletRequest request = getRequest();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            return StreamUtils.copyToString(inputStream, Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
        }
        return StrUtil.EMPTY;
    }

    public static String getAllRequestInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("请求详情为：").append(StrUtil.CRLF);
        sb.append("RemoteAddress: ").append(getRemoteIP()).append(StrUtil.CRLF);
        sb.append("Method: ").append(getRequest().getMethod()).append(StrUtil.CRLF);
        sb.append("URI: ").append(getRequestURI()).append(StrUtil.CRLF);
        sb.append("Headers: ").append(StrUtil.join(StrUtil.CRLF + "         ", mapToList(getHeaders()))).append(StrUtil.CRLF);
        sb.append("Body: ").append(getBody()).append(StrUtil.CRLF);
        return sb.toString();
    }

    private static List mapToList(Map parameters) {
        List parametersList = new ArrayList();
        parameters.forEach((name, value) -> {
            parametersList.add(name + "=" + value);
        });
        return parametersList;
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
        return "未知";
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
        return "127.0.0.1";
    }
}
