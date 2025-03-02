package com.kamcci.numberbox.hwp.client.adapter.service

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import com.kamcci.numberbox.hwp.client.adapter.mock.MockSocketFactory.getErrorSocketFactory
import com.kamcci.numberbox.hwp.client.adapter.mock.MockSocketFactory.getSocketFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HwpSocketClientServiceTest {
    companion object {
        const val RESPONSE_DATA = "TEST"
    }

    // 테스트 대상 설정
    private val socketFactory: SocketFactory = getSocketFactory(RESPONSE_DATA)
    private val hwpSocketClientService = HwpSocketClientService(socketFactory)

    // 테스트 대상 설정 - 에러 케이스
    private val socketErrFactory: SocketFactory = getErrorSocketFactory()
    private val hwpSocketClientServiceErrCase = HwpSocketClientService(socketErrFactory)

    @Test
    fun `jsonMsg to hwp 파일 변환 요청 - 성공`() {
        // given
        val jsonMsg = "{\"key\":\"val\"}"

        // when
        val actualRes = hwpSocketClientService.requestHwpFile(jsonMsg)

        // then
        assertEquals(RESPONSE_DATA, actualRes.toString(Charsets.UTF_8))
    }

    @Test
    fun `jsonMsg to hwp 파일 변환 요청 - 실패`() {
        // given
        val jsonMsg = "{\"key\":\"val\"}"

        // when & then
        assertThrows<RuntimeException> {
            hwpSocketClientServiceErrCase.requestHwpFile(jsonMsg)
        }
    }


    @Test
    fun `hwp to html zip 파일 변환 요청 - 성공`() {
        // given
        val jsonMsg = "{\"key\":\"val\"}".byteInputStream()

        // when
        val actualRes = hwpSocketClientService.requestHtmlZip(
            jsonMsg,
            RESPONSE_DATA.toByteArray().size,
            HwpExtensionType.HWP
        )

        // then
        assertEquals(RESPONSE_DATA, actualRes.toString(Charsets.UTF_8))
    }


    @Test
    fun `hwp to html zip 파일 변환 요청 - 실패`() {
        // given
        val jsonMsg = "{\"key\":\"val\"}".byteInputStream()

        // when & then
        assertThrows<RuntimeException> {
            hwpSocketClientServiceErrCase.requestHtmlZip(
                jsonMsg,
                RESPONSE_DATA.toByteArray().size,
                HwpExtensionType.HWP
            )
        }
    }
}

