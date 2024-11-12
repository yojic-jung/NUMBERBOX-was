package com.kamcci.numberbox.app.usecase.resource

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceVo

/**
 * 수학 자료(도형, 그래프) pdf - 조회
 */
interface MathResourceReadUseCase {
    // 대분류 id로 조회
    fun readByMainCateId(mainCateId: Int, pageReq: PageRequest): List<MathResourceVo>

    // 대분류 id로 전체 카운트 조회
    fun countByMainCateId(mainCateId: Int): Long
}