package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "math_con_repo")
class MathContentsRepositoryEntity {
    @EmbeddedId
    var id: MathContentsRepositoryDomain? = null

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null
}

@Embeddable
class MathContentsRepositoryDomain(
    @Column(nullable = false)
    val contentsId: Long,
    @Column(nullable = false)
    val memberId: UUID
) : Serializable