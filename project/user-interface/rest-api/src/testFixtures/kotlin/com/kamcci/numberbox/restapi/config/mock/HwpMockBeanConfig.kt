package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.domain.dto.hwp.HwpExtensionType
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.io.InputStream

@TestConfiguration
class HwpMockBeanConfig {
    @Bean
    fun hwpSocketClient(): HwpSocketClient = object : HwpSocketClient {
        override fun requestHwpFile(jsonMsg: String): ByteArray {
            // 필요한 로직을 작성합니다. 예시로 빈 바이트 배열을 반환합니다.
            return byteArrayOf()
        }

        override fun requestHtmlZip(hwpFileIS: InputStream, dataSize: Int, extension: HwpExtensionType): ByteArray {
            // 필요한 로직을 작성합니다. 예시로 빈 바이트 배열을 반환합니다.
            return byteArrayOf()
        }
    }
}