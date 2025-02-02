package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.math.MathContentsWriteCase
import java.util.*

class MockMathContentsWriteCase : MathContentsWriteCase {
    override fun createUserCustomContents(
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long {
        return 1L
    }

    override fun createInHouseContents(
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        return 1L
    }

    override fun createTransContents(orgContentsId: Long, contentsModifyDto: MathContentsModifyDto): Long {
        return 1L
    }

    override fun createIpsiContents(
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        return 1L
    }

    override fun updateUserCustomContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ) {
        if (contentsId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun updateInHouseContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ) {
        if (contentsId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun updateTransContents(contentsId: Long, contentsModifyDto: MathContentsModifyDto) {
        if (contentsId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun updateIpsiContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ) {
        if (contentsId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun delete(contentsId: Long, memberId: UUID) {
        if (contentsId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun delete(memberId: UUID) {
        if (memberId == EXCEPTION_MEMBER_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

}