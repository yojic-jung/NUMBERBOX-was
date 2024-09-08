package com.numberbox.infra.orm.entity.member

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "refresh_token_info")
class MemberRefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "token", nullable = false)
    var token: String = ""

    @Column(name = "user_uniq_id", nullable = false)
    var userUniqId: UUID = UUID.randomUUID()

    @Column(name = "token_create_date", nullable = false)
    @CreationTimestamp
    var tokenCreateDate: LocalDateTime = LocalDateTime.now()
}
