package com.kamcci.numberbox.app.domain.enumeration.math

/**
 * 문제 유형 구분
 */
enum class MathTypeClassifyType(val dbData: String) {
    Simple("단순계산"),
    Application("응용")
}