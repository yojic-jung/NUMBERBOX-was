package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType.NotRelease
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType.Release
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathContentsWriteOrmPort
import com.kamcci.numberbox.app.port.orm.math.MathContentsReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsWriteUseCase
import java.util.*

@UseCase
class MathContentsWriteService(
    private val mathContentsReadOrmPort: MathContentsReadOrmPort,
    private val mathContentsWriteOrmPort: MathContentsWriteOrmPort,
) : MathContentsWriteUseCase {
    companion object {
        const val NOT_EXIST_CONTENTS = "해당 수학문제가 존재하지 않습니다."
    }

    // 사용자 수학문제 등록
    @TXExecute
    override fun createUserCustomContents(
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long {
        // 수학문제 저장 및 저작권(사용자 수학문제는 즉시 출시)
        return mathContentsWriteOrmPort.saveWithLicense(Release, contentsModifyDto, licenseCreateDto)
    }

    // 자체 수학문제 등록
    @TXExecute
    override fun createInHouseContents(
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        // 수학문제 및 유사문제 출처 저장(자체제작 문제는 검수 진행하므로 미출시)
        val contentsId = mathContentsWriteOrmPort.saveWithSimilarSrc(NotRelease, contentsModifyDto, similarSrcDto)
        return contentsId
    }

    // 변형문제 등록
    @TXExecute
    override fun createTransContents(orgContentsId: Long, contentsModifyDto: MathContentsModifyDto): Long {
        // orgContentsId 존재여부 체크
        if (!mathContentsReadOrmPort.existById(orgContentsId)) throw BusinessValidException(NOT_EXIST_CONTENTS)

        // 수학문제 저장
        val contentsId =
            mathContentsWriteOrmPort.saveTransContents(orgContentsId, Release, contentsModifyDto)

        // 원본문제의 변형문제수 +1
        val transConCnt = mathContentsReadOrmPort.readTransContCntById(orgContentsId)!!
        mathContentsWriteOrmPort.updateTransConCntById(orgContentsId, transConCnt + 1)
        return contentsId
    }

    // 입시 문제 등록
    @TXExecute
    override fun createIpsiContents(
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        // 수학문제 및 입시 출처 정보 저장(입시 문제는 즉시 출시)
        val contentsId = mathContentsWriteOrmPort.saveWithIpsiSrc(Release, contentsModifyDto, ipsiSrcCreateDto)
        return contentsId
    }

    @TXExecute
    override fun updateUserCustomContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Boolean {
        // 수학문제 저장 및 저작권(사용자 수학문제는 즉시 출시)
        return mathContentsWriteOrmPort.updateWithLicense(contentsId, Release, contentsModifyDto, licenseCreateDto) > 0
    }

    @TXExecute
    override fun updateInHouseContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Boolean {
        // 수학문제 및 유사문제 출처 저장(자체제작 문제는 검수 진행하므로 미출시)
        return mathContentsWriteOrmPort.updateWithSimilarSrc(contentsId, Release, contentsModifyDto, similarSrcDto) > 0
    }

    @TXExecute
    override fun updateIpsiContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Boolean {
        // 수학문제 및 입시 출처 정보 저장(입시 문제는 즉시 출시)
        return mathContentsWriteOrmPort.updateWithIpsiSrc(contentsId, Release, contentsModifyDto, ipsiSrcCreateDto) > 0
    }

    @TXExecute
    override fun updateTransContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto
    ): Boolean {
        // 수학문제 저장
        return mathContentsWriteOrmPort.updateTransContents(contentsId, Release, contentsModifyDto) > 0
    }

    @TXExecute
    override fun delete(contentsId: Long, memberId: UUID) {
        // 수학문제 출시 상태 미출시로 변경
        mathContentsWriteOrmPort.updateSvcPosbStts(contentsId, memberId, NotRelease)
    }
}