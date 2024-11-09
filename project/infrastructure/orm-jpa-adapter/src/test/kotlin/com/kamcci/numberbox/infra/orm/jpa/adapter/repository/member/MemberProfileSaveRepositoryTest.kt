package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberProfileEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberProfileSaveRepositoryTest(
    @Autowired
    private val entityManager: EntityManager,
    @Autowired
    private val memberProfileSaveRepo: MemberProfileSaveRepository
) {
    @Test
    fun `개인정보 영속화 테스트 - 성공`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val id = memberProfileSaveRepo.save(memberId, "nickname")
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
        org.junit.jupiter.api.assertThrows<ConstraintViolationException> {
            memberProfileSaveRepo.save(memberId, "nickname")
            entityManager.flush()
        }
    }

    private fun assertEntity(memberProfileEntity: MemberProfileEntity) {
        assertThat(memberProfileEntity).isNotNull
        assertThat(memberProfileEntity.profileImgName).isNull()
        assertThat(memberProfileEntity.profileImgPath).isNull()
        assertThat(memberProfileEntity.profileType).isEqualTo(ProfileType.None)
        assertThat(memberProfileEntity.hwpDownCnt).isEqualTo(0)
        assertThat(memberProfileEntity.unitMappingCnt).isEqualTo(0)
        assertThat(memberProfileEntity.aiContentsCnt).isEqualTo(0)
        assertThat(memberProfileEntity.sysUpdateTime).isNotNull
        assertThat(memberProfileEntity.sysCreateTime).isNotNull
    }
}