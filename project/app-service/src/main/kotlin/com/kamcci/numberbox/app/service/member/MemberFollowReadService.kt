package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.member.MemberFollowReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadUseCase

@UseCase
class MemberFollowReadService(
    private val memberFollowReadOrmPort: MemberFollowReadOrmPort
) : MemberFollowReadUseCase {
    override fun findFollowingByFollower(profileId: Long): List<Long> =
        memberFollowReadOrmPort.findFollowingByFollower(profileId)

    override fun findFollowerByFollowing(profileId: Long): List<Long> =
        memberFollowReadOrmPort.findFollowerByFollowing(profileId)

    override fun countFollower(followingId: Long): Long =
        memberFollowReadOrmPort.countFollower(followingId)

    override fun isFollowing(follwingId: Long, followerId: Long): Boolean =
        memberFollowReadOrmPort.existFollow(follwingId, followerId)
}