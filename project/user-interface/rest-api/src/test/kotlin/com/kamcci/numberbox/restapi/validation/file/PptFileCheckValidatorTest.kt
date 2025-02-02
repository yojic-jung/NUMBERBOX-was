package com.kamcci.numberbox.restapi.validation.file

import com.kamcci.numberbox.restapi.stub.common.MockCustomMultipartFile
import com.kamcci.numberbox.restapi.validation.file.PptFileCheckValidator.Companion.MAX_FILE_SIZE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile

class PptFileCheckValidatorTest {
    @Test
    fun `ppt null 체크 - 성공`() {
        // given
        val mockPpt = null

        // when
        val isTrue = PptFileCheckValidator.isValidPpt(mockPpt)

        // then
        assertThat(isTrue).isTrue()
    }

    @Test
    fun `ppt 파일 용량 체크 - 성공`() {
        // given
        val mockPpt = MockMultipartFile(
            "file", // 파라미터 이름
            "test.pptx", // 파일 이름
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", // MIME 타입
            "".toByteArray() // 실제 파일 데이터
        )

        // when
        val isTrue = PptFileCheckValidator.isValidPpt(mockPpt)

        // then
        assertThat(isTrue).isTrue()
    }

    @Test
    fun `ppt 파일 용량 체크 맥시멈 사이즈 - 성공`() {
        // given
        val mockPpt = MockMultipartFile(
            "file", // 파라미터 이름
            null, // 파일 이름
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", // MIME 타입
            ByteArray((MAX_FILE_SIZE + 1)) { 0 } // 실제 파일 데이터
        )

        // when
        val isTrue = PptFileCheckValidator.isValidPpt(mockPpt)

        // then
        assertThat(isTrue).isFalse()
    }

    @Test
    fun `파일 확장자 없음 체크 - 성공`() {
        // given
        val mockMultipartFile = MockCustomMultipartFile(
            "file", // 파라미터 이름
            null, // 파일 이름
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", // MIME 타입
            "123".toByteArray()// 실제 파일 데이터
        )

        // when
        val isTrue = PptFileCheckValidator.isValidPpt(mockMultipartFile)

        // then
        assertThat(isTrue).isFalse()
    }

    @Test
    fun `ppt 확장자 체크 - 성공`() {
        // given
        val mockPpt = MockMultipartFile(
            "file", // 파라미터 이름
            "test.pptx", // 파일 이름
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", // MIME 타입
            "123".toByteArray() // 실제 파일 데이터
        )

        // when
        val isTrue = PptFileCheckValidator.isValidPpt(mockPpt)

        // then
        assertThat(isTrue).isTrue()
    }
}