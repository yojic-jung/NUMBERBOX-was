package com.numberbox.infra.orm_adpater.entity.member

import jakarta.persistence.*

@Entity
@Table(name = "members_role")
class MemberRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var seqNo: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uniq_id")
    var member: MemberEntity = MemberEntity()

    @Column(updatable = false, nullable = false)
    var enabled: Boolean = true

    @Column(updatable = false, nullable = false)
    var roleName: String = ""
}
