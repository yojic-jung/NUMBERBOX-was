package com.kamcci.numberbox.app.service.sample

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.domain.vo.resource.*
import java.time.LocalDateTime
import java.util.*

object MathResourceSampleData {

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

    fun getMathResourceMenuVo(id: Long = 1L): MathResourceMenuVo {
        return MathResourceMenuVo(
            id = id,
            mainCateId = 1,
            mainCateName = "mainCateName",
            midCateId = 1,
            midCateName = "midCateName",
            alignOrder = 1,
        )
    }

    fun getMathResourceMenuVoList(size: Int = 100): List<MathResourceMenuVo> {
        val resourceMenuVoList: MutableList<MathResourceMenuVo> = mutableListOf()
        for (i in 1..size) {
            resourceMenuVoList.add(getMathResourceMenuVo(i.toLong()))
        }
        return resourceMenuVoList
    }


    fun getMathResourceDetailVo(id: Long = 1L) =
        MathResourceDetailVo(
            id = id,
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

    fun getMathResourceVo(id: Long = 1L) =
        MathResourceVo(
            id = id,
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


    fun getMathResourceVoList(size: Int = 100): List<MathResourceVo> {
        val categoryVoList: MutableList<MathResourceVo> = mutableListOf()
        for (i in 1..size) {
            categoryVoList.add(getMathResourceVo(i.toLong()))
        }
        return categoryVoList
    }
}