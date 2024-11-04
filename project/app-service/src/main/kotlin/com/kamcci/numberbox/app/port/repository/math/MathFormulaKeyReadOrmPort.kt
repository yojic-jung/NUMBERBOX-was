package com.kamcci.numberbox.app.port.repository.math

import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo

/**
 * 수식 기호 조회
 */
interface MathFormulaKeyReadOrmPort {
    // 수식 기호 전체 조회
    fun readAll(): List<MathFormulaKeyVo>
}