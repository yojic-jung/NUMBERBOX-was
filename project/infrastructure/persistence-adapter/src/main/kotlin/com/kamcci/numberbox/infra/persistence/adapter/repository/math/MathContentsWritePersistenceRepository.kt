package com.kamcci.numberbox.infra.persistence.adapter.repository.math//package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.port.orm.math.MathContentsWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsWriteRepository
import com.kamcci.numberbox.infra.redis.adapter.common.CacheNames
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.*

@Primary
@Repository
class MathContentsWritePersistenceRepository(
    private val mathContentsWriteRepository: MathContentsWriteRepository
) : MathContentsWriteOrmPort, BaseRepository() {
    override fun saveWithLicense(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long {
        return mathContentsWriteRepository.saveWithLicense(svcPosbSttsType, contentsModifyDto, licenseCreateDto)
    }

    override fun saveWithSimilarSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        return mathContentsWriteRepository.saveWithSimilarSrc(svcPosbSttsType, contentsModifyDto, similarSrcDto)
    }

    override fun saveWithIpsiSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        return mathContentsWriteRepository.saveWithIpsiSrc(svcPosbSttsType, contentsModifyDto, ipsiSrcCreateDto)
    }

    override fun saveTransContents(
        orgContentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto
    ): Long {
        return mathContentsWriteRepository.saveTransContents(orgContentsId, svcPosbSttsType, contentsModifyDto)
    }

    override fun incrementTransConCntById(id: Long): Long {
        return mathContentsWriteRepository.incrementTransConCntById(id)
    }

    override fun updateWithLicense(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long {
        return mathContentsWriteRepository.updateWithLicense(
            contentsId,
            svcPosbSttsType,
            contentsModifyDto,
            licenseCreateDto
        )
    }

    override fun updateWithSimilarSrc(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        return mathContentsWriteRepository.updateWithSimilarSrc(
            contentsId,
            svcPosbSttsType,
            contentsModifyDto,
            similarSrcDto
        )
    }

    override fun updateWithIpsiSrc(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        return mathContentsWriteRepository.updateWithIpsiSrc(
            contentsId,
            svcPosbSttsType,
            contentsModifyDto,
            ipsiSrcCreateDto
        )
    }

    override fun updateTransContents(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto
    ): Long {
        return mathContentsWriteRepository.updateTransContents(contentsId, svcPosbSttsType, contentsModifyDto)
    }

    @Caching(
        evict = [
            CacheEvict(cacheNames = [CacheNames.EXIST_MATH_CONTENTS], key = "#contentsId"),
        ]
    )
    override fun updateContentsClassifyType(
        contentsId: Long,
        memberId: UUID,
        contentsClassifyType: ContentsClassifyType
    ): Long {
        return mathContentsWriteRepository.updateContentsClassifyType(contentsId, memberId, contentsClassifyType)
    }

    override fun updateContentsClassifyType(memberId: UUID, contentsClassifyType: ContentsClassifyType): Long {
        return mathContentsWriteRepository.updateContentsClassifyType(memberId, contentsClassifyType)
    }
}