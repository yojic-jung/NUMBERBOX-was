package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsCreateDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType.*
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType.NotRelease
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType.Release
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.math.MathContentsModifyOrmPort
import com.kamcci.numberbox.app.port.repository.math.MathContentsReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsModifyUseCase

@UseCase
class MathContentsModifyService(
    private val mathContentsReadOrmPort: MathContentsReadOrmPort,
    private val mathContentsModifyOrmPort: MathContentsModifyOrmPort,
) : MathContentsModifyUseCase {
    companion object {
        const val NOT_IN_HOUSE_CONTENTS = "자체 제작 수학문제가 아닌 경우 등록이 불가합니다."
        const val NOT_USER_CUSTOM_CONTENTS = "사용자 제작 수학문제가 아닌 경우 등록이 불가합니다."
        const val NOT_MODIFIED_CONTENTS = "변형문제가 아닌 경우 등록이 불가합니다."
        const val NOT_IPSI_CONTENTS = "입시문제가 아닌 경우 등록이 불가합니다."
        const val NOT_EXIST_CONTENTS = "해당 수학문제가 존재하지 않습니다."
    }

    // 사용자 수학문제 등록
    @TXExecute
    override fun createUserCustomContents(
        contentsCreateDto: MathContentsCreateDto,
        licenseCreateDto: MathConLicenseCreateDto
    ): Long {
        // 사용자 제작 문제 아닌 경우 등록 불가
        if (contentsCreateDto.contentsClassify != UserCustom) throw BusinessValidException(NOT_IN_HOUSE_CONTENTS)

        // 수학문제 저장 및 저작권(사용자 수학문제는 즉시 출시)
        return mathContentsModifyOrmPort.saveWithLicense(Release, contentsCreateDto, licenseCreateDto)
    }

    // 자체 수학문제 등록
    @TXExecute
    override fun createInHouseContents(
        contentsCreateDto: MathContentsCreateDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        // 자체 제작 문제 아닌 경우 등록 불가
        if (contentsCreateDto.contentsClassify != InHouse) throw BusinessValidException(NOT_USER_CUSTOM_CONTENTS)

        // 수학문제 및 유사문제 출처 저장(자체제작 문제는 검수 진행하므로 미출시)
        val contentsId = mathContentsModifyOrmPort.saveWithSimilarSrc(NotRelease, contentsCreateDto, similarSrcDto)
        return contentsId
    }

    // 변형문제 등록
    @TXExecute
    override fun createTransContents(orgContentsId: Long, contentsCreateDto: MathContentsCreateDto): Long {
        // 변형 문제 아닌 경우 등록 불가
        if (contentsCreateDto.contentsClassify != Modified) throw BusinessValidException(NOT_MODIFIED_CONTENTS)

        // 수학문제 저장
        val contentsId = mathContentsModifyOrmPort.saveForTransContents(orgContentsId, Release, contentsCreateDto)

        // 원본문제의 변형문제수 +1
        val transConCnt = mathContentsReadOrmPort.findTransContCntById(orgContentsId)
            ?: throw BusinessValidException(NOT_EXIST_CONTENTS)
        mathContentsModifyOrmPort.updateTransConCntById(orgContentsId, transConCnt + 1)
        return contentsId
    }

    // 입시 문제 등록
    @TXExecute
    override fun createIpsiContents(
        contentsCreateDto: MathContentsCreateDto,
        ipsiSrcCreateDto: MathConIpsiSrcCreateDto
    ): Long {
        // 입시 문제 아닌 경우 등록 불가
        if (contentsCreateDto.contentsClassify != Ipsi) throw BusinessValidException(NOT_IPSI_CONTENTS)

        // 수학문제 및 입시 출처 정보 저장(입시 문제는 즉시 출시)
        val contentsId = mathContentsModifyOrmPort.saveWithIpsiSrc(Release, contentsCreateDto, ipsiSrcCreateDto)
        return contentsId
    }
}