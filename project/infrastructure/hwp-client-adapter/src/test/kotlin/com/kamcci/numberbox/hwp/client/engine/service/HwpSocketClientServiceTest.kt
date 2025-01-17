package com.kamcci.numberbox.hwp.client.engine.service

import com.kamcci.numberbox.app.domain.dto.hwp.HwpExtensionType
import com.kamcci.numberbox.hwp.client.engine.dummy.MockSocketFactory.getErrorSocketFactory
import com.kamcci.numberbox.hwp.client.engine.dummy.MockSocketFactory.getSocketFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HwpSocketClientServiceTest {
    companion object {
        const val RESPONSE_DATA = "TEST"
    }

    private val socketFactory: SocketFactory = getSocketFactory(RESPONSE_DATA)

    private val socketErrFactory: SocketFactory = getErrorSocketFactory(RESPONSE_DATA)

    @Test
    fun `jsonMsg to hwp 파일 변환 요청 - 성공`() {
        // given
        val hwpSocketClientService = HwpSocketClientService(socketFactory)
        val jsonMsg = "{\"key\":\"val\"}"

        // when
        val actualRes = hwpSocketClientService.requestHwpFile(jsonMsg)

        // then
        assertEquals(RESPONSE_DATA, actualRes.toString(Charsets.UTF_8))
    }

    @Test
    fun `jsonMsg to hwp 파일 변환 요청 - 실패`() {
        // given
        val hwpSocketClientService = HwpSocketClientService(socketErrFactory)
        val jsonMsg = "{\"key\":\"val\"}"

        // when & then
        assertThrows<RuntimeException> {
            hwpSocketClientService.requestHwpFile(jsonMsg)
        }
    }


    @Test
    fun `hwp to html zip 파일 변환 요청 - 성공`() {
        // given
        val hwpSocketClientService = HwpSocketClientService(socketFactory)
        val jsonMsg = "{\"key\":\"val\"}".byteInputStream()

        // when
        val actualRes = hwpSocketClientService.requestHtmlZip(
            jsonMsg,
            RESPONSE_DATA.toByteArray().size,
            HwpExtensionType.Hwp
        )

        // then
        assertEquals(RESPONSE_DATA, actualRes.toString(Charsets.UTF_8))
    }


    @Test
    fun `hwp to html zip 파일 변환 요청 - 실패`() {
        // given
        val hwpSocketClientService = HwpSocketClientService(socketErrFactory)
        val jsonMsg = "{\"key\":\"val\"}".byteInputStream()

        // when & then
        assertThrows<RuntimeException> {
            hwpSocketClientService.requestHtmlZip(
                jsonMsg,
                RESPONSE_DATA.toByteArray().size,
                HwpExtensionType.Hwp
            )
        }
    }
}

