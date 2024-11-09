package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.math

import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsLicenseEntity
import java.time.LocalDateTime

object MathContentsLicenseFactory {
    fun getSaveEntity(licenseModifyDto: MathConLicenseModifyDto): MathContentsLicenseEntity {
        // 타임 스탬프
        val now = LocalDateTime.now()

        val entity = MathContentsLicenseEntity().apply {
            shareStts = licenseModifyDto.shareStts
            onlineLicStts = licenseModifyDto.onlineLicStts
            perLicStts = licenseModifyDto.perLicStts
            perLicPrice = 0
            entLicStts = licenseModifyDto.entLicStts
            entLicPrice = 0
            sysCreateDate = now
            sysUpdateDate = now
        }

        return entity
    }

    fun getUpdateEntity(
        orgLicenseEntity: MathContentsLicenseEntity,
        licenseModifyDto: MathConLicenseModifyDto
    ): MathContentsLicenseEntity {
        // 타임 스탬프
        val now = LocalDateTime.now()

        val entity = orgLicenseEntity.apply {
            shareStts = licenseModifyDto.shareStts
            onlineLicStts = licenseModifyDto.onlineLicStts
            perLicStts = licenseModifyDto.perLicStts
            perLicPrice = 0
            entLicStts = licenseModifyDto.entLicStts
            entLicPrice = 0
            sysUpdateDate = now
        }

        return entity
    }

}