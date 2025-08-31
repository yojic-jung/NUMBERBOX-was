package com.kamcci.numberbox.app.domain.dto.hwp

/**
 * hwp to html 변환 요청 메시지 전송 성공 알림
 */
data class HwpToHtmlRequestResultEvent(
    // hwpConvertFile.id
    val id: Long,
    // 성공 여부
    val isSuccess: Boolean,
)