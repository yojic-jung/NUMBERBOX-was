package com.kamcci.numberbox.app.service.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import com.kamcci.numberbox.app.port.orm.cs.CsErrorReportWriteOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito
import java.util.*

class CsErrorReportWriteServiceTest {
    private val csErrorReportWriteOrmPort: CsErrorReportWriteOrmPort = Mockito.mock()
    private val csErrorReportWriteService = CsErrorReportWriteService(csErrorReportWriteOrmPort, Mockito.mock())

    private val contentsErrDto = CsErrorReportCreateDto(
        errType = CSErrorType.MathContents,
        contentsId = 1L,
        reportMemberId = UUID.randomUUID(),
        reportContents = "contents",
        clientOs = OsType.Etc,
        clientBrowser = BrowserType.Chrome,
        firstImgPath = "root/aa",
        firstImgName = "abc.png",
        secondImgPath = "root/bb",
        secondImgName = "bcd.png",
        thirdImgPath = "root/cc.png",
        thirdImgName = "cda.png",
        reportStts = ReportSttsType.Submit,
    )

    private val docsErrDto = CsErrorReportCreateDto(
        errType = CSErrorType.MathDocs,
        contentsId = 1L,
        reportMemberId = UUID.randomUUID(),
        reportContents = "contents",
        clientOs = OsType.Etc,
        clientBrowser = BrowserType.Chrome,
        firstImgPath = "root/aa",
        firstImgName = "abc.png",
        secondImgPath = "root/bb",
        secondImgName = "bcd.png",
        thirdImgPath = "root/cc.png",
        thirdImgName = "cda.png",
        reportStts = ReportSttsType.Submit,
    )

    @Test
    fun `수학문제 오류 신고 - 성공`() {
        // given
        Mockito.`when`(csErrorReportWriteOrmPort.create(contentsErrDto)).thenReturn(1L)

        // when
        assertDoesNotThrow {
            csErrorReportWriteService.createReport(contentsErrDto)
        }
    }

    @Test
    fun `학습지 오류 신고 - 성공`() {
        // given
        Mockito.`when`(csErrorReportWriteOrmPort.create(docsErrDto)).thenReturn(1L)

        // when
        assertDoesNotThrow {
            csErrorReportWriteService.createReport(docsErrDto)
        }
    }

}