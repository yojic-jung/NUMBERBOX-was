package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberProfileEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberProfileModifyRepositoryTest(
    @Autowired
    private val entityManager: EntityManager,
    @Autowired
    private val memberProfileModifyRepository: MemberProfileModifyRepository
) {
    @Test
    fun `프로필 타입 변경`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val profileType = ProfileType.HeadOfAcademy

        // when
        memberProfileModifyRepository.updateProfileTypeByMemberId(memberId, profileType)
        entityManager.flush()
        entityManager.clear()

        // then
        val memberProfileEntity = entityManager.find(MemberProfileEntity::class.java, 1)
        assertThat(memberProfileEntity.profileType).isEqualTo(profileType)
    }

    @Test
    fun `프로필 이미지 변경`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val profileImgPath = "profileImgPath"
        val profileImgName = "profileImgName"
        val profileImgInfo = MemberProfileImgUpdtDto(memberId, profileImgPath, profileImgName)

        // when
        memberProfileModifyRepository.updateImgByMemberId(profileImgInfo)
        entityManager.flush()
        entityManager.clear()

        // then
        val memberProfileEntity = entityManager.find(MemberProfileEntity::class.java, 1)
        assertThat(memberProfileEntity.profileImgPath).isEqualTo(profileImgInfo.profileImgPath)
        assertThat(memberProfileEntity.profileImgName).isEqualTo(profileImgInfo.profileImgName)
    }


    @Test
    fun `프로필 닉네임 변경`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val nickname = "닉네임"

        // when
        memberProfileModifyRepository.updateNicknameByMemberId(memberId, nickname)
        entityManager.flush()
        entityManager.clear()

        // then
        val memberProfileEntity = entityManager.find(MemberProfileEntity::class.java, 1)
        assertThat(memberProfileEntity.nickname).isEqualTo(nickname)
    }
}