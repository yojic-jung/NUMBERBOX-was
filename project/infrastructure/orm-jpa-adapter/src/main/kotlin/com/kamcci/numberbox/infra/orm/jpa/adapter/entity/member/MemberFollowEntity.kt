package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
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

    @CreationTimestamp
    @Column(name = "sys_create_date", nullable = false)
    var sysCreateDate: LocalDateTime = LocalDateTime.now()
}

@Embeddable
class FollowUserDomain(
    val followingUserNo: Long,
    val followerUserNo: Long
) : Serializable