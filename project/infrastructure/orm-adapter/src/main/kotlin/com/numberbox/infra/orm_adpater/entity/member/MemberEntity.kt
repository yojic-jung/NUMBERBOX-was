package com.numberbox.infra.orm_adpater.entity.member

import com.fasterxml.uuid.Generators
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "members")
class MemberEntity {
    @Id
    @Column(name = "user_uniq_id", columnDefinition = "BINARY(16)", nullable = false)
    var userUniqId: UUID = UUID.randomUUID()

    @PrePersist
    fun createUserUniqId() {
        // sequential uuid 생성
        var uuid = Generators.timeBasedGenerator().generate()
        val uuidArr = uuid.toString().split("-")
        val uuidStr = uuidArr[2] + uuidArr[1] + uuidArr[0] + uuidArr[3] + uuidArr[4]
        val sb = StringBuilder(uuidStr)
        sb.insert(8, "-")
        sb.insert(13, "-")
        sb.insert(18, "-")
        sb.insert(23, "-")
        uuid = UUID.fromString(sb.toString())
        this.userUniqId = uuid
    }

    @Column(name = "email", nullable = false)
    var email: String = ""

    @Column(name = "password", nullable = false)
    var password: String = ""

    /*
     * 0 : 일반 계정 1 : 휴먼계정 2 : 탈퇴 요청 3 : 탈퇴 계정(유령계정)
     */
    @Column(name = "human_status", nullable = false)
    var humanStatus: Int = 0

    @Column(name = "fail_count", nullable = false)
    var failCount: Int = 0

    @CreationTimestamp
    @Column(name = "last_fail_time")
    var lastFailTime: LocalDateTime? = null

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    var role: MutableList<MemberRoleEntity> = mutableListOf()
    
    // 0 : 일반계정
    // 1 : 임시 비밀번호 발급계정(임시 비밀번호 발급계정 비밀번호 수정 안 하는 경우 새로운 비밀번호로 수정(스케쥴러로 구현))
    @Column(name = "tmp_password", nullable = false)
    var tmpPassword: Boolean = false

    @CreationTimestamp
    @Column(name = "signup_date", nullable = false)
    var signupDate: LocalDateTime? = LocalDateTime.now()

    @UpdateTimestamp
    @Column(name = "last_login_date", nullable = false)
    var lastLoginDate: LocalDateTime = LocalDateTime.now()
}
