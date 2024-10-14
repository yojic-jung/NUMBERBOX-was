package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.app.port.repository.math.MathContentsReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsEntity.mathContentsEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsLicenseEntity.mathContentsLicenseEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsLikeEntity.mathContentsLikeEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsRepositoryEntity.mathContentsRepositoryEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathUnitInfoEntity.mathUnitInfoEntity
import com.kamcci.numberbox.infra.orm.entity.member.QMemberProfileEntity.memberProfileEntity
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathContentsReadOrmAdapter : MathContentsReadOrmPort, BaseRepository() {
    override fun findByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest): List<MathContentsVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    MathContentsVo::class.java,
                    mathContentsEntity.id,
                    mathContentsEntity.unitId,
                    mathContentsEntity.typeId,
                    mathContentsEntity.contents,
                    mathContentsEntity.contentsImg,
                    mathContentsEntity.solution,
                    mathContentsEntity.solutionImg,
                    mathContentsEntity.imgPath,
                    mathContentsEntity.solutionImgPath,
                    mathContentsEntity.firNo,
                    mathContentsEntity.secNo,
                    mathContentsEntity.thrNo,
                    mathContentsEntity.fourNo,
                    mathContentsEntity.fifNo,
                    mathContentsEntity.multiChoiceType,
                    mathContentsEntity.answer,
                    mathContentsEntity.choiceAnswer,
                    mathContentsEntity.quesLevel,
                    mathContentsEntity.ansExistStts,
                    mathContentsEntity.svcPosbStts,
                    mathContentsEntity.contentsClassify,
                    mathContentsEntity.orgContentsNo,
                    mathContentsEntity.transConCnt,
                    mathContentsEntity.sysCreateDate,
                    mathContentsEntity.sysUpdateDate,
                    mathContentsLicenseEntity.onlineLicStts,
                    mathContentsLicenseEntity.perLicStts,
                    mathContentsLicenseEntity.perLicPrice,
                    mathContentsLicenseEntity.entLicStts,
                    mathContentsLicenseEntity.entLicPrice,
                    mathContentsLicenseEntity.shareStts,
                    memberProfileEntity.id,
                    memberProfileEntity.nickname,
                    memberProfileEntity.profileImgName,
                    memberProfileEntity.profileImgPath,
                    mathUnitInfoEntity.subject,
                    mathUnitInfoEntity.firUnit,
                    mathUnitInfoEntity.secUnit,
                    mathUnitInfoEntity.thrUnit,
                    JPAExpressions
                        .selectOne()
                        .from(mathContentsRepositoryEntity)
                        .where(
                            mathContentsRepositoryEntity.id.contentsId.eq(mathContentsEntity.id),
                            mathContentsRepositoryEntity.id.memberId.eq(memberId),
                        )
                        .exists(),
                    JPAExpressions
                        .selectOne()
                        .from(mathContentsLikeEntity)
                        .where(
                            mathContentsLikeEntity.id.contentsId.eq(mathContentsEntity.id),
                            mathContentsLikeEntity.id.memberId.eq(memberId),
                        )
                        .exists()
                )
            )
            .from(mathContentsEntity)
            .innerJoin(mathUnitInfoEntity)
            .on(mathContentsEntity.unitId.eq(mathUnitInfoEntity.id))
            .innerJoin(memberProfileEntity)
            .on(mathContentsEntity.memberId.eq(memberProfileEntity.memberId))
            .leftJoin(mathContentsLicenseEntity)
            .on(mathContentsEntity.id.eq(mathContentsLicenseEntity.contentsId))
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
            .offset(pageReq.getOffset())
            .limit(pageReq.volume)
            .orderBy(mathContentsEntity.quesLevel.desc())
            .fetch()
    }

    override fun countByUnitId(unitId: List<Int>): Long {
        return queryFactory
            .select(mathContentsEntity.id.count())
            .from(mathContentsEntity)
            .leftJoin(mathContentsLicenseEntity)
            .on(mathContentsEntity.id.eq(mathContentsLicenseEntity.contentsId))
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