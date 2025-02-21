package com.kamcci.numberbox.restapi.validation.file

import com.kamcci.numberbox.restapi.mock.common.MockCustomMultipartFile
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile

class ImgFileCheckValidatorTest {
    @Test
    fun `img null 체크 - 성공`() {
        // given
        val mockPpt = null

        // when
        val isTrue = ImgFileCheckValidator.isValidImg(mockPpt)

        // then
        Assertions.assertThat(isTrue).isTrue()
    }

    @Test
    fun `img 파일 용량 체크 - 성공`() {
        // given
        val mockPpt = MockMultipartFile(
            "file", // 파라미터 이름
            "test.pptx", // 파일 이름
            "image/jpeg", // MIME 타입
            "".toByteArray() // 실제 파일 데이터
        )

        // when
        val isTrue = ImgFileCheckValidator.isValidImg(mockPpt)

        // then
        Assertions.assertThat(isTrue).isTrue()
    }

    @Test
    fun `img 파일 용량 체크 맥시멈 사이즈 - 성공`() {
        // given
        val mockPpt = MockMultipartFile(
            "file", // 파라미터 이름
            null, // 파일 이름
            "image/jpeg", // MIME 타입
            ByteArray((ImgFileCheckValidator.MAX_FILE_SIZE + 1)) { 0 } // 실제 파일 데이터
        )

        // when
        val isTrue = ImgFileCheckValidator.isValidImg(mockPpt)

        // then
        Assertions.assertThat(isTrue).isFalse()
    }

    @Test
    fun `파일 확장자 없음 체크 - 성공`() {
        // given
        val mockMultipartFile = MockCustomMultipartFile(
            "file", // 파라미터 이름
            null, // 파일 이름
            "image/jpeg", // MIME 타입
            "123".toByteArray() // 실제 파일 데이터
        )

        // when
        val isTrue = ImgFileCheckValidator.isValidImg(mockMultipartFile)

        // then
        Assertions.assertThat(isTrue).isFalse()
    }

    @Test
    fun `img 확장자 체크 - 성공`() {
        // given
        val mockPpt = MockMultipartFile(
            "file", // 파라미터 이름
            "test.jpg", // 파일 이름
            "image/jpeg", // MIME 타입
            "123".toByteArray() // 실제 파일 데이터
        )

        // when
        val isTrue = ImgFileCheckValidator.isValidImg(mockPpt)

        // then
        Assertions.assertThat(isTrue).isTrue()
    }
}