package com.kamcci.numberbox.app.port.repository.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import java.util.*

/**
 * 프로필 조회
 */
interface MemberProfileReadOrmPort {
    // 프로필 조회 - memberId로 조회
    fun findByMemberId(memberId: UUID): MemberProfileVo?

    // 프로필 조회 - 프로필 id로 조회
    fun findByProfileId(profileId: Long): MemberProfileVo?

    // 프로필 조회 - 프로필 id list로 조회
    fun findByProfileIdList(profileId: List<Long>): List<MemberProfileVo>

    // 프로필 id 조회
    fun findProfileIdByMemberId(memberId: UUID): Long?

    // 프로필 이미지 정보 조회
    fun findProfileImgByMemberId(memberId: UUID): MemberProfileImgVo?
}