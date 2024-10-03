package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import java.util.*

/**
 * 프로필 조회
 */
interface MemberProfileReadUseCase {
    // 사용자 id로 프로필 가져오기
    fun findByMemberId(memberId: UUID): MemberProfileVo?

    // 프로필 id로 프로필 가져오기
    fun findByProfileId(profileId: Long): MemberProfileVo?

    // 프로필 이미지 가져오기
    fun findProfileImgByMemberId(memberId: UUID): MemberProfileImgVo?

    // 프로필 id 조회
    fun findProfileIdByMemberId(memberId: UUID): Long?

    // 팔로잉 프로필 정보 조회
    fun findFollowingProfileByMemberId(memberId: UUID): List<MemberProfileVo>

    // 팔로워 프로필 정보 조회
    fun findFollowerProfileByMemberId(memberId: UUID): List<MemberProfileVo>

}