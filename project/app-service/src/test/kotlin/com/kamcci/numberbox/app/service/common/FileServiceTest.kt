package com.kamcci.numberbox.app.service.common

import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class FileServiceTest {
    private val fileNameMakeService = FileService(mock())

    companion object {
        const val MAX_FILE_NAME_SIZE = 30
    }

    @Test
    fun `파일명 30글자 이하 - 성공`() {
        // given
        FileType.entries.forEach {
            // when
            val fileNameVo = fileNameMakeService.makeFileNameByType("tmp.heic", it)

            assertThat(fileNameVo.name.length).isLessThanOrEqualTo(MAX_FILE_NAME_SIZE)
        }
    }

    @Test
    fun `확장자는 그대로 가져가며 새로운 파일 이름 생성 - 성공`() {
        // given
        val fileName = "test.png"
        val fileType = FileType.ProfileIMG

        // when
        val fileNameVo = fileNameMakeService.makeFileNameByType(fileName, fileType)

        // then
        // 1. 파일 경로 첫글자는 파일 타입 상태코드
        assertEquals(fileType.path, fileNameVo.path.split("/")[0])

        // 2. 파일 확장자 그대로
        val expectedExtension = fileName.substringAfterLast(".")
        val actualExtension = fileNameVo.name.substringAfterLast(".")
        assertEquals(expectedExtension, actualExtension)
    }

}