package com.kamcci.numberbox.app.domain.enumeration.math

/**
 * 가/나형 구분
 */
enum class IpsiPaperType(val id: Int, val desc: String) {
    Integration(1, "통합"),
    Ka(2, "가형"),
    Na(3, "나형"),
}