package com.kamcci.numberbox.app.usecase.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.app.domain.system.construction.TXExecute
import java.util.*

/**
 *  hwp to html 변환 컨텐츠
 */
interface HwpConvertContentsWriteCase {
    // 변환 컨텐츠 저장
    @TXExecute
    fun create(createDto: HwpConvertContentsCreateDto): Long

    // 변환 컨텐츠 수정
    @TXExecute
    fun update(updateDto: HwpConvertContentsUpdateDto): Long

    // 변환 컨텐츠 삭제
    @TXExecute
    fun delete(contentsId: Long, memberId: UUID): Long
}