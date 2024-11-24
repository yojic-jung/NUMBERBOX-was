package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryTypeVo

/**
 * 수학 유형 정보 조회
 */
interface MathCategoryTypeReadCase {
    // 단원 하위 유형 조회
    fun readByUnitId(unitId: Int): List<MathCategoryTypeVo>

    // 단원 하위 유형 조회
    fun readByUnitId(unitIdList: List<Int>): List<MathCategoryTypeVo>
}