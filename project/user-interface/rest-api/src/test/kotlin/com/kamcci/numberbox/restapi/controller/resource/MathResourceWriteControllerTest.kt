package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.numberbox.app.service.constant.MockTestConstant.SUCCESS_ID
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.sample.file.FileSampleData.getMultipartFile
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class MathResourceWriteControllerTest : BaseMockMvcTest() {
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
        val pptFile = getMultipartFile("pptFile", "name.ppt", "any")
        val fileList = listOf(pptFile)

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
        val pptFile = getMultipartFile("pptFile", "name.ppt", "any")
        val fileList = listOf(pptFile)

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

        // when
        val resultAction = putMultipartForm(PREFIX, req, listOf())

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습자료 삭제 - 성공`() {
        // given
        val resourceId = SUCCESS_ID

        // when
        val resultAction = delRequest("$PREFIX/$resourceId")

        // then
        assert2xx(resultAction)
    }

}