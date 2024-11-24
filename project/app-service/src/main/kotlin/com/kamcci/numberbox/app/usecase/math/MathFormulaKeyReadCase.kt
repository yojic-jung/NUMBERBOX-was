package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo

/**
 * 수식 기호 조회
 */
interface MathFormulaKeyReadCase {
    // 수식 기호 전체 조회
    fun readAll(): List<MathFormulaKeyVo>
}