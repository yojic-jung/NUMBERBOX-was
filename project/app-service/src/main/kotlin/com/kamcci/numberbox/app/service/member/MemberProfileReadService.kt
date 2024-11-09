package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.port.orm.member.MemberFollowReadOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberProfileReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import java.util.*

@UseCase
class MemberProfileReadService(
    private val memberProfileReadOrmPort: MemberProfileReadOrmPort,
    private val memberFollowReadOrmPort: MemberFollowReadOrmPort
) : MemberProfileReadUseCase {
    override fun readByMemberId(memberId: UUID): MemberProfileVo? {
        return memberProfileReadOrmPort.readByMemberId(memberId)
    }

    override fun readByProfileId(profileId: Long): MemberProfileVo? {
        return memberProfileReadOrmPort.readByProfileId(profileId)
    }

    override fun readProfileIdByMemberId(memberId: UUID): Long? {
        return memberProfileReadOrmPort.readProfileIdByMemberId(memberId)
    }


    override fun readProfileImgByMemberId(memberId: UUID): MemberProfileImgVo? {
        return memberProfileReadOrmPort.readProfileImgByMemberId(memberId)
    }

    override fun readFollowingProfileByMemberId(memberId: UUID): List<MemberProfileVo> {
        // 팔로잉 profileId 조회
        val profileId = memberProfileReadOrmPort.readProfileIdByMemberId(memberId)
            ?: throw BusinessValidException("회원 프로필이 존재하지 않습니다.")
        val followingProfileIdList = memberFollowReadOrmPort.readFollowingByFollower(profileId)

        // 프로필 조회
        return memberProfileReadOrmPort.readByProfileIdList(followingProfileIdList)
    }

    override fun readFollowerProfileByMemberId(memberId: UUID): List<MemberProfileVo> {
        // 팔로워 profileId 조회
        val profileId = memberProfileReadOrmPort.readProfileIdByMemberId(memberId)
            ?: throw BusinessValidException("회원 프로필이 존재하지 않습니다.")
        val followerProfileIdList = memberFollowReadOrmPort.readFollowerByFollowing(profileId)

        // 프로필 조회
        return memberProfileReadOrmPort.readByProfileIdList(followerProfileIdList)
    }
}