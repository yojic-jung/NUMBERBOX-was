package com.kamcci.numberbox.infra.orm.entity.member

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
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

    @NotNull
    @Column(name = "user_uniq_id", nullable = false)
    var memberId: UUID? = null

    @Column(name = "sys_update_time", nullable = false)
    var sysUpdateTime: LocalDateTime? = LocalDateTime.now()

    @Column(name = "token_create_date", nullable = false)
    @CreationTimestamp
    var sysCreateTime: LocalDateTime = LocalDateTime.now()
}
