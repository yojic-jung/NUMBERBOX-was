package com.kamcci.numberbox.app.usecase.resource

import com.kamcci.numberbox.app.domain.vo.resource.MathResourceMenuVo

/**
 * 수학 자료(도형, 그래프 pdf) 카테고리 - 조회
 */
interface MathResourceMenuReadUseCase {
    // 전체 조회
    fun readAll(): List<MathResourceMenuVo>
}