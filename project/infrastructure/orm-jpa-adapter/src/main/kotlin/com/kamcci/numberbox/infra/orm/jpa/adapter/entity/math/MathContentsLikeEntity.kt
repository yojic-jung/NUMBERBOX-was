package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

/**
 * 수학문제 좋아요 정보
 */
@Entity
@Table(name = "math_con_like")
class MathContentsLikeEntity {
    @EmbeddedId
    var id: MathContentsLikeDomain? = null
}

@Embeddable
class MathContentsLikeDomain(
    @Column(nullable = false)
    val contentsId: Long,
    @Column(nullable = false)
    val memberId: UUID
) : Serializable