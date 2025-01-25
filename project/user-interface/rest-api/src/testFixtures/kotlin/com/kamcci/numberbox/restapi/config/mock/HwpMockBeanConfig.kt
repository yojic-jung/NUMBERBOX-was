package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import com.kamcci.numberbox.app.domain.vo.hwp.HwpConvertContentsVo
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import java.io.InputStream
import java.time.LocalDateTime
import java.util.*

@Profile("test")
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

    @Bean
    fun hwpConvertContentsWriteCase(): HwpConvertContentsWriteCase = object : HwpConvertContentsWriteCase {
        override fun create(createDto: HwpConvertContentsCreateDto): Long {
            return 1L
        }

        override fun update(updateDto: HwpConvertContentsUpdateDto): Long {
            return 1L
        }

        override fun delete(contentsId: Long, memberId: UUID): Long {
            return 1L
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
}