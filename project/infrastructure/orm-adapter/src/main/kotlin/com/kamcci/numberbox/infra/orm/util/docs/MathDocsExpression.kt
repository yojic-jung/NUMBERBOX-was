package com.kamcci.numberbox.infra.orm.util.docs

import com.kamcci.numberbox.app.domain.vo.docs.MathInHouseDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathIpsiDocsVo
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsEntity.mathContentsEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsIpsiSrcEntity.mathContentsIpsiSrcEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathTypeInfoEntity.mathTypeInfoEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathUnitInfoEntity.mathUnitInfoEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Component

@Component
class MathDocsExpression {
    fun ceMathIpsiDocsVo() = Projections.constructor(
        MathIpsiDocsVo::class.java,
        mathContentsEntity.id,
        mathContentsEntity.unitId,
        mathContentsEntity.typeId,
        mathContentsEntity.contents,
        mathContentsEntity.contentsImg,
        mathContentsEntity.imgPath,
        mathContentsEntity.solution,
        mathContentsEntity.solutionImg,
        mathContentsEntity.solutionImgPath,
        mathContentsEntity.fifNo,
        mathContentsEntity.secNo,
        mathContentsEntity.thrNo,
        mathContentsEntity.fourNo,
        mathContentsEntity.fifNo,
        mathContentsEntity.multiChoiceType,
        mathContentsEntity.answer,
        mathContentsEntity.choiceAnswer,
        mathContentsEntity.quesLevel,
        mathContentsEntity.ansExistStts,
        mathContentsEntity.contentsClassify,
        mathUnitInfoEntity.subject,
        mathUnitInfoEntity.firUnit,
        mathUnitInfoEntity.secUnit,
        mathUnitInfoEntity.thrUnit,
        mathTypeInfoEntity.quesType,
        mathContentsEntity.sysCreateDate,
        mathContentsIpsiSrcEntity.impYear,
        mathContentsIpsiSrcEntity.impMonth,
        mathContentsIpsiSrcEntity.oddQuesNum,
        mathContentsIpsiSrcEntity.wrongRatio,
        mathContentsIpsiSrcEntity.paperType,
    )

    fun ceMathInHouseDocsVo() = Projections.constructor(
        MathInHouseDocsVo::class.java,
        mathContentsEntity.id,
        mathContentsEntity.unitId,
        mathContentsEntity.typeId,
        mathContentsEntity.contents,
        mathContentsEntity.contentsImg,
        mathContentsEntity.imgPath,
        mathContentsEntity.solution,
        mathContentsEntity.solutionImg,
        mathContentsEntity.solutionImgPath,
        mathContentsEntity.fifNo,
        mathContentsEntity.secNo,
        mathContentsEntity.thrNo,
        mathContentsEntity.fourNo,
        mathContentsEntity.fifNo,
        mathContentsEntity.multiChoiceType,
        mathContentsEntity.answer,
        mathContentsEntity.choiceAnswer,
        mathContentsEntity.quesLevel,
        mathContentsEntity.ansExistStts,
        mathContentsEntity.contentsClassify,
        mathUnitInfoEntity.subject,
        mathUnitInfoEntity.firUnit,
        mathUnitInfoEntity.secUnit,
        mathUnitInfoEntity.thrUnit,
        mathTypeInfoEntity.quesType,
        mathContentsEntity.sysCreateDate
    )

}