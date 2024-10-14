package com.kamcci.numberbox.app.domain.enumeration.math

/**
 * 객관식 주관식 여부
 */
enum class MultiChoiceType(val id: String, val desc: String) {
    Essay("E", "주관식"),
    Multiple("M", "객관식")
}