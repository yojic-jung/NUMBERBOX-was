package com.kamcci.modules.logging.engine.util

import com.kamcci.modules.logging.control.constant.ProxyIPHeaderType
import jakarta.servlet.http.HttpServletRequest

object IPAddressUtil {
    // 프록시 서버를 거쳤을 때: ProxyIPHeaderType를 순회하면서 헤더 값을 가져오고, 그중 첫 번째로 발견된 값을 반환
    // 프록시 서버를 거치지 않았을 때: 위의 값이 null이므로, ?: req.remoteAddr를 통해 클라이언트의 IP 주소를 반환
    fun getIPAddress(req: HttpServletRequest): String {
        return ProxyIPHeaderType.entries.asSequence()
            .mapNotNull { req.getHeader(it.attrName) }
            .firstOrNull()
            ?: req.remoteAddr
    }

    fun getPublicIPAddress(req: HttpServletRequest) = getIPAddress(req).split(",").first().trim()
}
