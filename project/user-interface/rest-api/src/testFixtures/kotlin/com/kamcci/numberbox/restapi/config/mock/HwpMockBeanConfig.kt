package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import com.kamcci.numberbox.app.domain.vo.hwp.HwpConvertContentsVo
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import com.kamcci.numberbox.restapi.util.hwp.HwpConvertFileUtil
import org.springframework.context.annotation.Bean
import java.io.InputStream
import java.time.LocalDateTime
import java.util.*

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
    fun hwpConvertContentsWriteCase(): HwpConvertContentsWriteCase = object : HwpConvertContentsWriteCase {
        override fun create(createDto: HwpConvertContentsCreateDto): Long {
            return if (createDto.fileName != "실패") 0L else 1L
        }

        override fun update(updateDto: HwpConvertContentsUpdateDto): Long {
            return if (updateDto.id % 2L == 1L) 1L else 0L
        }

        override fun delete(contentsId: Long, memberId: UUID): Long {
            return if (contentsId % 2L == 1L) 1L else 0L
        }
    }

    @Bean
    fun hwpConvertContentsReadCase(): HwpConvertContentsReadCase = object : HwpConvertContentsReadCase {
        override fun readAllByMemberId(memberId: UUID): List<HwpConvertContentsVo> {
            return listOf(
                HwpConvertContentsVo(
                    id = 1L,
                    fileName = "",
                    contents = "",
                    isConverted = false,
                    imgPath = "",
                    sysCreateDate = LocalDateTime.now(),
                    sysUpdateDate = LocalDateTime.now(),
                )
            )
        }
    }

    @Bean
    fun hwpConvertFileUtil(): HwpConvertFileUtil = object : HwpConvertFileUtil() {
        override fun unzip(zipBytes: ByteArray): Pair<String?, Map<String, ByteArray>> {
            return Pair("index.xhtml", mutableMapOf("BIN001.png" to "".toByteArray()))
        }
    }
}