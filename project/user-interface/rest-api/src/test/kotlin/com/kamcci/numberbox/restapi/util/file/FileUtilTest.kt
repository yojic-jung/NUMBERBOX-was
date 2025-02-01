package com.kamcci.numberbox.restapi.util.file

import com.kamcci.numberbox.restapi.dummy.file.FileDummyData
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import java.io.ByteArrayOutputStream

class FileUtilTest {
    @Test
    fun `multipartFile to uploadDto - 성공`() {
        // given
        val multipartFile = FileDummyData.getMultipartFile("name", "name.png")

        // when
        val fileUploadDto = FileUtil.toFile(multipartFile)

        // then
        assertThat(multipartFile.originalFilename).isEqualTo(fileUploadDto.name)
    }

    @Test
    fun `ppt 슬라이드별 inputStream 변환 - 성공`() {
        // given
        // Mock PPT 파일 생성
        val pptSlideShow = XMLSlideShow()
        pptSlideShow.createSlide() // 첫 번째 슬라이드 추가
        pptSlideShow.createSlide() // 두 번째 슬라이드 추가

        // ppt 생성
        val outputStream = ByteArrayOutputStream()
        pptSlideShow.write(outputStream)
        pptSlideShow.close()

        // multipartFile 생성
        val mockMultipartFile = MockMultipartFile(
            "file", // 파라미터 이름
            "test.pptx", // 파일 이름
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", // MIME 타입
            outputStream.toByteArray() // 실제 파일 데이터
        )

        // when
        val result = FileUtil.toPptSlide(mockMultipartFile)

        // then
        assertEquals(2, result.size) // 2개의 슬라이드 변환 확인
        assertEquals("image/png", result[0].contentType) // MIME 타입 확인
    }
}