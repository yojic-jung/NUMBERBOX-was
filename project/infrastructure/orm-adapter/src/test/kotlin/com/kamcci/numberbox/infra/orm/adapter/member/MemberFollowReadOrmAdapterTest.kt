package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberFollowReadOrmAdapterTest(
    @Autowired
    private val memberFollowReadOrmAdapter: MemberFollowReadOrmAdapter
) {
    @Test
    fun `팔로잉 조회`() {
        // given
        val followerId = 4L

        // when
        val followingList = memberFollowReadOrmAdapter.readFollowingByFollower(followerId)

        // then
        assertThat(followingList.size).isGreaterThan(0)
    }

    @Test
    fun `팔로워 조회`() {
        // given
        val followingId = 3L

        // when
        val followerList = memberFollowReadOrmAdapter.readFollowerByFollowing(followingId)

        // then
        assertThat(followerList.size).isGreaterThan(0)
    }

    @Test
    fun `팔로워 카운트 조회`() {
        // given
        val followingId = 3L

        // when
        val followerCnt = memberFollowReadOrmAdapter.countFollower(followingId)

        // then
        assertThat(followerCnt).isGreaterThan(0)
    }

    @Test
    fun `팔로우 존재 여부 조회`() {
        // given
        val followingId = 3L
        val followerId = 4L

        // when
        val isExist = memberFollowReadOrmAdapter.existFollow(followingId, followerId)

        // then
        assertThat(isExist).isTrue()
    }
}