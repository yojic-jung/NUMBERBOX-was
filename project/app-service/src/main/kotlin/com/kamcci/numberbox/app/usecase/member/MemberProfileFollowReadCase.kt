package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import java.util.*

/**
 * 프로필 팔로잉 정보 조회
 */
interface MemberProfileFollowReadCase {

    // 팔로잉 프로필 정보 조회
    fun readFollowingProfileByMemberId(memberId: UUID): List<MemberProfileVo>

    // 팔로워 프로필 정보 조회
    fun readFollowerProfileByMemberId(memberId: UUID): List<MemberProfileVo>
}