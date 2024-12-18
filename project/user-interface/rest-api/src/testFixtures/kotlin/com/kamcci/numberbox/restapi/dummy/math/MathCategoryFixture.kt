package com.kamcci.numberbox.restapi.dummy.math

import com.kamcci.numberbox.app.domain.enumeration.math.FormulaClassificationType
import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo

object MathCategoryFixture {
    fun getMathCategoryUnitVo(): List<MathCategoryUnitVo> {
        return listOf(
            MathCategoryUnitVo(21001, "중1", "수와 연산", "소인수분해", "소인수분해"),
            MathCategoryUnitVo(21002, "중1", "수와 연산", "소인수분해", "최대공약수와 최소공배수"),
            MathCategoryUnitVo(21003, "중1", "수와 연산", "정수와 유리수", "정수와 유리수의 뜻"),
            MathCategoryUnitVo(21004, "중1", "수와 연산", "정수와 유리수", "정수와 유리수의 대소 관계"),
        )
    }

    fun getMathFormulaKeyVo(id: Int, classification: FormulaClassificationType) =
        MathFormulaKeyVo(
            id = id,
            formulOrder = 1,
            formulName = "asd",
            formulUi = "sddf",
            shortcutKey = "dsf",
            latexGrammer = "sdf",
            nbGrammer = "sdf",
            guide = "sdf",
            shortcutKeycode = "d",
            texGrammer = "tex",
            lineChange = 1,
            classification = classification,
        )

    fun getMathFormulaKeyVoList(): List<MathFormulaKeyVo> {
        return listOf(
            getMathFormulaKeyVo(1, FormulaClassificationType.Main),
            getMathFormulaKeyVo(2, FormulaClassificationType.Main),
            getMathFormulaKeyVo(3, FormulaClassificationType.Main),
            getMathFormulaKeyVo(4, FormulaClassificationType.High1),
            getMathFormulaKeyVo(5, FormulaClassificationType.High1),
            getMathFormulaKeyVo(6, FormulaClassificationType.High1),
        )
    }

}
