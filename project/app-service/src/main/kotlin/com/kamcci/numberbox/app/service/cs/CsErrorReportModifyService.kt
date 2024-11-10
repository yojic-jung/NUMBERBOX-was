package com.kamcci.numberbox.app.service.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportSaveDto
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.port.orm.cs.CsErrorReportModifyOrmPort
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperModifyOrmPort
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.common.file.FileNameMaker
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportModifyUseCase
import java.io.InputStream

@UseCase
class CsErrorReportModifyService(
    private val fileNameMaker: FileNameMaker,
    private val fileStoragePort: FileStoragePort,
    private val csErrorReportModifyOrmPort: CsErrorReportModifyOrmPort,
    private val mathDocsPaperModifyOrmPort: MathDocsPaperModifyOrmPort,
) : CsErrorReportModifyUseCase {
    @TXExecute
    override fun createReport(createDto: CsErrorReportCreateDto): Long {
        // 이미지 업로드(최대 세장)
        val firImgNameVo = uploadImgFile(createDto.firstImgName, createDto.firstImg)
        val secImgVo = uploadImgFile(createDto.secondImgName, createDto.secondImg)
        val thrImgVo = uploadImgFile(createDto.thirdImgName, createDto.thirdImg)

        // 학습지 에러인 경우
        if (createDto.errType == CSErrorType.MathDocs) {
            // 학습지 상태 변경
            mathDocsPaperModifyOrmPort.updateDocsSttsByIdAndMemberId(
                createDto.contentsId!!,
                createDto.reportMemberId,
                DocsStatusType.Self
            )
        }

        // 문의 신고 저장
        val saveDto = toSaveDto(createDto, firImgNameVo, secImgVo, thrImgVo)
        return csErrorReportModifyOrmPort.create(saveDto)
    }

    private fun uploadImgFile(imgName: String?, img: InputStream?): FileNameVo? {
        return if (imgName != null && img != null) {
            val fileNameVO = fileNameMaker.makeFileNameByType(imgName, FileType.CsErrIMG)
            fileStoragePort.upload("${fileNameVO.path}/${fileNameVO.name}", img)
            fileNameVO
        } else null
    }

    private fun toSaveDto(
        createDto: CsErrorReportCreateDto,
        firImg: FileNameVo?,
        secImg: FileNameVo?,
        thrImg: FileNameVo?
    ): CsErrorReportSaveDto {
        return CsErrorReportSaveDto(
            errType = createDto.errType,
            contentsId = createDto.contentsId,
            reportMemberId = createDto.reportMemberId,
            reportContents = createDto.reportContents,
            clientOs = createDto.clientOs,
            clientBrowser = createDto.clientBrowser,
            firstImgPath = firImg?.path,
            firstImgName = firImg?.name,
            secondImgPath = secImg?.path,
            secondImgName = secImg?.name,
            thirdImgPath = thrImg?.path,
            thirdImgName = thrImg?.name,
            reportStts = ReportSttsType.Submit
        )
    }
}