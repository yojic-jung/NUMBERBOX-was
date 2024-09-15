package com.kamcci.numberbox.infra.orm.entity.member

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 회원 팔로우 정보
 */
@Table(name = "members_follow_info")
@Entity
class MemberFollowEntity {
    @EmbeddedId
    var id: FollowUserDomain? = null

    @Column(name = "sys_create_date", nullable = false)
    var sysCreateDate: LocalDateTime = LocalDateTime.now()
}

class FollowUserDomain(
    val followingUserNo: Long,
    val followerUserNo: Long
) : Serializable {
    private val serialVersionUID = 1L
}