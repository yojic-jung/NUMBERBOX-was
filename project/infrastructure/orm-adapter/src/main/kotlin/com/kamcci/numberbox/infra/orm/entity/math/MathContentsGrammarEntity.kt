package com.kamcci.numberbox.infra.orm.entity.math

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * 수학문제 tex 문법 저장 엔티티
 */
@Table(name = "math_contents_grammer")
@Entity
class MathContentsGrammarEntity {
    /**
     * MathContentsEntity.id
     */
    @Id
    @Column(name = "contents_no", nullable = false)
    var contentsId: Long = 0

    @Column(name = "contents_gram", nullable = false)
    var grammar: String? = null
}