package com.kamcci.numberbox.restapi.controller.cs

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.sample.file.FileSampleData.getMultipartFile
import org.junit.jupiter.api.Test
import org.springframework.web.bind.MethodArgumentNotValidException

@WebMvcUnitTest
class CsErrorReportWriteControllerTest : BaseMockMvcTest() {
    companion object {
        // 고객센터 신고하기
        const val REPORT_CS_ERROR = "/cs/error"
    }

    // given
    private val reqBody = mapOf(
        "errType" to "Etc",
        "contentsId" to "1",
        "reportContents" to "문의 내용",
        "clientOs" to "Windows",
        "clientBrowser" to "Chrome",
    )

    @Test
    fun `고객센터 신고하기(파일 포함) - 성공`() {
        // given
        val firstImgFile = getMultipartFile("firstImgFile", "firstImgFile.png")
        val secondImgFile = getMultipartFile("secondImgFile", "secondImgFile.png")
        val thirdImgFile = getMultipartFile("thirdImgFile", "thirdImgFile.png")
        val imgFileList = listOf(firstImgFile, secondImgFile, thirdImgFile)

        // when
        val resultAction = postMultipartForm(REPORT_CS_ERROR, reqBody, imgFileList)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `고객센터 신고하기(파일 미포함) - 성공`() {
        // when
        val resultAction = postMultipartForm(REPORT_CS_ERROR, reqBody, listOf())

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `고객센터 신고하기(이미지 파일 아님) - 실패`() {
        // given
        val firstImgFile = getMultipartFile("firstImgFile", "firstImgFile.xml")
        val secondImgFile = getMultipartFile("secondImgFile", "secondImgFile.xml")
        val thirdImgFile = getMultipartFile("thirdImgFile", "thirdImgFile.xml")
        val imgFileList = listOf(firstImgFile, secondImgFile, thirdImgFile)

        // when
        val resultAction = postMultipartForm(REPORT_CS_ERROR, reqBody, imgFileList)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }

}