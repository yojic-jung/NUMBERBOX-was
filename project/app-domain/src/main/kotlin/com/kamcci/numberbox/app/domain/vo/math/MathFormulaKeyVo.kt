package com.kamcci.numberbox.app.domain.vo.math

import com.kamcci.numberbox.app.domain.enumeration.math.FormulaClassificationType

/**
 * 수식 단축키 정보
 */
data class MathFormulaKeyVo(
    val id: Int,
    /**
     * 순서
     */
    val formulOrder: Int,
    /**
     * 수식 이름
     */
    val formulName: String,
    /**
     * 수식 html ui
     */
    val formulUi: String,
    /**
     * 단축키
     */
    val shortcutKey: String,
    /**
     * latex 수식 문법
     */
    val latexGrammer: String,
    /**
     * N명의수학 수식 문법
     */
    val nbGrammer: String,
    /**
     * 사용법
     */
    val guide: String,
    /**
     * 단축키 키값 코드
     */
    val shortcutKeycode: String,
    /**
     * tex 수식 문법
     */
    val texGrammer: String,
    /**
     * 줄바꿈 여부
     */
    val lineChange: Int,
    /**
     * 분류
     */
    val classification: FormulaClassificationType,
)