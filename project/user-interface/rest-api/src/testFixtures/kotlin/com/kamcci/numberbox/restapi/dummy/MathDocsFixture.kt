package com.kamcci.numberbox.restapi.dummy

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsPaperVo
import java.time.LocalDateTime

object MathDocsFixture {
    fun getMathDocsPaperVo() = MathDocsPaperVo(
        id = 1L,
        contentsIdList = listOf(1L, 2L),
        docsGrade = "중1",
        docsTitle = "소인수 분해",
        docsSubTitle = "최대 공약수 구하기",
        docsOwner = "호랑이 선생님",
        docsSttsType = DocsStatusType.None,
        sysCreateDate = LocalDateTime.now(),
        sysUpdateDate = LocalDateTime.now(),
    )
}