package com.kamcci.numberbox.restapi.controller.cs

import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportWriteCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dummy.file.FileFixture.getImgFile
import com.kamcci.numberbox.restapi.util.file.FileUtil.toFile
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.MethodArgumentNotValidException

@WebMvcUnitTest
class CsErrorReportWriteControllerTest : BaseMockMvcTest() {
    companion object {
        // 고객센터 신고하기
        const val REPORT_CS_ERROR = "/cs/error"
    }

    @Autowired
    lateinit var csErrorReportWriteCase: CsErrorReportWriteCase

    @Autowired
    lateinit var fileUseCase: FileUseCase

    // given
    val reqBody = mapOf(
        "errType" to "Etc",
        "contentsId" to "1",
        "reportContents" to "문의 내용",
        "clientOs" to "Windows",
        "clientBrowser" to "Chrome",
    )


    @Test
    fun `고객센터 신고하기(파일 포함) - 성공`() {
        // given
        val firstImgFile = getImgFile("firstImgFile", "firstImgFile.png")
        val secondImgFile = getImgFile("secondImgFile", "secondImgFile.png")
        val thirdImgFile = getImgFile("thirdImgFile", "thirdImgFile.png")
        val imgFileList = listOf(firstImgFile, secondImgFile, thirdImgFile)

        `when`(csErrorReportWriteCase.createReport(any())).thenReturn(any())
        `when`(fileUseCase.upload(toFile(firstImgFile), any())).thenReturn(any())
        `when`(fileUseCase.upload(toFile(secondImgFile), any())).thenReturn(any())
        `when`(fileUseCase.upload(toFile(thirdImgFile), any())).thenReturn(any())

        // when
        val resultAction = postMultipartForm(REPORT_CS_ERROR, reqBody, imgFileList)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `고객센터 신고하기(파일 미포함) - 성공`() {
        // given
        val reqBody = mapOf(
            "errType" to "Etc",
            "contentsId" to "1",
            "reportContents" to "문의 내용",
            "clientOs" to "Windows",
            "clientBrowser" to "Chrome",
        )
        `when`(csErrorReportWriteCase.createReport(any())).thenReturn(any())

        // when
        val resultAction = postMultipartForm(REPORT_CS_ERROR, reqBody, listOf())

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `고객센터 신고하기(이미지 파일 아님) - 실패`() {
        // given
        val firstImgFile = getImgFile("firstImgFile", "firstImgFile.xml")
        val secondImgFile = getImgFile("secondImgFile", "secondImgFile.xml")
        val thirdImgFile = getImgFile("thirdImgFile", "thirdImgFile.xml")
        val imgFileList = listOf(firstImgFile, secondImgFile, thirdImgFile)

        `when`(csErrorReportWriteCase.createReport(any())).thenReturn(any())
        `when`(fileUseCase.upload(toFile(firstImgFile), any())).thenReturn(any())
        `when`(fileUseCase.upload(toFile(secondImgFile), any())).thenReturn(any())
        `when`(fileUseCase.upload(toFile(thirdImgFile), any())).thenReturn(any())

        // when
        val resultAction = postMultipartForm(REPORT_CS_ERROR, reqBody, imgFileList)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }

}