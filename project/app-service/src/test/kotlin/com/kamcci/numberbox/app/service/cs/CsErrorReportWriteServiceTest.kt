package com.kamcci.numberbox.app.service.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import com.kamcci.numberbox.app.service.stub.port.orm.cs.MockCsErrorReportWriteOrmPort
import com.kamcci.numberbox.app.service.stub.port.orm.docs.MockMathDocsPaperWriteOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

class CsErrorReportWriteServiceTest {
    private val csErrorReportWriteService =
        CsErrorReportWriteService(MockCsErrorReportWriteOrmPort(), MockMathDocsPaperWriteOrmPort())

    @Test
    fun `수학문제 오류 신고 - 성공`() {
        // given
        val contentsErrDto = CsErrorReportCreateDto(
            errType = CSErrorType.MathContents,
            contentsId = 1L, // 성공 케이스
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

        // when & then
        assertDoesNotThrow {
            csErrorReportWriteService.createReport(contentsErrDto)
        }
    }

    @Test
    fun `학습지 오류 신고 - 성공`() {
        // given
        val docsErrDto = CsErrorReportCreateDto(
            errType = CSErrorType.MathDocs,
            contentsId = 1L, // 성공 케이스
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

        // when & then
        assertDoesNotThrow {
            csErrorReportWriteService.createReport(docsErrDto)
        }
    }

}