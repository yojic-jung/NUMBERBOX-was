package com.kamcci.numberbox.infra.orm.util.math

import com.kamcci.numberbox.app.domain.vo.math.MathContentsDetailVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsEntity.mathContentsEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsLicenseEntity.mathContentsLicenseEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsLikeEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsRepositoryEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathUnitInfoEntity.mathUnitInfoEntity
import com.kamcci.numberbox.infra.orm.entity.member.QMemberProfileEntity.memberProfileEntity
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
                .from(QMathContentsRepositoryEntity.mathContentsRepositoryEntity)
                .where(
                    QMathContentsRepositoryEntity.mathContentsRepositoryEntity.id.contentsId.eq(mathContentsEntity.id),
                    QMathContentsRepositoryEntity.mathContentsRepositoryEntity.id.memberId.eq(memberId),
                )
                .exists(),
            JPAExpressions
                .selectOne()
                .from(QMathContentsLikeEntity.mathContentsLikeEntity)
                .where(
                    QMathContentsLikeEntity.mathContentsLikeEntity.id.contentsId.eq(mathContentsEntity.id),
                    QMathContentsLikeEntity.mathContentsLikeEntity.id.memberId.eq(memberId),
                )
                .exists()
        )
}