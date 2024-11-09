package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "math_category_type")
class MathCategoryTypeEntity {
    @EmbeddedId
    var mathTypeDomain: MathTypeDomain? = null

    @Column(length = 1500, nullable = false)
    var quesType: String? = null

    @Column(length = 2, nullable = false)
    var typeOrder: Int = 0
}

@Embeddable
class MathTypeDomain(
    @Column(nullable = false)
    val unitId: Int,
    @Column(nullable = false)
    val typeId: Int,
) : Serializable