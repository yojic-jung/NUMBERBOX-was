package com.kamcci.modules.logging.engine.util

import com.kamcci.modules.logging.control.service.IPAddressService
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Service
class IPAddressUtil : IPAddressService {
    // 프록시 서버를 거쳤을 때: ProxyIPHeaderType를 순회하면서 헤더 값을 가져오고, 그중 첫 번째로 발견된 값을 반환
    // 프록시 서버를 거치지 않았을 때: 위의 값이 null이므로, ?: req.remoteAddr를 통해 클라이언트의 IP 주소를 반환
    override fun getIPAddress(): String {
        val attr = RequestContextHolder.getRequestAttributes()
        val request = (attr as ServletRequestAttributes).request
        return ProxyIPHeaderType.entries.asSequence()
            .mapNotNull { request.getHeader(it.attrName) }
            .firstOrNull()
            ?: request.remoteAddr
    }

    override fun getPublicIPAddress(): String {
        return getIPAddress().split(",").first().trim()
    }


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
}
