package com.kamcci.numberbox.restapi.util.file

import com.kamcci.numberbox.restapi.sample.file.FileSampleData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class FileUtilImplTest {
    private val fileUtil = FileUtilImpl()

    @Test
    fun `multipartFile to uploadDto - 성공`() {
        // given
        val multipartFile = FileSampleData.getMultipartFile("name", "name.png")

        // when
        val fileUploadDto = fileUtil.toFile(multipartFile)

        // then
        assertThat(multipartFile.originalFilename).isEqualTo(fileUploadDto.name)
    }

    @Test
    fun `ppt 슬라이드별 inputStream 변환 - 성공`() {
        // given
        // Mock PPT 파일 생성
        val name = "src/test/resources/data/test.pptx"
        val file = File(name)

        // when
        val result = fileUtil.toPptSlide(file.inputStream())

        // then
        assertEquals(2, result.size) // 2개의 슬라이드 변환 확인
        assertEquals("image/png", result[0].contentType) // MIME 타입 확인
    }
}