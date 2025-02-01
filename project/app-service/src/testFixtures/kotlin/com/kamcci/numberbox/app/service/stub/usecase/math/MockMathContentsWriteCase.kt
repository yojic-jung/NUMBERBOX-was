package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
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
    }

    override fun updateInHouseContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ) {
    }

    override fun updateTransContents(contentsId: Long, contentsModifyDto: MathContentsModifyDto) {
    }

    override fun updateIpsiContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ) {
    }

    override fun delete(contentsId: Long, memberId: UUID) {
    }

    override fun delete(memberId: UUID) {
    }

}