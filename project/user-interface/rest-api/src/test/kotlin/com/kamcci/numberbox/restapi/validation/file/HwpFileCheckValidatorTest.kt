package com.kamcci.numberbox.restapi.validation.file

import com.kamcci.numberbox.restapi.stub.common.MockCustomMultipartFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile

class HwpFileCheckValidatorTest {
    @Test
    fun `hwp 파일 용량 체크 - 성공`() {
        // given
        val mockPpt = MockMultipartFile(
            "file", // 파라미터 이름
            "test.hwp", // 파일 이름
            "application/x-hwp", // MIME 타입
            "".toByteArray() // 실제 파일 데이터
        )

        // when
        val isUnderMaxSize = HwpFileCheckValidator.isValidHwp(mockPpt)

        // then
        assertThat(isUnderMaxSize).isTrue()
    }

    @Test
    fun `hwp 파일 용량 체크 맥시멈 사이즈 - 성공`() {
        // given
        val mockPpt = MockMultipartFile(
            "file", // 파라미터 이름
            null, // 파일 이름
            "application/x-hwp", // MIME 타입
            ByteArray((HwpFileCheckValidator.MAX_FILE_SIZE + 1)) { 0 } // 실제 파일 데이터
        )

        // when
        val isUnderMaxSize = HwpFileCheckValidator.isValidHwp(mockPpt)

        // then
        assertThat(isUnderMaxSize).isFalse()
    }

    @Test
    fun `파일 확장자 없음 체크 - 성공`() {
        // given
        val mockMultipartFile = MockCustomMultipartFile(
            "file", // 파라미터 이름
            null, // 파일 이름
            "", // MIME 타입
            "123".toByteArray() // 실제 파일 데이터
        )

        // when
        val existExtesion = HwpFileCheckValidator.isValidHwp(mockMultipartFile)

        // then
        assertThat(existExtesion).isFalse()
    }

    @Test
    fun `hwp 확장자 체크 - 성공`() {
        // given
        val mockPpt = MockMultipartFile(
            "file", // 파라미터 이름
            "test.hwp", // 파일 이름
            "application/x-hwp", // MIME 타입
            "123".toByteArray() // 실제 파일 데이터
        )

        // when
        val existExtesion = HwpFileCheckValidator.isValidHwp(mockPpt)

        // then
        assertThat(existExtesion).isTrue()
    }
}