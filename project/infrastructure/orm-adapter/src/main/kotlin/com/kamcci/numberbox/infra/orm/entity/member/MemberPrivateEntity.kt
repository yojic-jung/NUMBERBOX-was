package com.kamcci.numberbox.infra.orm.entity.member

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

/**
 * 회원 개인정보
 */
@Table(name = "members_private")
@Entity
class MemberPrivateEntity {
    @Id
    @Column(name = "user_uniq_id", columnDefinition = "BINARY(16)")
    var memberId: UUID? = null

    @Column(name = "user_name", nullable = false)
    var userName: String? = null

    @Column(name = "phone_number", length = 11, nullable = false)
    var phoneNumber: String? = null

    @Column(name = "birth", length = 6, nullable = false)
    var birth: String? = null

    @Column(name = "sys_update_time", nullable = false)
    var sysUpdateTime: LocalDateTime? = LocalDateTime.now()

    @CreationTimestamp
    @Column(name = "sys_create_time", nullable = false)
    var sysCreateTime: LocalDateTime = LocalDateTime.now()
}