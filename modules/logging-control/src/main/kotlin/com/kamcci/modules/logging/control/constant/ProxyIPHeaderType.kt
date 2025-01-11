package com.kamcci.modules.logging.control.constant

/**
 * 프록시 서버에서 사용하는 ip 속성 헤더 유형
 */
enum class ProxyIPHeaderType(val attrName: String) {
    XForwardedFor("X-Forwarded-For"),
    ProxyClientIP("Proxy-Client-IP"),
    WLProxyClientIP("WL-Proxy-Client-IP"),
    HttpClientIP("HTTP_CLIENT_IP"),
    HttpXForwardedFor("HTTP_X_FORWARDED_FOR"),
    HttpXForwarded("HTTP_X_FORWARDED"),
    HttpXClusterClientIP("HTTP_X_CLUSTER_CLIENT_IP"),
    HttpForwardedFor("HTTP_FORWARDED_FOR"),
    HttpForwarded("HTTP_FORWARDED"),
    HttpVia("HTTP_VIA"),
    RemoteAddr("REMOTE_ADDR"),
}
