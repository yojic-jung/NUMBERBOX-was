package com.kamcci.numberbox.app.service.dummy

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsVo
import java.time.LocalDateTime

object MathDocsDummyData {
    fun getMathDocsVoList() = listOf(
        MathDocsVo(
            1L,
            1,
            1,
            "contents",
            "contentsImg",
            "imgPath",
            "solution",
            "solutionImg",
            "solutionImgPath",
            "firNo",
            "secNo",
            "thrNo",
            "fourNo",
            "fifNo",
            MultiChoiceType.Essay,
            "answer",
            "choiceAnswer",
            1,
            true,
            ContentsClassifyType.Ipsi,
            "subject",
            "firUnit",
            "secUnit",
            "thrUnit",
            "secUnit",
            LocalDateTime.now()
        )
    )

    fun getMathIpsiDocsReadDto() = MathIpsiDocsReadDto(
        unitIdAndTypeId = listOf("21001-1"),
        quesLevel = listOf(3, 4, 5),
        wrongRatioMin = 50,
        wrongRatioMax = 100,
        ipsiYearStrt = 2019,
        ipsiYearEnd = 2022,
        ipsiMonth = listOf(6, 9, 11),
        count = 100L,
    )

    fun getMathDocsUsageCreateDto() = MathDocsUsageCreateDto(
        contentsIdList = listOf(1L, 2L),
        docsGrade = "중1",
        docsTitle = "소인수분해",
        docsSubTitle = "최대공약수",
        docsOwner = "호랑이 샘",
    )

    fun getMathDocsPaperCreateDto() = MathDocsPaperCreateDto(
        contentsIdList = listOf(1L, 2L, 3L),
        docsGrade = "중1",
        docsTitle = "소인수분해",
        docsSubTitle = "최대공약수",
        docsOwner = "호랑이 샘",
        docsStts = DocsStatusType.None,
    )

    fun getMathDocsPaperUpdtDto() = MathDocsPaperUpdtDto(
        id = 1L,
        contentsIdList = listOf(1L, 2L, 3L),
        docsGrade = "중1",
        docsTitle = "소인수분해",
        docsSubTitle = "최대공약수",
        docsOwner = "호랑이 샘",
        docsStts = DocsStatusType.None,
    )
}