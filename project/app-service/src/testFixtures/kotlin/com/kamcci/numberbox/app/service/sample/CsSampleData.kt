package com.kamcci.numberbox.app.service.sample

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import com.kamcci.numberbox.app.domain.vo.cs.CsErrReportVo
import java.time.LocalDateTime

object CsSampleData {
    fun getCsErrReportVo(id: Long): CsErrReportVo {
        val now = LocalDateTime.now()
        return CsErrReportVo(
            id = id,
            errType = CSErrorType.MathContents,
            contentsId = 11L,
            reportContents = "ad",
            browserType = BrowserType.Chrome,
            osType = OsType.Windows,
            replyContents = "af",
            firstImgPath = "af",
            firstImgName = "af",
            secondImgPath = "af",
            secondImgName = "af",
            thirdImgPath = "af",
            thirdImgName = "af",
            reportStts = ReportSttsType.Submit,
            sysCreateDate = now,
            sysUpdateDate = now,
        )
    }

    fun getCsErrReportVoList() = listOf(
        getCsErrReportVo(1L),
        getCsErrReportVo(2L)
    )

}