package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.vo.math.*
import com.kamcci.numberbox.app.port.orm.math.MathContentsReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathCategoryUnitEntity.mathCategoryUnitEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsEntity.mathContentsEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsIpsiSrcEntity.mathContentsIpsiSrcEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsLicenseEntity.mathContentsLicenseEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsSimilarSrcEntity.mathContentsSimilarSrcEntity
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
    override fun readById(contentsId: Long): MathContentsVo? =
        findBy(contentsId = contentsId, contentsIdList = null, pageReq = null)
            .fetchOne()

    override fun readById(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo> =
        findBy(contentsId = null, contentsIdList = contentsId, pageReq = pageReq)
            .fetch()

    // 검색조건에 따른 동적 쿼리
    private fun findBy(
        contentsId: Long?,
        contentsIdList: List<Long>?,
        pageReq: PageRequest?
    ): JPAQuery<MathContentsVo> {
        // id 조건에 따른 동적 쿼리 조건
        val idCondition =
            when {
                contentsId != null -> mathContentsEntity.id.eq(contentsId)

                contentsIdList != null -> mathContentsEntity.id.`in`(contentsIdList)

                else -> null
            }

        // 쿼리 생성
        val qry: JPAQuery<MathContentsVo> =
            queryFactory
                .select(mathContentsExpression.ceMathContentsVo())
                .from(mathContentsEntity)
                .innerJoin(mathCategoryUnitEntity)
                .on(mathContentsEntity.unitId.eq(mathCategoryUnitEntity.id))
                .innerJoin(memberProfileEntity)
                .on(mathContentsEntity.memberId.eq(memberProfileEntity.memberId))
                .leftJoin(mathContentsLicenseEntity)
                .on(mathContentsEntity.id.eq(mathContentsLicenseEntity.mathContents.id))
                .where(
                    idCondition,
                    mathContentsEntity.svcPosbStts.eq(ContentsSvcPosbSttsType.Release)
                )

        // 페이징 조건 추가
        if (pageReq != null) {
            qry.offset(pageReq.getOffset())
                .limit(pageReq.volume)
                .orderBy(mathContentsEntity.sysCreateDate.desc())
        }

        // 쿼리 반환
        return qry
    }


    override fun readDetailByIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo? {
        return detailCommonQuery(memberId)
            .where(
                mathContentsEntity.id.eq(id),
                mathContentsEntity.memberId.eq(memberId),
            )
            .fetchOne()
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        // 서비스 가능 상태 조건
        val svcPosbSttsCondition =
            if (svcPosbSttsType != null) {
                mathContentsEntity.svcPosbStts.eq(svcPosbSttsType)
            } else {
                svcPosbSttsType
            }

        return detailCommonQuery(memberId)
            .where(
                mathContentsEntity.memberId.eq(memberId),
                svcPosbSttsCondition
            )
            .offset(pageReq.getOffset())
            .limit(pageReq.volume)
            .orderBy(mathContentsEntity.sysCreateDate.desc())
            .fetch()
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        myMemberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        // 서비스 가능 상태 조건
        val svcPosbSttsCondition =
            if (svcPosbSttsType != null) {
                mathContentsEntity.svcPosbStts.eq(svcPosbSttsType)
            } else {
                svcPosbSttsType
            }

        return detailCommonQuery(myMemberId)
            .where(
                mathContentsEntity.memberId.eq(memberId),
                svcPosbSttsCondition
            )
            .offset(pageReq.getOffset())
            .limit(pageReq.volume)
            .orderBy(mathContentsEntity.sysCreateDate.desc())
            .fetch()
    }

    override fun readDetailByUnitId(
        memberId: UUID,
        unitId: List<Int>,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return detailCommonQuery(memberId)
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

    private fun detailCommonQuery(memberId: UUID): JPAQuery<MathContentsDetailVo> {
        return queryFactory
            .select(mathContentsExpression.ceMathContentsDetailVo(memberId))
            .from(mathContentsEntity)
            .innerJoin(mathCategoryUnitEntity)
            .on(mathContentsEntity.unitId.eq(mathCategoryUnitEntity.id))
            .innerJoin(memberProfileEntity)
            .on(mathContentsEntity.memberId.eq(memberProfileEntity.memberId))
            .leftJoin(mathContentsLicenseEntity)
            .on(mathContentsEntity.id.eq(mathContentsLicenseEntity.mathContents.id))
    }

    override fun readInHouseContentsById(contentsId: Long): MathInHouseContentsVo? {
        return queryFactory
            .select(mathContentsExpression.ceMathInHouseContentsVo())
            .from(mathContentsEntity)
            .innerJoin(mathCategoryUnitEntity)
            .on(mathContentsEntity.unitId.eq(mathCategoryUnitEntity.id))
            .innerJoin(memberProfileEntity)
            .on(mathContentsEntity.memberId.eq(memberProfileEntity.memberId))
            .leftJoin(mathContentsSimilarSrcEntity)
            .on(mathContentsEntity.id.eq(mathContentsSimilarSrcEntity.mathContents.id))
            .where(mathContentsEntity.id.eq(contentsId))
            .fetchOne()
    }

    override fun readIpsiContentsById(contentsId: Long): MathIpsiContentsVo? {
        return queryFactory
            .select(mathContentsExpression.ceMathIpsiContentsVo())
            .from(mathContentsEntity)
            .innerJoin(mathCategoryUnitEntity)
            .on(mathContentsEntity.unitId.eq(mathCategoryUnitEntity.id))
            .innerJoin(memberProfileEntity)
            .on(mathContentsEntity.memberId.eq(memberProfileEntity.memberId))
            .leftJoin(mathContentsIpsiSrcEntity)
            .on(mathContentsEntity.id.eq(mathContentsIpsiSrcEntity.mathContents.id))
            .where(mathContentsEntity.id.eq(contentsId))
            .fetchOne()
    }

    override fun readTransContCntById(id: Long): Int? {
        return queryFactory
            .select(mathContentsEntity.transConCnt)
            .from(mathContentsEntity)
            .where(mathContentsEntity.id.eq(id))
            .fetchOne()
    }

    override fun readContentsOnly(contentsId: Long, memberId: UUID): MathContentsOnlyVo? {
        return queryFactory
            .select(mathContentsExpression.ceMathContentsOnlyVo(memberId))
            .from(mathContentsEntity)
            .innerJoin(mathCategoryUnitEntity)
            .on(mathContentsEntity.unitId.eq(mathCategoryUnitEntity.id))
            .innerJoin(memberProfileEntity)
            .on(mathContentsEntity.memberId.eq(memberProfileEntity.memberId))
            .where(mathContentsEntity.id.eq(contentsId))
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