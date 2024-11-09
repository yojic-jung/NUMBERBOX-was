package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.app.domain.enumeration.math.FormulaClassificationType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "math_formula_key")
class MathFormulaKeyEntity {
    @Id
    var id: Int = 0

    /**
     * 순서
     */
    @Column(length = 3, nullable = false)
    var formulOrder: Int = 0

    /**
     * 수식 이름
     */
    @Column(length = 30, nullable = false)
    var formulName: String? = null

    /**
     * 수식 html ui
     */
    @Column(length = 45, nullable = false)
    var formulUi: String? = null

    /**
     * 단축키
     */
    @Column(length = 2, nullable = true)
    var shortcutKey: String? = null

    /**
     * latex 수식 문법
     */
    @Column(length = 250, nullable = false)
    var latexGrammer: String? = null

    /**
     * N명의수학 수식 문법
     */
    @Column(length = 250, nullable = false)
    var nbGrammer: String? = null

    /**
     * 사용법
     */
    @Column(length = 80, nullable = true)
    var guide: String? = null

    /**
     * 단축키 키값 코드
     */
    @Column(length = 5, nullable = true)
    var shortcutKeycode: String? = null

    /**
     * tex 수식 문법
     */
    @Column(length = 40, nullable = false)
    var texGrammer: String? = null

    /**
     * 줄바꿈 여부
     */
    @Column(length = 1, nullable = false)
    var lineChange: Int = 0

    /**
     * 분류
     */
    @Column(length = 5, nullable = false)
    var classification: FormulaClassificationType? = null
}