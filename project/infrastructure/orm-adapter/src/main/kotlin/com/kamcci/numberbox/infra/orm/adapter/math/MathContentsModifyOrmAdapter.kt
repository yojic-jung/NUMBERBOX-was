package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsCreateDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.port.repository.math.MathContentsModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsEntity.mathContentsEntity
import com.kamcci.numberbox.infra.orm.factory.math.MathContentsFactory
import com.kamcci.numberbox.infra.orm.factory.math.MathContentsIpsiFactory
import com.kamcci.numberbox.infra.orm.factory.math.MathContentsLicenseFactory
import com.kamcci.numberbox.infra.orm.factory.math.MathContentsSimilarSrcFactory
import org.springframework.stereotype.Repository

@Repository
class MathContentsModifyOrmAdapter : MathContentsModifyOrmPort, BaseRepository() {
    override fun saveWithLicense(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsCreateDto: MathContentsCreateDto,
        licenseCreateDto: MathConLicenseCreateDto
    ): Long {
        // 수학문제 엔티티 생성
        val contentsEntity = MathContentsFactory.getSaveEntity(svcPosbSttsType, contentsCreateDto)
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
        contentsCreateDto: MathContentsCreateDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long {
        // 수학문제 엔티티 생성
        val contentsEntity = MathContentsFactory.getSaveEntity(svcPosbSttsType, contentsCreateDto)
        // 유사문제 출처 정보 엔티티 생성
        val similarSrcEntity = MathContentsSimilarSrcFactory.getSaveEntity(contentsEntity.id, similarSrcDto)
        contentsEntity.mathContentsSimilarSrc = mutableListOf(similarSrcEntity)

        // 영속화
        em.persist(contentsEntity)
        return contentsEntity.id
    }

    override fun saveWithIpsiSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsCreateDto: MathContentsCreateDto,
        ipsiSrcCreateDto: MathConIpsiSrcCreateDto
    ): Long {
        // 수학문제 엔티티 생성
        val contentsEntity = MathContentsFactory.getSaveEntity(svcPosbSttsType, contentsCreateDto)
        // 입시문제 출처 정보 엔티티 생성
        val similarSrcEntity = MathContentsIpsiFactory.getSaveEntity(ipsiSrcCreateDto)
        contentsEntity.mathContentsIpsiSrc = mutableListOf(similarSrcEntity)

        // 영속화
        em.persist(contentsEntity)
        return contentsEntity.id
    }

    override fun saveForTransContents(
        orgContentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsCreateDto: MathContentsCreateDto
    ): Long {
        val entity = MathContentsFactory.getSaveEntity(svcPosbSttsType, contentsCreateDto)
        entity.orgContentsId = orgContentsId // 원본문제 id 셋팅
        em.persist(entity)
        return entity.id
    }

    override fun updateTransConCntById(id: Long, transContCnt: Int): Boolean {
        return queryFactory
            .update(mathContentsEntity)
            .set(mathContentsEntity.transConCnt, transContCnt)
            .where(mathContentsEntity.id.eq(id))
            .execute() > 0
    }
}