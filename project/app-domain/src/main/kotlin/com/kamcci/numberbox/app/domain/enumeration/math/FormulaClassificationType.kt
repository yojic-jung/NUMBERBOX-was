package com.kamcci.numberbox.app.domain.enumeration.math

/**
 * 수식 분류 타입
 */
enum class FormulaClassificationType(val dbData: String) {
    Main("main"), // 메인
    High1("high1"), // 고등
    Etc("etc"), // 기타
    Etc2("etc2"), // 기타2
}