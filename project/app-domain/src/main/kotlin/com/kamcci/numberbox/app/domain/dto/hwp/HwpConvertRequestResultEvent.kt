package com.kamcci.numberbox.app.domain.dto.hwp

/**
 * hwp 변환 요청 메시지 전송 성공 여부
 */
data class HwpConvertRequestResultEvent(
    // hwpConvertFile.id
    val id: Long,
    // 성공 여부
    val isSuccess: Boolean,
)
