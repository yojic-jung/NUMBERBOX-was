package com.kamcci.numberbox.restapi.dummy.resource

import com.kamcci.numberbox.app.domain.vo.resource.MathResourceCateVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceDetailVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceImgVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceVo
import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceCreateRequest
import com.kamcci.numberbox.restapi.dummy.file.FileFixture.getMultipartFile
import java.time.LocalDateTime

object MathResourceFixture {
    fun getMathResourceVo(id: Long?) =
        MathResourceVo(
            id = id ?: 1L,
            title = "title",
            imgPath = "imgPath",
            imgName = "imgName",
            pptPath = "pptPath",
            pptName = "pptName",
            pptPageCnt = 1,
            downCnt = 1,
            sysCreateDate = LocalDateTime.now(),
            sysUpdateDate = LocalDateTime.now(),
        )

    fun getMathResourceVoList() =
        listOf(
            getMathResourceVo(1L),
            getMathResourceVo(2L),
            getMathResourceVo(3L),
            getMathResourceVo(4L),
            getMathResourceVo(5L),
        )

    fun getMathResourceDetailVo(id: Long?) =
        MathResourceDetailVo(
            id = id ?: 1L,
            title = "title",
            imgPath = "imgPath",
            imgName = "imgName",
            pptPath = "pptPath",
            pptName = "pptName",
            pptPageCnt = 1,
            downCnt = 1,
            imgList = listOf(MathResourceImgVo("", "")),
            cateList = listOf(MathResourceCateVo(1, 1)),
            sysCreateDate = LocalDateTime.now(),
            sysUpdateDate = LocalDateTime.now(),
        )

    fun getMathResourceDetailVoList() =
        listOf(
            getMathResourceDetailVo(1L),
            getMathResourceDetailVo(2L),
            getMathResourceDetailVo(3L),
            getMathResourceDetailVo(4L),
            getMathResourceDetailVo(5L),
        )

    fun getMathResourceCreateRequest() =
        MathResourceCreateRequest(
            title = "title",
            mainCateId = "1",
            midCateId = "1",
            cateList = listOf("1-1", "1-2"),
            pptFile = getMultipartFile("name", "name.ppt"),
            imgFile = getMultipartFile("name", "name.png"),
        )
}