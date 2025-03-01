package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberProfileDummyFactory.getMemberProfileDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberProfileEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberProfileWriteRepositoryTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberProfileModifyRepository: MemberProfileWriteRepository
) {
    private val memberProfileDummyEntity = getMemberProfileDummyEntity()

    @Test
    fun `개인정보 영속화 테스트 - 성공`() {
        // given
        val memberId = memberProfileDummyEntity.memberId

        // when
        val id = memberProfileModifyRepository.save(memberId, "nickname")
        entityManager.flush()
        entityManager.clear()

        // then
        val memberProfileEntity = entityManager.find(MemberProfileEntity::class.java, id)
        assertEntity(memberProfileEntity)
    }

    @Test
    fun `개인정보 영속화 member 없는 경우 - 실패`() {
        // given
        val memberId = UUID.fromString("29ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        assertThrows<ConstraintViolationException> {
            memberProfileModifyRepository.save(memberId, "nickname")
            entityManager.flush()
        }
    }

    private fun assertEntity(memberProfileEntity: MemberProfileEntity) {
        assertThat(memberProfileEntity).isNotNull
        assertThat(memberProfileEntity.profileImgName).isNull()
        assertThat(memberProfileEntity.profileImgPath).isNull()
        assertThat(memberProfileEntity.profileType).isEqualTo(ProfileType.None)
        assertThat(memberProfileEntity.hwpDownCnt).isZero()
        assertThat(memberProfileEntity.unitMappingCnt).isZero()
        assertThat(memberProfileEntity.aiContentsCnt).isZero()
        assertThat(memberProfileEntity.sysUpdateTime).isNotNull
        assertThat(memberProfileEntity.sysCreateTime).isNotNull
    }

    @Test
    fun `프로필 타입 변경`() {
        // given
        val memberId = memberProfileDummyEntity.memberId
        val profileType = ProfileType.HeadOfAcademy

        // when
        memberProfileModifyRepository.updateProfileTypeByMemberId(memberId, profileType)
        entityManager.flush()
        entityManager.clear()

        // then
        val memberProfileEntity =
            entityManager.find(MemberProfileEntity::class.java, memberProfileDummyEntity.profileId)
        assertThat(memberProfileEntity.profileType).isEqualTo(profileType)
    }

    @Test
    fun `프로필 이미지 변경`() {
        // given
        val memberId = memberProfileDummyEntity.memberId
        val newProfileImgPath = "newProfileImgPath"
        val newProfileImgName = "newprofileImgName"
        val profileImgInfo = MemberProfileImgUpdtDto(memberId, newProfileImgPath, newProfileImgName)

        // when
        memberProfileModifyRepository.updateImgByMemberId(profileImgInfo)
        entityManager.flush()
        entityManager.clear()

        // then
        val memberProfileEntity =
            entityManager.find(MemberProfileEntity::class.java, memberProfileDummyEntity.profileId)
        assertThat(memberProfileEntity.profileImgPath).isEqualTo(profileImgInfo.profileImgPath)
        assertThat(memberProfileEntity.profileImgName).isEqualTo(profileImgInfo.profileImgName)
    }


    @Test
    fun `프로필 닉네임 변경`() {
        // given
        val memberId = memberProfileDummyEntity.memberId
        val nickname = "닉네임"

        // when
        memberProfileModifyRepository.updateNicknameByMemberId(memberId, nickname)
        entityManager.flush()
        entityManager.clear()

        // then
        val memberProfileEntity =
            entityManager.find(MemberProfileEntity::class.java, memberProfileDummyEntity.profileId)
        assertThat(memberProfileEntity.nickname).isEqualTo(nickname)
    }

    @Test
    fun `한글 다운로드 수 전체 초기화`() {
        // given
        val hwpDownCnt = 0

        // when
        val executeRowCnt = memberProfileModifyRepository.updateHwpDownCntByMemberId(hwpDownCnt)

        // then
        assertThat(executeRowCnt).isPositive()
    }
}