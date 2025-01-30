package com.kamcci.modules.logging.engine.util

import com.kamcci.modules.logging.engine.util.IPAddressUtil.ProxyIPHeaderType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class IPAddressUtilTest {
    private val ipAddressUtil = IPAddressUtil()

    @Test
    fun `ip 헤더에서 추출 - 성공`() {
        // given
        val expectedIP = "203.0.113.195"
        val req = MockHttpServletRequest()
        val reqAttr = ServletRequestAttributes(req)
        RequestContextHolder.setRequestAttributes(reqAttr)
        req.addHeader(ProxyIPHeaderType.XForwardedFor.attrName, expectedIP)

        // when
        val actualIP = ipAddressUtil.getIPAddress()

        // then
        assertThat(actualIP).isEqualTo(expectedIP)
    }

    @Test
    fun `ip remoteAddr에서 추출 - 성공`() {
        // given
        val expectedIP = "192.168.0.1"
        val req = MockHttpServletRequest()
        val reqAttr = ServletRequestAttributes(req)
        RequestContextHolder.setRequestAttributes(reqAttr)
        req.remoteAddr = expectedIP

        // when
        val actualIP = ipAddressUtil.getIPAddress()

        // then
        assertThat(actualIP).isEqualTo(expectedIP)
    }

    @Test
    fun `public IP 추출 - 성공`() {
        // given
        val publicIP = "203.0.113.195"
        val proxyIP = "192.168.0.1"
        val req = MockHttpServletRequest()
        val reqAttr = ServletRequestAttributes(req)
        RequestContextHolder.setRequestAttributes(reqAttr)
        req.addHeader(ProxyIPHeaderType.XForwardedFor.attrName, "$publicIP, $proxyIP")

        // when
        val actualIP = ipAddressUtil.getPublicIPAddress()

        // then
        assertThat(actualIP).isEqualTo(publicIP)
    }
}