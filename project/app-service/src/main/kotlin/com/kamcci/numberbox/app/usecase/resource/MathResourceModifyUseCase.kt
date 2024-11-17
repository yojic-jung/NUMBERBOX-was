package com.kamcci.numberbox.app.usecase.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.vo.resource.MathResFileModifyStatusVo

/**
 * 수학 학습 자료 - 변경
 */
interface MathResourceModifyUseCase {
    /**
     * 등록
     */
    fun create(createDto: MathResourceCreateDto): Long

    /**
     * 수정
     *
     * @return 파일 수정 실행 여부
     */
    fun update(updateDto: MathResourceUpdateDto): MathResFileModifyStatusVo
}