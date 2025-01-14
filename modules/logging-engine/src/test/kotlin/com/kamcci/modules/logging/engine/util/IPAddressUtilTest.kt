package com.kamcci.modules.logging.engine.util

import com.kamcci.modules.logging.control.constant.ProxyIPHeaderType
import com.kamcci.modules.logging.engine.util.IPAddressUtil.getIPAddress
import com.kamcci.modules.logging.engine.util.IPAddressUtil.getPublicIPAddress
import jakarta.servlet.http.HttpServletRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class IPAddressUtilTest {
    @Test
    fun `ip 헤더에서 추출 - 성공`() {
        // given
        val expectedIP = "203.0.113.195"
        val req = mock(HttpServletRequest::class.java)
        `when`(req.getHeader(ProxyIPHeaderType.XForwardedFor.attrName)).thenReturn(expectedIP)

        // when
        val actualIP = getIPAddress(req)

        // then
        assertThat(actualIP).isEqualTo(expectedIP)
    }

    @Test
    fun `ip remoteAddr에서 추출 - 성공`() {
        // given
        val expectedIP = "192.168.0.1"
        val req = mock(HttpServletRequest::class.java)
        `when`(req.getHeader(ProxyIPHeaderType.XForwardedFor.attrName)).thenReturn(null)
        `when`(req.remoteAddr).thenReturn(expectedIP)

        // when
        val actualIP = getIPAddress(req)

        // then
        assertThat(actualIP).isEqualTo(expectedIP)
    }

    @Test
    fun `public IP 추출 - 성공`() {
        // given
        val publicIP = "203.0.113.195"
        val proxyIP = "192.168.0.1"
        val req = mock(HttpServletRequest::class.java)
        `when`(req.getHeader(ProxyIPHeaderType.XForwardedFor.attrName)).thenReturn("$publicIP, $proxyIP")

        // when
        val actualIP = getPublicIPAddress(req)

        // then
        assertThat(actualIP).isEqualTo(publicIP)
    }
}