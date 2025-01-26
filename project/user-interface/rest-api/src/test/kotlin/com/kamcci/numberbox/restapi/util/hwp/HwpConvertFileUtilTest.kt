package com.kamcci.numberbox.restapi.util.hwp

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class HwpConvertFileUtilTest {
    private val hwpConvertFileUtil = HwpConvertFileUtil()

    @Test
    fun `hwpToHtml 압축 해제 - 성공`() {
        // given
        val zipFilePath = Paths.get("src/test/resources/data/[N명의수학] 나의 제작 문제.zip")
        val zipBytes = Files.readAllBytes(zipFilePath)

        // when
        val (indexXhtml, images) = hwpConvertFileUtil.unzip(zipBytes)

        // then
        assertThat(indexXhtml).isNotNull()
        assertThat(images.size).isGreaterThan(0)
    }

    @Test
    fun `hwpToHtml 압축 해제 - 실패`() {
        // when
        val (indexXhtml, images) = hwpConvertFileUtil.unzip("".toByteArray())

        // then
        assertThat(indexXhtml).isNull()
        assertThat(images.size).isEqualTo(0)
    }
}