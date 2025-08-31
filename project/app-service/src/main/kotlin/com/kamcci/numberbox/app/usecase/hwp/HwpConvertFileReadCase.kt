package com.kamcci.numberbox.app.usecase.hwp

import com.kamcci.numberbox.app.domain.vo.hwp.HwpConvertFileTypeVo
import java.time.LocalDateTime

/**
 *  hwp to html 변환 파일 조회
 */
interface HwpConvertFileReadCase {
    // 요청에 실패하고, requestAt 만큼 지난 변환 요청 조회
    fun readByRequestAtLoe(requestAt: LocalDateTime): List<HwpConvertFileTypeVo>
}