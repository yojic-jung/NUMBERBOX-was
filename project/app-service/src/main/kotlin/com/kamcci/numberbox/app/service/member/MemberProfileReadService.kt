package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileFollowReadCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import java.util.*

@UseCase
class MemberProfileReadService(
    private val memberProfileReadCase: MemberProfileReadCase,
    private val memberFollowReadOrmPort: MemberFollowReadCase
) : MemberProfileFollowReadCase {
    companion object {
        const val NOT_EXIST_PROFILE = "회원 프로필이 존재하지 않습니다."
    }

    override fun readFollowingProfileByMemberId(memberId: UUID): List<MemberProfileVo> {
        // 팔로잉 profileId 조회
        val profileId = memberProfileReadCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessInValidException(NOT_EXIST_PROFILE)
        val followingProfileIdList = memberFollowReadOrmPort.readFollowingByFollower(profileId)

        // 프로필 조회
        return memberProfileReadCase.readByProfileIdList(followingProfileIdList)
    }

    override fun readFollowerProfileByMemberId(memberId: UUID): List<MemberProfileVo> {
        // 팔로워 profileId 조회
        val profileId = memberProfileReadCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessInValidException(NOT_EXIST_PROFILE)
        val followerProfileIdList = memberFollowReadOrmPort.readFollowerByFollowing(profileId)

        // 프로필 조회
        return memberProfileReadCase.readByProfileIdList(followerProfileIdList)
    }
}