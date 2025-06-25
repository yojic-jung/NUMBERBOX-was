package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.port.orm.math.MathContentsWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsEntity.mathContentsEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.math.MathContentsFactory
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.math.MathContentsIpsiFactory
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.math.MathContentsLicenseFactory
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.math.MathContentsSimilarSrcFactory
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common.CacheNames
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathContentsWriteRepository : MathContentsWriteOrmPort, BaseRepository() {
    override fun saveWithLicense(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long {
        // 수학문제 엔티티 생성
        val contentsEntity = MathContentsFactory.getSaveEntity(svcPosbSttsType, contentsModifyDto)
        contentsEntity.contentsClassify = ContentsClassifyType.UserCustom

        // 저작권 정보 엔티티 생성
        val licenseEntity = MathContentsLicenseFactory.getSaveEntity(licenseCreateDto)

        // 연관관계 설정
        licenseEntity.mathContents = contentsEntity
        contentsEntity.mathContentsLicenses.add(licenseEntity)

        // 영속화
        em.persist(contentsEntity)
        return contentsEntity.id
    }

    override fun saveWithSimilarSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        // 수학문제 엔티티 생성
        val contentsEntity = MathContentsFactory.getSaveEntity(svcPosbSttsType, contentsModifyDto)
        contentsEntity.contentsClassify = ContentsClassifyType.InHouse

        // 유사문제 출처 정보 엔티티 생성
        val similarSrcEntity = MathContentsSimilarSrcFactory.getSaveEntity(contentsEntity.id, similarSrcDto)
        contentsEntity.mathContentsSimilarSrc = mutableListOf(similarSrcEntity)

        // 영속화
        em.persist(contentsEntity)
        return contentsEntity.id
    }

    override fun saveWithIpsiSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        // 수학문제 엔티티 생성
        val contentsEntity = MathContentsFactory.getSaveEntity(svcPosbSttsType, contentsModifyDto)
        contentsEntity.contentsClassify = ContentsClassifyType.Ipsi

        // 입시문제 출처 정보 엔티티 생성
        val similarSrcEntity = MathContentsIpsiFactory.getSaveEntity(ipsiSrcCreateDto)
        contentsEntity.mathContentsIpsiSrc = mutableListOf(similarSrcEntity)

        // 영속화
        em.persist(contentsEntity)
        return contentsEntity.id
    }

    override fun saveTransContents(
        orgContentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto
    ): Long {
        val entity = MathContentsFactory.getSaveEntity(svcPosbSttsType, contentsModifyDto)
        entity.contentsClassify = ContentsClassifyType.Modified
        entity.orgContentsId = orgContentsId // 원본문제 id 셋팅
        em.persist(entity)
        return entity.id
    }

    override fun incrementTransConCntById(id: Long): Long {
        return queryFactory
            .update(mathContentsEntity)
            .set(mathContentsEntity.transConCnt, mathContentsEntity.transConCnt.add(1))
            .where(mathContentsEntity.id.eq(id))
            .execute()
    }

    override fun updateWithLicense(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long {
        // 수학문제 엔티티 수정
        val orgEntity = em.find(MathContentsEntity::class.java, contentsId)
        val contentsEntity = MathContentsFactory.getUpdtEntity(orgEntity, svcPosbSttsType, contentsModifyDto)

        // 저작권 정보 엔티티 수정
        val orgLicenseEntity = orgEntity.mathContentsLicenses[0]
        MathContentsLicenseFactory.getUpdateEntity(orgLicenseEntity, licenseCreateDto)

        // 영속화
        em.persist(contentsEntity)
        return 1L
    }

    override fun updateWithSimilarSrc(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        // 수학문제 엔티티 수정
        val orgEntity = em.find(MathContentsEntity::class.java, contentsId)
        val contentsEntity = MathContentsFactory.getUpdtEntity(orgEntity, svcPosbSttsType, contentsModifyDto)

        // 유사문제 출처 정보 엔티티 수정
        val orgSimilarSrcEntity = orgEntity.mathContentsSimilarSrc[0]
        MathContentsSimilarSrcFactory.getUpdateEntity(orgSimilarSrcEntity, similarSrcDto)

        // 영속화
        em.persist(contentsEntity)
        return 1L
    }

    override fun updateWithIpsiSrc(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long {
        // 수학문제 엔티티 수정
        val orgEntity = em.find(MathContentsEntity::class.java, contentsId)
        val contentsEntity = MathContentsFactory.getUpdtEntity(orgEntity, svcPosbSttsType, contentsModifyDto)

        // 입시문제 출처 정보 엔티티 수정
        val orgIpsiSrcEntity = orgEntity.mathContentsIpsiSrc[0]
        MathContentsIpsiFactory.getUpdateEntity(orgIpsiSrcEntity, ipsiSrcCreateDto)

        // 영속화
        em.persist(contentsEntity)
        return 1L
    }

    override fun updateTransContents(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto
    ): Long {
        val orgEntity = em.find(MathContentsEntity::class.java, contentsId)
        val contentsEntity = MathContentsFactory.getUpdtEntity(orgEntity, svcPosbSttsType, contentsModifyDto)
        em.persist(contentsEntity)
        return 1L
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
        return queryFactory
            .update(mathContentsEntity)
            .set(mathContentsEntity.contentsClassify, contentsClassifyType)
            .where(
                mathContentsEntity.id.eq(contentsId),
                mathContentsEntity.memberId.eq(memberId),
            )
            .execute()
    }

    override fun updateContentsClassifyType(memberId: UUID, contentsClassifyType: ContentsClassifyType): Long {
        return queryFactory
            .update(mathContentsEntity)
            .set(mathContentsEntity.contentsClassify, contentsClassifyType)
            .where(mathContentsEntity.memberId.eq(memberId))
            .execute()
    }
}