package com.kamcci.numberbox.app.service.mock.port.orm.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.port.orm.math.MathContentsWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import java.util.*

class MockMathContentsWriteOrmPort : MathContentsWriteOrmPort {
    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var executeCnt = 0 // 실행 횟수

    override fun saveWithLicense(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long {
        executeCnt++
        return if (contentsModifyDto.memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun saveWithSimilarSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        executeCnt++
        return if (contentsModifyDto.memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun saveWithIpsiSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        executeCnt++
        return if (contentsModifyDto.memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun saveTransContents(
        orgContentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto
    ): Long {
        executeCnt++
        return if (contentsModifyDto.memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun incrementTransConCntById(id: Long): Long {
        return if (id == FAIL_ID) 0L else 1L
    }

    override fun updateWithLicense(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long {
        executeCnt++
        return if (contentsId == FAIL_ID) 0L else 1L
    }

    override fun updateWithSimilarSrc(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        executeCnt++
        return if (contentsId == FAIL_ID) 0L else 1L
    }

    override fun updateWithIpsiSrc(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        executeCnt++
        return if (contentsId == FAIL_ID) 0L else 1L
    }

    override fun updateTransContents(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto
    ): Long {
        executeCnt++
        return if (contentsId == FAIL_ID) 0L else 1L
    }

    override fun updateContentsClassifyType(
        contentsId: Long,
        memberId: UUID,
        contentsClassifyType: ContentsClassifyType
    ): Long {
        executeCnt++
        return if (contentsId == FAIL_ID) 0L else 1L
    }

    override fun updateContentsClassifyType(memberId: UUID, contentsClassifyType: ContentsClassifyType): Long {
        executeCnt++
        return if (memberId == FAIL_MEMBER_ID) 0L else 1L
    }
}