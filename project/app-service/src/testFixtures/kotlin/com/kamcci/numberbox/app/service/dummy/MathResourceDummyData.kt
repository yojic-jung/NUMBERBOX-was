package com.kamcci.numberbox.app.service.dummy

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceFileVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceImgVo
import java.util.*

object MathResourceDummyData {

    fun getMathResourceCreateDto() = MathResourceCreateDto(
        memberId = UUID.randomUUID(),
        title = "title",
        pptFilePath = "pptFilePath",
        pptFileName = "pptFileName",
        pptPageCnt = 1,
        imgPath = "imgPath",
        imgName = "imgName",
        cateList = listOf("1", "2"),
        imgList = listOf(FileNameVo("name", "path")),
    )

    fun getMathResourceFileVo() = MathResourceFileVo(
        1L,
        "",
        "",
        "",
        "",
        listOf(MathResourceImgVo("", ""))
    )

    fun getMathResourceUpdateDto() = MathResourceUpdateDto(
        resourceId = 1L,
        title = "",
        pptFileName = null,
        pptFilePath = null,
        pptPageCnt = null,
        imgPath = null,
        imgName = null,
        cateList = listOf(""),
        imgList = listOf(),
    )


    fun getMathResourceUpdateDtoList() = listOf(
        MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = null,
            pptFilePath = null,
            pptPageCnt = null,
            imgPath = null,
            imgName = null,
            cateList = listOf(""),
            imgList = listOf(),
        ),

        MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = "",
            pptFilePath = "",
            pptPageCnt = null,
            imgPath = "",
            imgName = "",
            cateList = listOf(""),
            imgList = listOf(),
        ),

        MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = "",
            pptFilePath = null,
            pptPageCnt = null,
            imgPath = null,
            imgName = "",
            cateList = listOf(""),
            imgList = listOf(),
        ),

        MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = null,
            pptFilePath = "",
            pptPageCnt = null,
            imgPath = "",
            imgName = null,
            cateList = listOf(""),
            imgList = listOf(),
        ),

        MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = "name",
            pptFilePath = null,
            pptPageCnt = null,
            imgPath = null,
            imgName = "name",
            cateList = listOf(""),
            imgList = listOf(),
        ),

        MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = null,
            pptFilePath = "path",
            pptPageCnt = null,
            imgPath = "path",
            imgName = null,
            cateList = listOf(""),
            imgList = listOf(),
        ),
        MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = "name",
            pptFilePath = "",
            pptPageCnt = null,
            imgPath = "",
            imgName = "name",
            cateList = listOf(""),
            imgList = listOf(),
        ),

        MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = "",
            pptFilePath = "path",
            pptPageCnt = null,
            imgPath = "path",
            imgName = "",
            cateList = listOf(""),
            imgList = listOf(FileNameVo("", "")),
        ),
    )
}