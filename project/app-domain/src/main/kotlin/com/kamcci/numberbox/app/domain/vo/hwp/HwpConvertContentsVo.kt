package com.kamcci.numberbox.app.domain.vo.hwp

import java.time.LocalDateTime
import java.util.*

/**
 * hwp 변환 컨텐츠 정보
 */
data class HwpConvertContentsVo(
    val id: Long,
    // 소유자 id
    @Transient
    val memberId: UUID,
    // xhtml 파일 경로
    val filePath: String,
    // html 컨텐츠 내용 - html 스크립트 문법 문자열
    val contents: String,
    // html 컨텐츠 내부의 이미지 파일 경로
    val imgPath: String,
    // 생성날짜
    val sysCreateDate: LocalDateTime,
    // 수정 날짜
    val sysUpdateDate: LocalDateTime,
)