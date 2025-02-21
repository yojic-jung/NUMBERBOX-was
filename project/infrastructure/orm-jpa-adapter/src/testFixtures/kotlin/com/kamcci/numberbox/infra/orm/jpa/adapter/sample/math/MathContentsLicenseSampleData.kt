package com.kamcci.numberbox.infra.orm.jpa.adapter.sample.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsLicenseEntity
import java.time.LocalDateTime

object MathContentsLicenseSampleData {
    fun getSaveEntity(): MathContentsLicenseEntity {
        val now = LocalDateTime.now()
        return MathContentsLicenseEntity().apply {
            onlineLicStts = true
            perLicStts = true
            perLicPrice = 10000
            entLicStts = true
            entLicPrice = 10000
            shareStts = true
            sysCreateDate = now
            sysUpdateDate = now
        }
    }
}