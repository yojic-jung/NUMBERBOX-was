package com.kamcci.numberbox.infra.orm.entity.math

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "math_category_unit")
class MathCategoryUnitEntity {
    @Id
    @Column(nullable = false)
    var id: Int = 0

    /**
     * 학년
     */
    @Column(length = 20, nullable = false)
    var subject: String? = null

    /**
     * 대단원
     */
    @Column(length = 30, nullable = false)
    var firUnit: String? = null

    /**
     * 중단원
     */
    @Column(length = 30, nullable = false)
    var secUnit: String? = null

    /**
     * 소단원
     */
    @Column(length = 1000, nullable = true)
    var thrUnit: String? = null

}