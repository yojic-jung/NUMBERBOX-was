package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType.NotRelease
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType.Release
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system.construction.TXExecute
import com.kamcci.numberbox.app.domain.system.construction.UseCase
import com.kamcci.numberbox.app.port.orm.math.MathContentsWriteOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import com.kamcci.numberbox.app.usecase.math.MathContentsWriteCase
import java.util.*

@UseCase
class MathContentsWriteService(
    private val mathContentsReadCase: MathContentsReadCase,
    private val mathContentsWriteOrmPort: MathContentsWriteOrmPort,
) : MathContentsWriteCase {
    companion object {
        const val NOT_EXIST_CONTENTS = "해당 수학문제가 존재하지 않습니다."
        const val NOT_UPDATED_CONTENTS = "수학문제가 수정되지 않았습니다."
        const val NOT_DELETED_CONTENTS = "수학문제가 삭제되지 않았습니다."
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
        return mathContentsWriteOrmPort.saveWithSimilarSrc(NotRelease, contentsModifyDto, similarSrcDto)
    }

    // 변형문제 등록
    @TXExecute
    override fun createTransContents(orgContentsId: Long, contentsModifyDto: MathContentsModifyDto): Long {
        // orgContentsId 존재여부 체크
        if (!mathContentsReadCase.existById(orgContentsId)) throw BusinessInValidException(NOT_EXIST_CONTENTS)

        // 수학문제 저장
        val contentsId =
            mathContentsWriteOrmPort.saveTransContents(orgContentsId, Release, contentsModifyDto)

        // 원본문제의 변형문제수 +1
        mathContentsWriteOrmPort.incrementTransConCntById(orgContentsId)
        return contentsId
    }

    // 입시 문제 등록
    @TXExecute
    override fun createIpsiContents(
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        // 수학문제 및 입시 출처 정보 저장(입시 문제는 즉시 출시)
        return mathContentsWriteOrmPort.saveWithIpsiSrc(Release, contentsModifyDto, ipsiSrcCreateDto)
    }

    @TXExecute
    override fun updateUserCustomContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ) {
        // 수학문제 저장 및 저작권(사용자 수학문제는 즉시 출시)
        mathContentsWriteOrmPort.updateWithLicense(contentsId, Release, contentsModifyDto, licenseCreateDto).let {
            if (it != 1L) throw BusinessInValidException(NOT_UPDATED_CONTENTS)
        }
    }

    @TXExecute
    override fun updateInHouseContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ) {
        // 수학문제 및 유사문제 출처 저장(자체제작 문제는 검수 진행하므로 미출시)
        mathContentsWriteOrmPort.updateWithSimilarSrc(contentsId, Release, contentsModifyDto, similarSrcDto).let {
            if (it != 1L) throw BusinessInValidException(NOT_UPDATED_CONTENTS)
        }
    }

    @TXExecute
    override fun updateIpsiContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ) {
        // 수학문제 및 입시 출처 정보 저장(입시 문제는 즉시 출시)
        mathContentsWriteOrmPort.updateWithIpsiSrc(contentsId, Release, contentsModifyDto, ipsiSrcCreateDto)
            .let {
                if (it != 1L) throw BusinessInValidException(NOT_UPDATED_CONTENTS)
            }
    }

    @TXExecute
    override fun updateTransContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto
    ) {
        // 수학문제 저장
        mathContentsWriteOrmPort.updateTransContents(contentsId, Release, contentsModifyDto).let {
            if (it != 1L) throw BusinessInValidException(NOT_UPDATED_CONTENTS)
        }
    }

    @TXExecute
    override fun delete(contentsId: Long, memberId: UUID) {
        // 수학문제 출시 상태 미출시로 변경
        mathContentsWriteOrmPort.updateContentsClassifyType(contentsId, memberId, ContentsClassifyType.Deleted).let {
            if (it != 1L) throw BusinessInValidException(NOT_DELETED_CONTENTS)
        }
    }

    @TXExecute
    override fun delete(memberId: UUID) {
        // 수학문제 출시 상태 미출시로 변경
        mathContentsWriteOrmPort.updateContentsClassifyType(memberId, ContentsClassifyType.Deleted).let {
            if (it != 1L) throw BusinessInValidException(NOT_DELETED_CONTENTS)
        }
    }
}