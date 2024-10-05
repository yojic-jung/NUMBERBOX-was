package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.entity.member.FollowUserDomain
import com.kamcci.numberbox.infra.orm.entity.member.MemberFollowEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberFollowModifyOrmAdapterTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val memberFollowModifyOrmAdapter: MemberFollowModifyOrmAdapter
) {
    @Test
    fun `팔로우 영속화`() {
        // given
        val followingId = 1L
        val followerId = 2L

        // when
        memberFollowModifyOrmAdapter.save(followingId, followerId)
        em.flush()
        em.clear()

        // then
        val domain = FollowUserDomain(followingId, followerId)
        val savedFollowEntity = em.find(MemberFollowEntity::class.java, domain)
        assertThat(savedFollowEntity).isNotNull
        assertThat(savedFollowEntity.id?.followingUserNo).isEqualTo(followingId)
    }

    @Test
    fun `팔로우 삭제`() {
        // given
        val followingId = 1L
        val followerId = 2L
        memberFollowModifyOrmAdapter.save(followingId, followerId)
        em.flush()
        em.clear()

        // when
        val isDeleted = memberFollowModifyOrmAdapter.delete(followingId, followerId)
        em.flush()
        em.clear()

        // then
        assertThat(isDeleted).isTrue()
    }
}