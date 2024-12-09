package com.easyacg.core.util;

import cn.hutool.core.util.StrUtil;
import com.easyacg.core.contents.WebHeaderContents;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

@Slf4j
@UtilityClass
public class IpUtil {
    /**
     * 未知 ip
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 根据 Request Header 获取对应的 ip 地址
     *
     * @param request web请求
     * @return ip地址
     */
    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader(WebHeaderContents.X_FORWARDED_FOR);
        if (isIpInvalid(ip)) {
            ip = request.getHeader(WebHeaderContents.PROXY_CLIENT_IP);
        }
        if (isIpInvalid(ip)) {
            ip = request.getHeader(WebHeaderContents.HTTP_CLIENT_IP);
        }
        if (isIpInvalid(ip)) {
            ip = request.getHeader(WebHeaderContents.HTTP_X_FORWARDED_FOR);
        }
        if (isIpInvalid(ip)) {
            ip = request.getHeader(WebHeaderContents.WL_PROXY_CLIENT_IP);
        }
        if (isIpInvalid(ip)) {
            // 这个方法返回的是客户端的IP地址，但是它不一定代表客户端的真实IP地址。
            // 如果客户端经过了代理服务器，那么这个方法返回的IP地址可能是代理服务器的IP地址而不是客户端的真实IP地址。
            ip = request.getRemoteAddr();
            if (isLocalhost(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    log.error("IpUtil#getIp has exception.", e);
                }
                ip = inet == null ? UNKNOWN : inet.getHostAddress();
            }
        }
        return normalize(ip);
    }

    /**
     * 判断 ip 是否无效
     *
     * @param ip 访问地址
     * @return ip为空 || ip未知
     */
    public boolean isIpInvalid(String ip) {
        return StrUtil.isBlankIfStr(ip) || UNKNOWN.equalsIgnoreCase(ip);
    }

    /**
     * 判断 ip 是否为本地调用
     *
     * @param ip ip地址
     * @return 是否本地调用
     */
    public boolean isLocalhost(String ip) {
        return "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip);
    }

    /**
     * 归一化 ip
     *
     * @param ip 访问地址
     * @return ip
     */
    public String normalize(String ip) {
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip == null ? UNKNOWN : StrUtil.normalize(ip);
    }
}
