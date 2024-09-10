package com.kamcci.numberbox.infra.orm.entity.member

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Table(name = "email_id_code")
@Entity
class MemberEmailIdCodeEntity {
    @Id
    @Column(name = "email", nullable = false)
    var email: String? = null

    @Column(name = "id_code", nullable = false)
    var idCode: String? = null

    @Column(name = "try_cnt", nullable = false)
    var tryCnt: Int = 0

    @CreationTimestamp
    @Column(name = "sys_create_time", nullable = false)
    var sysCreateTime: LocalDateTime = LocalDateTime.now()
}
