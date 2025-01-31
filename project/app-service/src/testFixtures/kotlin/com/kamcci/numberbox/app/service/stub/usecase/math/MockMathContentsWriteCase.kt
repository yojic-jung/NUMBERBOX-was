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
        TODO("Not yet implemented")
    }

    override fun createInHouseContents(
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        TODO("Not yet implemented")
    }

    override fun createTransContents(orgContentsId: Long, contentsModifyDto: MathContentsModifyDto): Long {
        TODO("Not yet implemented")
    }

    override fun createIpsiContents(
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        TODO("Not yet implemented")
    }

    override fun updateUserCustomContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ) {
        TODO("Not yet implemented")
    }

    override fun updateInHouseContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ) {
        TODO("Not yet implemented")
    }

    override fun updateTransContents(contentsId: Long, contentsModifyDto: MathContentsModifyDto) {
        TODO("Not yet implemented")
    }

    override fun updateIpsiContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ) {
        TODO("Not yet implemented")
    }

    override fun delete(contentsId: Long, memberId: UUID) {
        TODO("Not yet implemented")
    }

    override fun delete(memberId: UUID) {
        TODO("Not yet implemented")
    }

}