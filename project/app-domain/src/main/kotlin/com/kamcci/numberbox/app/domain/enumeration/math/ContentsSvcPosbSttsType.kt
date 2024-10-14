package com.kamcci.numberbox.app.domain.enumeration.math

/**
 * 수학 문제 서비스 가능 여부 상태 코드
 */
enum class ContentsSvcPosbSttsType(val id: Int, val desc: String) {
    NotRelease(0, "미출시"),
    Release(1, "출시"),
    Approved(2, "검수 완료"),
    Error(3, "오류"),
}