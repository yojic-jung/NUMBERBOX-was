package com.kamcci.numberbox.infra.orm.entity.math

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "math_con_like_info")
class MathContentsLikeEntity {
    @EmbeddedId
    var id: MathContentsLikeDomain? = null
}

@Embeddable
class MathContentsLikeDomain(
    @Column(name = "contents_no")
    val contentsId: Long,
    @Column(name = "user_uniq_id")
    val memberId: UUID
) : Serializable