package com.kamcci.numberbox.infra.orm.entity.math

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "math_con_repo_info")
class MathContentsRepositoryEntity {
    @EmbeddedId
    var id: MathContentsRepositoryDomain? = null

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null
}

@Embeddable
class MathContentsRepositoryDomain(
    @Column(name = "contents_no")
    val contentsId: Long,
    @Column(name = "user_uniq_id")
    val memberId: UUID
) : Serializable