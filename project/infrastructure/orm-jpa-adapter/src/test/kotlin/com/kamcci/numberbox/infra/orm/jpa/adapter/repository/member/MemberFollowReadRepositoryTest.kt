package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberFollowDummyFactory.NOT_EXIST_FOLLOW_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberFollowDummyFactory.getMemberFollowDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberFollowReadRepositoryTest @Autowired constructor(
    private val memberFollowReadRepository: MemberFollowReadRepository
) {
    private val followDummyEntity = getMemberFollowDummyEntity()

    @Test
    fun `팔로잉 조회`() {
        // given
        val followerUserNo = followDummyEntity.followerUserNo

        // when
        val followingList = memberFollowReadRepository.readFollowingByFollower(followerUserNo)

        // then
        assertThat(followingList.size).isPositive()
    }

    @Test
    fun `팔로워 조회`() {
        // given
        val followingId = followDummyEntity.followingUserNo

        // when
        val followerList = memberFollowReadRepository.readFollowerByFollowing(followingId)

        // then
        assertThat(followerList.size).isPositive()
    }

    @Test
    fun `팔로워 카운트 조회`() {
        // given
        val followingId = followDummyEntity.followingUserNo

        // when
        val followerCnt = memberFollowReadRepository.countFollower(followingId)

        // then
        assertThat(followerCnt).isPositive()
    }

    @Test
    fun `팔로우 존재 여부 조회 - 존재`() {
        // given
        val followingId = followDummyEntity.followingUserNo
        val followerId = followDummyEntity.followerUserNo

        // when
        val isExist = memberFollowReadRepository.existFollow(followingId, followerId)

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `팔로우 존재 여부 조회 - 미존재`() {
        // given
        val followingId = NOT_EXIST_FOLLOW_ID
        val followerId = NOT_EXIST_FOLLOW_ID

        // when
        val isExist = memberFollowReadRepository.existFollow(followingId, followerId)

        // then
        assertThat(isExist).isFalse()
    }
}