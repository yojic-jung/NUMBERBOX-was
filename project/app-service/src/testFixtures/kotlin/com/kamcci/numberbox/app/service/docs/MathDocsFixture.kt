package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsVo
import java.time.LocalDateTime

object MathDocsFixture {
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
}