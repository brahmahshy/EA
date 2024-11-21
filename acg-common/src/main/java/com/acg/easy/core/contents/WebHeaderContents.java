package com.acg.easy.core.contents;

public interface WebHeaderContents {
    /**
     * 这个请求头是由代理服务器添加的，用于标识客户端的真实IP地址。
     * 如果客户端经过多个代理服务器，那么X-Forwarded-For中可能会包含多个IP地址，其中第一个IP地址通常是客户端的真实IP地址。
     */
    String X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * 这个请求头也是由代理服务器添加的，但是它只表示客户端的IP地址，而不关心客户端是否经过了多个代理服务器。
     * 因此，Proxy-Client-IP中获取到的IP地址可能不是真实的客户端IP地址。
     */
    String PROXY_CLIENT_IP = "Proxy-Client-IP";

    /**
     * 这个请求头是由WebLogic代理插件添加的，与Proxy-Client-IP类似，它只表示客户端的IP地址，而不关心客户端是否经过了多个代理服务器。
     * 因此，WL-Proxy-Client-IP中获取到的IP地址可能不是真实的客户端IP地址。
     */
    String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * 这个请求头是由Apache等HTTP服务器添加的，用于标识客户端的真实IP地址。
     * 如果客户端经过了多个代理服务器，那么HTTP_CLIENT_IP中可能会包含多个IP地址，其中第一个IP地址通常是客户端的真实IP地址。
     */
    String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

    /**
     * 这个请求头是由代理服务器添加的，用于标识客户端的真实IP地址。
     * 如果客户端经过了多个代理服务器，那么HTTP_X_FORWARDED_FOR中可能会包含多个IP地址，其中第一个IP地址通常是客户端的真实IP地址。
     */
    String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
}


