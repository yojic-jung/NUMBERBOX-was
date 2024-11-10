package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberFollowReadRepositoryTest(
    @Autowired
    private val memberFollowReadRepository: MemberFollowReadRepository
) {
    @Test
    fun `팔로잉 조회`() {
        // given
        val followerId = 4L

        // when
        val followingList = memberFollowReadRepository.readFollowingByFollower(followerId)

        // then
        assertThat(followingList.size).isGreaterThan(0)
    }

    @Test
    fun `팔로워 조회`() {
        // given
        val followingId = 3L

        // when
        val followerList = memberFollowReadRepository.readFollowerByFollowing(followingId)

        // then
        assertThat(followerList.size).isGreaterThan(0)
    }

    @Test
    fun `팔로워 카운트 조회`() {
        // given
        val followingId = 3L

        // when
        val followerCnt = memberFollowReadRepository.countFollower(followingId)

        // then
        assertThat(followerCnt).isGreaterThan(0)
    }

    @Test
    fun `팔로우 존재 여부 조회`() {
        // given
        val followingId = 3L
        val followerId = 4L

        // when
        val isExist = memberFollowReadRepository.existFollow(followingId, followerId)

        // then
        assertThat(isExist).isTrue()
    }
}