package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "member_refresh_token")
class MemberRefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "token", nullable = false)
    var token: String = ""

    @NotNull
    @Column(name = "member_id", nullable = false)
    var memberId: UUID? = null

    @Column(name = "sys_update_time", nullable = false)
    var sysUpdateTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "sys_create_time", nullable = false)
    @CreationTimestamp
    var sysCreateTime: LocalDateTime = LocalDateTime.now()
}
