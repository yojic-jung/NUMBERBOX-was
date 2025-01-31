package com.kamcci.numberbox.restapi.stub

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import com.kamcci.numberbox.app.service.stub.usecase.hwp.MockHwpConvertContentsReadCase
import com.kamcci.numberbox.app.service.stub.usecase.hwp.MockHwpConvertContentsWriteCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import com.kamcci.numberbox.restapi.util.hwp.HwpConvertFileUtil
import org.springframework.context.annotation.Bean
import java.io.InputStream

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

    @Bean
    fun hwpConvertContentsWriteCase(): HwpConvertContentsWriteCase = MockHwpConvertContentsWriteCase()

    @Bean
    fun hwpConvertContentsReadCase(): HwpConvertContentsReadCase = MockHwpConvertContentsReadCase()

    @Bean
    fun hwpConvertFileUtil(): HwpConvertFileUtil = object : HwpConvertFileUtil() {
        override fun unzip(zipBytes: ByteArray): Pair<String?, Map<String, ByteArray>> {
            return Pair("index.xhtml", mutableMapOf("BIN001.png" to "".toByteArray()))
        }
    }
}