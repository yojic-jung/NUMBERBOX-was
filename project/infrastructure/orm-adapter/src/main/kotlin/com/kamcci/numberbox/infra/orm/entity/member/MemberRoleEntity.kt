package com.kamcci.numberbox.infra.orm.entity.member

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "members_role")
class MemberRoleEntity {
    @Id
    @Column(name = "seq_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uniq_id")
    var member: MemberEntity = MemberEntity()

    @Column(updatable = false, nullable = false)
    var enabled: Boolean = true

    @Column(updatable = false, nullable = false)
    var roleName: String = ""

    @Column(name = "sys_update_time", nullable = false)
    var sysUpdateTime: LocalDateTime? = LocalDateTime.now()

    @CreationTimestamp
    @Column(name = "sys_create_time", nullable = false)
    var sysCreateTime: LocalDateTime = LocalDateTime.now()
}
