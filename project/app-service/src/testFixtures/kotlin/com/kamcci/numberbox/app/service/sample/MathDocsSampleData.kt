package com.kamcci.numberbox.app.service.sample

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.IpsiPaperType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.app.domain.vo.docs.MathAllTypeDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsPaperVo
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathIpsiDocsVo
import java.time.LocalDateTime

object MathDocsSampleData {

    fun getMathDocsVo() = MathDocsVo(
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
        "quesType",
        LocalDateTime.now()
    )

    fun getMathDocsVoList() = listOf(
        getMathDocsVo()
    )

    fun getMathAllTypeDocsVoList() = listOf(
        MathAllTypeDocsVo(
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
            "quesType",
            LocalDateTime.now(),
            2011,
            11,
            1,
            20,
            IpsiPaperType.Ka
        )
    )

    fun getMathIpsiDocsVoList() = listOf(
        MathIpsiDocsVo(
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
            "quesType",
            LocalDateTime.now(),
            2011,
            11,
            1,
            20,
            IpsiPaperType.Ka
        )
    )

    fun getMathIpsiDocsReadDto(
        unitIdAndTypeId: List<String> = listOf("21001-1"),
        quesLevel: List<Int> = listOf(3, 4, 5)
    ) = MathIpsiDocsReadDto(
        unitIdAndTypeId = unitIdAndTypeId,
        quesLevel = quesLevel,
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

    fun getMathDocsPaperUpdtDto(id: Long = 1L) = MathDocsPaperUpdtDto(
        id = id,
        contentsIdList = listOf(1L, 2L, 3L),
        docsGrade = "중1",
        docsTitle = "소인수분해",
        docsSubTitle = "최대공약수",
        docsOwner = "호랑이 샘",
        docsStts = DocsStatusType.None,
    )

    fun getMathDocsPaperVo() = MathDocsPaperVo(
        id = 1L,
        contentsIdList = listOf(1L, 2L, 3L),
        docsGrade = "중1",
        docsTitle = "소인수분해",
        docsSubTitle = "최대공약수",
        docsOwner = "호랑이 샘",
        docsSttsType = DocsStatusType.None,
        sysCreateDate = LocalDateTime.now(),
        sysUpdateDate = LocalDateTime.now()
    )

    fun getMathDocsPaperVoList(size: Long = 1): List<MathDocsPaperVo> {
        val docsPaperList: MutableList<MathDocsPaperVo> = mutableListOf()
        for (i in 1..size) {
            docsPaperList.add(getMathDocsPaperVo())
        }
        return docsPaperList
    }
}