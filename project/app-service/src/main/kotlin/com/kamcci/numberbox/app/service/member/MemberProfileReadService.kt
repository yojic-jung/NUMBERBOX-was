package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.port.repository.member.MemberFollowReadOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberProfileReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import java.util.*

@UseCase
class MemberProfileReadService(
    private val memberProfileReadOrmPort: MemberProfileReadOrmPort,
    private val memberFollowReadOrmPort: MemberFollowReadOrmPort
) : MemberProfileReadUseCase {
    override fun findByMemberId(memberId: UUID): MemberProfileVo? {
        return memberProfileReadOrmPort.findByMemberId(memberId)
    }

    override fun findByProfileId(profileId: Long): MemberProfileVo? {
        return memberProfileReadOrmPort.findByProfileId(profileId)
    }

    override fun findProfileIdByMemberId(memberId: UUID): Long? {
        return memberProfileReadOrmPort.findProfileIdByMemberId(memberId)
    }


    override fun findProfileImgByMemberId(memberId: UUID): MemberProfileImgVo? {
        return memberProfileReadOrmPort.findProfileImgByMemberId(memberId)
    }

    override fun findFollowingProfileByMemberId(memberId: UUID): List<MemberProfileVo> {
        // 팔로잉 profileId 조회
        val profileId = memberProfileReadOrmPort.findProfileIdByMemberId(memberId)
            ?: throw BusinessInValidException("회원 프로필이 존재하지 않습니다.")
        val followingProfileIdList = memberFollowReadOrmPort.findFollowingByFollower(profileId)

        // 프로필 조회
        return memberProfileReadOrmPort.findByProfileIdList(followingProfileIdList)
    }

    override fun findFollowerProfileByMemberId(memberId: UUID): List<MemberProfileVo> {
        // 팔로워 profileId 조회
        val profileId = memberProfileReadOrmPort.findProfileIdByMemberId(memberId)
            ?: throw BusinessInValidException("회원 프로필이 존재하지 않습니다.")
        val followerProfileIdList = memberFollowReadOrmPort.findFollowerByFollowing(profileId)

        // 프로필 조회
        return memberProfileReadOrmPort.findByProfileIdList(followerProfileIdList)
    }
}