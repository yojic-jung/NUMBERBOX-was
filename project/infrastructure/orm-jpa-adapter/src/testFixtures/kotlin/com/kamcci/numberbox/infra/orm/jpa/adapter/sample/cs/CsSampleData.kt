package com.kamcci.numberbox.infra.orm.jpa.adapter.sample.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import java.util.*

object CsSampleData {

    fun getCsErrorReportCreateDto() = CsErrorReportCreateDto(
        CSErrorType.Etc,
        1L,
        UUID.randomUUID(),
        "",
        OsType.Etc,
        BrowserType.Etc,
        "",
        "",
        "",
        "",
        "",
        "",
        ReportSttsType.Submit
    )
}