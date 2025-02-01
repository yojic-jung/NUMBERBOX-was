package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dummy.file.FileDummyData.getMultipartFile
import com.kamcci.numberbox.restapi.util.file.FileUtil
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import java.io.ByteArrayInputStream

@WebMvcUnitTest
class MathResourceWriteControllerTest(
    @Autowired
    private val fileUseCase: FileUseCase,
) : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/resource"
    }

    @Test
    fun `학습자료 등록 - 성공`() {
        // given
        val req = mapOf(
            "title" to "title",
            "mainCateId" to "1",
            "midCateId" to "1",
            "cateList" to "1-1,1-2",
        )
        val pptFile = getMultipartFile("pptFile", "name.ppt")
        val imgFile = getMultipartFile("imgFile", "name.png")
        val fileList = listOf(pptFile, imgFile)
        Mockito.`when`(fileUseCase.upload(any(), any())).thenReturn(FileNameVo("pptName", "pptPath"))
        mockkObject(FileUtil)
        every { FileUtil.toPptSlide(pptFile) } returns
                listOf(FileUploadDto("", "", 1, ByteArrayInputStream("1".toByteArray())))

        // when
        val resultAction = postMultipartForm(PREFIX, req, fileList)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습자료 등록(대표이미지 미존재) - 성공`() {
        // given
        val req = mapOf(
            "title" to "title",
            "mainCateId" to "1",
            "midCateId" to "1",
            "cateList" to "1-1,1-2",
        )
        val pptFile = getMultipartFile("pptFile", "name.ppt")
        val fileList = listOf(pptFile)
        Mockito.`when`(fileUseCase.upload(any(), any())).thenReturn(FileNameVo("pptName", "pptPath"))
        mockkObject(FileUtil)
        every { FileUtil.toPptSlide(pptFile) } returns
                listOf(FileUploadDto("", "", 1, ByteArrayInputStream("1".toByteArray())))

        // when
        val resultAction = postMultipartForm(PREFIX, req, fileList)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습자료 수정 - 성공`() {
        // given
        val req = mapOf(
            "resourceId" to "1",
            "title" to "title",
            "mainCateId" to "1",
            "midCateId" to "1",
            "cateList" to "1-1,1-2",
        )
        val pptFile = getMultipartFile("pptFile", "name.ppt")
        val imgFile = getMultipartFile("imgFile", "name.png")
        val fileList = listOf(pptFile, imgFile)
        Mockito.`when`(fileUseCase.upload(any(), any())).thenReturn(FileNameVo("pptName", "pptPath"))
        mockkObject(FileUtil)
        every { FileUtil.toPptSlide(pptFile) } returns
                listOf(FileUploadDto("", "", 1, ByteArrayInputStream("1".toByteArray())))

        // when
        val resultAction = putMultipartForm(PREFIX, req, fileList)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습자료 수정(대표 이미지 미존재) - 성공`() {
        // given
        val req = mapOf(
            "resourceId" to "1",
            "title" to "title",
            "mainCateId" to "1",
            "midCateId" to "1",
            "cateList" to "1-1,1-2",
        )
        val pptFile = getMultipartFile("pptFile", "name.ppt")
        val fileList = listOf(pptFile)
        Mockito.`when`(fileUseCase.upload(any(), any())).thenReturn(FileNameVo("pptName", "pptPath"))
        mockkObject(FileUtil)
        every { FileUtil.toPptSlide(pptFile) } returns
                listOf(FileUploadDto("", "", 1, ByteArrayInputStream("1".toByteArray())))

        // when
        val resultAction = putMultipartForm(PREFIX, req, fileList)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습자료 수정(파일 미존재) - 성공`() {
        // given
        val req = mapOf(
            "resourceId" to "1",
            "title" to "title",
            "mainCateId" to "1",
            "midCateId" to "1",
            "cateList" to "1-1,1-2",
        )
        Mockito.`when`(fileUseCase.upload(any(), any())).thenReturn(null)

        // when
        val resultAction = putMultipartForm(PREFIX, req, listOf())

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습자료 삭제 - 성공`() {
        // given
        val resourceId = 1

        // when
        val resultAction = delRequest("$PREFIX/$resourceId")

        // then
        assert2xx(resultAction)
    }

}