package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.vo.math.MathContentsDetailVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.app.port.repository.math.MathContentsReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsEntity.mathContentsEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsLicenseEntity.mathContentsLicenseEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathUnitInfoEntity.mathUnitInfoEntity
import com.kamcci.numberbox.infra.orm.entity.member.QMemberProfileEntity.memberProfileEntity
import com.kamcci.numberbox.infra.orm.util.math.MathContentsExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathContentsReadOrmAdapter(
    private val mathContentsExpression: MathContentsExpression
) : MathContentsReadOrmPort, BaseRepository() {
    override fun findByContentsId(contentsId: Long): MathContentsVo? =
        findBy(contentsId = contentsId, contentsIdList = null, profileId = null, pageReq = null)
            .fetchOne()

    override fun findByContentsId(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo> =
        findBy(contentsId = null, contentsIdList = contentsId, profileId = null, pageReq = pageReq)
            .fetch()

    override fun findByProfileId(profileId: Long, pageReq: PageRequest): List<MathContentsVo> =
        findBy(contentsId = null, contentsIdList = null, profileId = profileId, pageReq = pageReq)
            .fetch()

    // 검색조건에 따른 동적 쿼리
    private fun findBy(
        contentsId: Long?,
        contentsIdList: List<Long>?,
        profileId: Long?,
        pageReq: PageRequest?
    ): JPAQuery<MathContentsVo> {
        // id 검색 조건
        val idCondition =
            when {
                contentsId != null -> mathContentsEntity.id.eq(contentsId)

                contentsIdList != null -> mathContentsEntity.id.`in`(contentsIdList)

                profileId != null -> memberProfileEntity.id.eq(profileId)

                else -> null
            }

        val qry: JPAQuery<MathContentsVo> =
            queryFactory
                .select(mathContentsExpression.ceMathContentsVo())
                .from(mathContentsEntity)
                .innerJoin(mathUnitInfoEntity)
                .on(mathContentsEntity.unitId.eq(mathUnitInfoEntity.id))
                .innerJoin(memberProfileEntity)
                .on(mathContentsEntity.memberId.eq(memberProfileEntity.memberId))
                .leftJoin(mathContentsLicenseEntity)
                .on(mathContentsEntity.id.eq(mathContentsLicenseEntity.mathContents.id))
                .where(idCondition)

        if (pageReq != null) {
            qry.offset(pageReq.getOffset())
                .limit(pageReq.volume)
                .orderBy(mathContentsEntity.sysCreateDate.desc())
        }

        return qry

    }

    override fun findDetailByMemberId(memberId: UUID, pageReq: PageRequest): List<MathContentsDetailVo> {
        return queryFactory
            .select(mathContentsExpression.ceMathContentsDetailVo(memberId))
            .from(mathContentsEntity)
            .innerJoin(mathUnitInfoEntity)
            .on(mathContentsEntity.unitId.eq(mathUnitInfoEntity.id))
            .innerJoin(memberProfileEntity)
            .on(mathContentsEntity.memberId.eq(memberProfileEntity.memberId))
            .leftJoin(mathContentsLicenseEntity)
            .on(mathContentsEntity.id.eq(mathContentsLicenseEntity.mathContents.id))
            .where(mathContentsEntity.memberId.eq(memberId))
            .offset(pageReq.getOffset())
            .limit(pageReq.volume)
            .orderBy(mathContentsEntity.quesLevel.desc())
            .fetch()
    }


    override fun findDetailByUnitId(
        memberId: UUID,
        unitId: List<Int>,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return queryFactory
            .select(mathContentsExpression.ceMathContentsDetailVo(memberId))
            .from(mathContentsEntity)
            .innerJoin(mathUnitInfoEntity)
            .on(mathContentsEntity.unitId.eq(mathUnitInfoEntity.id))
            .innerJoin(memberProfileEntity)
            .on(mathContentsEntity.memberId.eq(memberProfileEntity.memberId))
            .leftJoin(mathContentsLicenseEntity)
            .on(mathContentsEntity.id.eq(mathContentsLicenseEntity.mathContents.id))
            .where(
                mathContentsEntity.svcPosbStts.eq(ContentsSvcPosbSttsType.Release),
                mathContentsEntity.contentsClassify.eq(ContentsClassifyType.InHouse)
                    .or(
                        mathContentsEntity.contentsClassify.eq(ContentsClassifyType.UserCustom).and(
                            mathContentsLicenseEntity.shareStts.eq(Expressions.asBoolean(true))
                        )
                    ),
            )
            .offset(pageReq.getOffset())
            .limit(pageReq.volume)
            .orderBy(mathContentsEntity.quesLevel.desc())
            .fetch()
    }

    override fun findTransContCntById(id: Long): Int? {
        return queryFactory
            .select(mathContentsEntity.transConCnt)
            .from(mathContentsEntity)
            .where(mathContentsEntity.id.eq(id))
            .fetchOne()
    }


    override fun countByUnitId(unitId: List<Int>): Long {
        return queryFactory
            .select(mathContentsEntity.id.count())
            .from(mathContentsEntity)
            .leftJoin(mathContentsLicenseEntity)
            .on(mathContentsEntity.id.eq(mathContentsLicenseEntity.mathContents.id))
            .where(
                mathContentsEntity.svcPosbStts.eq(ContentsSvcPosbSttsType.Release),
                mathContentsEntity.contentsClassify.eq(ContentsClassifyType.InHouse)
                    .or(
                        mathContentsEntity.contentsClassify.eq(ContentsClassifyType.UserCustom).and(
                            mathContentsLicenseEntity.shareStts.eq(Expressions.asBoolean(true))
                        )
                    ),
                mathContentsEntity.unitId.`in`(unitId)
            )
            .fetchFirst()
    }

    override fun existById(id: Long): Boolean {
        return queryFactory
            .selectOne()
            .from(mathContentsEntity)
            .where(mathContentsEntity.id.eq(id))
            .fetchFirst() != null
    }
}