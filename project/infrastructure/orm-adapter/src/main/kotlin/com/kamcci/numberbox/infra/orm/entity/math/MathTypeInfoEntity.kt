package com.kamcci.numberbox.infra.orm.entity.math

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "math_type_info")
class MathTypeInfoEntity {
    @EmbeddedId
    var mathTypeDomain: MathTypeDomain? = null

    @Column(length = 1500, nullable = false)
    var quesType: String? = null

    @Column(length = 2, nullable = false)
    var typeOrder: Int = 0
}

@Embeddable
class MathTypeDomain(
    @Column(name = "unit_uniq_no")
    val unitId: Int,
    @Column(name = "type_no")
    val typeId: Int,
) : Serializable