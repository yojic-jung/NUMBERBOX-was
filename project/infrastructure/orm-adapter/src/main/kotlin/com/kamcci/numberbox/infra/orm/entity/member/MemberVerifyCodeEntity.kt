package com.kamcci.numberbox.infra.orm.entity.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Table(name = "members_verify_code")
@Entity
class MemberVerifyCodeEntity {
    @Id
    @Column(name = "email", nullable = false)
    var email: String? = null

    @Column
    var codeType: VerifyCodeType? = null

    @Column(name = "id_code", nullable = false)
    var verifyCode: String? = null

    @Column(name = "try_cnt")
    var tryCnt: Int = 0

    @Column(name = "sys_update_time", nullable = false)
    var sysUpdateTime: LocalDateTime? = LocalDateTime.now()

    @CreationTimestamp
    @Column(name = "sys_create_time", nullable = false)
    var sysCreateTime: LocalDateTime = LocalDateTime.now()
}
