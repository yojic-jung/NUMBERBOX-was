package com.kamcci.numberbox.restapi.controller.cs

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportWriteCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dummy.file.FileFixture.getMultipartFile
import com.kamcci.numberbox.restapi.util.file.FileUtil
import com.kamcci.numberbox.restapi.util.file.FileUtil.toFile
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.MethodArgumentNotValidException
import java.io.ByteArrayInputStream

@WebMvcUnitTest
class CsErrorReportWriteControllerTest @Autowired constructor(
    private val csErrorReportWriteCase: CsErrorReportWriteCase,
    private val fileUseCase: FileUseCase
) : BaseMockMvcTest() {
    companion object {
        // 고객센터 신고하기
        const val REPORT_CS_ERROR = "/cs/error"
    }

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
        val firstImgFile = getMultipartFile("firstImgFile", "firstImgFile.png")
        val secondImgFile = getMultipartFile("secondImgFile", "secondImgFile.png")
        val thirdImgFile = getMultipartFile("thirdImgFile", "thirdImgFile.png")
        val imgFileList = listOf(firstImgFile, secondImgFile, thirdImgFile)

        val mockFileDto = FileUploadDto("fileKey", "originalName", 123, ByteArrayInputStream("content".toByteArray()))
        mockkObject(FileUtil)
        every { toFile(any()) } returns mockFileDto
        `when`(fileUseCase.upload(mockFileDto, FileType.CsErrIMG))
            .thenReturn(FileNameVo("pptName", "pptPath"))
        `when`(csErrorReportWriteCase.createReport(any())).thenReturn(any())

        // when
        val resultAction = postMultipartForm(REPORT_CS_ERROR, reqBody, imgFileList)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `고객센터 신고하기(파일 미포함) - 성공`() {
        `when`(csErrorReportWriteCase.createReport(any())).thenReturn(any())

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