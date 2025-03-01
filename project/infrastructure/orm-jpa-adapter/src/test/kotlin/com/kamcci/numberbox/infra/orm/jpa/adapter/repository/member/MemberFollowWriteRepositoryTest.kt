package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberFollowDummyFactory.getMemberFollowDummyEntity4Del
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.FollowUserDomain
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberFollowEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberFollowWriteRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val memberFollowModifyRepository: MemberFollowWriteRepository
) {
    @Test
    fun `팔로우 영속화`() {
        // given
        val followingId = 1L
        val followerId = 2L

        // when
        memberFollowModifyRepository.save(followingId, followerId)
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
        val dummyEntity = getMemberFollowDummyEntity4Del()
        val followingId = dummyEntity.followingUserNo
        val followerId = dummyEntity.followerUserNo
        
        // when
        val isDeleted = memberFollowModifyRepository.delete(followingId, followerId)
        em.flush()
        em.clear()

        // then
        assertThat(isDeleted).isPositive()
    }
}