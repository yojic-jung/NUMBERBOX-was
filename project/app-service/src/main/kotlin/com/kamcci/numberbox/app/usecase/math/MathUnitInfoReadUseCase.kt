package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo

/**
 * 수학 단원 정보 조회
 */
interface MathUnitInfoReadUseCase {
    // 단원 정보 전체 조회
    fun readAll(): List<MathCategoryUnitVo>
}