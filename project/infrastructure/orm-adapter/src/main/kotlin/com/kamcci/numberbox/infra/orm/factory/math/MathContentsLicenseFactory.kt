package com.kamcci.numberbox.infra.orm.factory.math

import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseCreateDto
import com.kamcci.numberbox.infra.orm.entity.math.MathContentsLicenseEntity
import java.time.LocalDateTime

object MathContentsLicenseFactory {
    fun getSaveEntity(licenseCreateDto: MathConLicenseCreateDto): MathContentsLicenseEntity {
        // 타임 스탬프
        val now = LocalDateTime.now()

        val entity = MathContentsLicenseEntity().apply {
            shareStts = licenseCreateDto.shareStts
            onlineLicStts = licenseCreateDto.onlineLicStts
            perLicStts = licenseCreateDto.perLicStts
            perLicPrice = 0
            entLicStts = licenseCreateDto.entLicStts
            entLicPrice = 0
            sysCreateDate = now
            sysUpdateDate = now
        }

        return entity
    }

}