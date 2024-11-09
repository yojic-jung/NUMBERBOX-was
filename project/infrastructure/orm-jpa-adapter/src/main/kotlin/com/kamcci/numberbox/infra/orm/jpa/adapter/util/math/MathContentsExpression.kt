package com.kamcci.numberbox.infra.orm.jpa.adapter.util.math

import com.kamcci.numberbox.app.domain.vo.math.*
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathCategoryUnitEntity.mathCategoryUnitEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsEntity.mathContentsEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsIpsiSrcEntity.mathContentsIpsiSrcEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsLicenseEntity.mathContentsLicenseEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsLikeEntity.mathContentsLikeEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsRepositoryEntity.mathContentsRepositoryEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsSimilarSrcEntity.mathContentsSimilarSrcEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberProfileEntity.memberProfileEntity
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPAExpressions
import org.springframework.stereotype.Component
import java.util.*

@Component
class MathContentsExpression {

    fun ceMathContentsVo(): ConstructorExpression<MathContentsVo> =
        Projections.constructor(
            MathContentsVo::class.java,
            mathContentsEntity.id,
            mathContentsEntity.memberId,
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
            mathContentsEntity.orgContentsId,
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
            mathCategoryUnitEntity.subject,
            mathCategoryUnitEntity.firUnit,
            mathCategoryUnitEntity.secUnit,
            mathCategoryUnitEntity.thrUnit,
        )

    fun ceMathInHouseContentsVo(): ConstructorExpression<MathInHouseContentsVo> =
        Projections.constructor(
            MathInHouseContentsVo::class.java,
            mathContentsEntity.id,
            mathContentsEntity.memberId,
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
            mathContentsEntity.orgContentsId,
            mathContentsEntity.transConCnt,
            mathContentsEntity.sysCreateDate,
            mathContentsEntity.sysUpdateDate,
            mathContentsSimilarSrcEntity.orgSrcRef,
            mathContentsSimilarSrcEntity.orgSrcNo,
            mathContentsSimilarSrcEntity.orgSrcPage,
            mathContentsSimilarSrcEntity.copyrightYear,
            mathContentsSimilarSrcEntity.mathTypeClassify,
            mathContentsSimilarSrcEntity.id,
            memberProfileEntity.id,
            memberProfileEntity.nickname,
            memberProfileEntity.profileImgName,
            memberProfileEntity.profileImgPath,
            mathCategoryUnitEntity.subject,
            mathCategoryUnitEntity.firUnit,
            mathCategoryUnitEntity.secUnit,
            mathCategoryUnitEntity.thrUnit,
        )

    fun ceMathIpsiContentsVo(): ConstructorExpression<MathIpsiContentsVo> =
        Projections.constructor(
            MathIpsiContentsVo::class.java,
            mathContentsEntity.id,
            mathContentsEntity.memberId,
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
            mathContentsEntity.orgContentsId,
            mathContentsEntity.transConCnt,
            mathContentsEntity.sysCreateDate,
            mathContentsEntity.sysUpdateDate,
            mathContentsIpsiSrcEntity.paperType,
            mathContentsIpsiSrcEntity.oddQuesNum,
            mathContentsIpsiSrcEntity.evenQuesNum,
            mathContentsIpsiSrcEntity.wrongRatio,
            mathContentsIpsiSrcEntity.impYear,
            mathContentsIpsiSrcEntity.impMonth,
            mathContentsIpsiSrcEntity.manageIns,
            mathContentsIpsiSrcEntity.id,
            memberProfileEntity.id,
            memberProfileEntity.nickname,
            memberProfileEntity.profileImgName,
            memberProfileEntity.profileImgPath,
            mathCategoryUnitEntity.subject,
            mathCategoryUnitEntity.firUnit,
            mathCategoryUnitEntity.secUnit,
            mathCategoryUnitEntity.thrUnit,
        )

    fun ceMathContentsDetailVo(memberId: UUID?): ConstructorExpression<MathContentsDetailVo> =
        Projections.constructor(
            MathContentsDetailVo::class.java,
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
            mathContentsEntity.orgContentsId,
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
            mathCategoryUnitEntity.subject,
            mathCategoryUnitEntity.firUnit,
            mathCategoryUnitEntity.secUnit,
            mathCategoryUnitEntity.thrUnit,
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

    fun ceMathContentsOnlyVo(memberId: UUID?): ConstructorExpression<MathContentsOnlyVo> =
        Projections.constructor(
            MathContentsOnlyVo::class.java,
            mathContentsEntity.id,
            mathContentsEntity.memberId,
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
            mathContentsEntity.orgContentsId,
            mathContentsEntity.transConCnt,
            mathContentsEntity.sysCreateDate,
            mathContentsEntity.sysUpdateDate,
            memberProfileEntity.id,
            memberProfileEntity.nickname,
            memberProfileEntity.profileImgName,
            memberProfileEntity.profileImgPath,
            mathCategoryUnitEntity.subject,
            mathCategoryUnitEntity.firUnit,
            mathCategoryUnitEntity.secUnit,
            mathCategoryUnitEntity.thrUnit,
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
}