package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberProfileDummyFactory.getExistProfileIdList
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberProfileDummyFactory.getMemberProfileDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberProfileReadRepositoryTest(
    @Autowired
    private val memberProfileReadRepository: MemberProfileReadRepository
) {
    private val memberProfileDummyEntity = getMemberProfileDummyEntity()

    @Test
    fun `멤버 id로 프로필 조회`() {
        // given
        val memberId = memberProfileDummyEntity.memberId

        // when
        val profile = memberProfileReadRepository.readByMemberId(memberId)

        // then
        assertThat(profile?.memberId).isEqualTo(memberId)
    }

    @Test
    fun `프로필 id로 프로필 조회`() {
        // given
        val profileId = memberProfileDummyEntity.profileId

        // when
        val profile = memberProfileReadRepository.readByProfileId(profileId)

        // then
        assertThat(profile?.id).isEqualTo(1L)
    }

    @Test
    fun `멤버 id로 프로필 id 조회`() {
        // given
        val memberId = memberProfileDummyEntity.memberId

        // when
        val profileId = memberProfileReadRepository.readProfileIdByMemberId(memberId)

        // then
        assertThat(profileId).isEqualTo(memberProfileDummyEntity.profileId)
    }

    @Test
    fun `멤버 id로 프로필 이미지 조회`() {
        // when
        val profileImg = memberProfileReadRepository.readProfileImgByMemberId(memberProfileDummyEntity.memberId)

        // then
        assertThat(profileImg?.profileImgPath).isEqualTo(memberProfileDummyEntity.profileImgPath)
        assertThat(profileImg?.profileImgName).isEqualTo(memberProfileDummyEntity.profileImgName)
    }

    @Test
    fun `in절로 프로필 조회`() {
        // given
        val profileIds = getExistProfileIdList()

        // when
        val profiles = memberProfileReadRepository.readByProfileIdList(profileIds)

        // then
        assertThat(profiles.size).isEqualTo(profileIds.size)
    }
}