package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.service.sample.MathDocsSampleData.getMathDocsUsageCreateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberEntityDummy.MEMBER_ID
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathDocsUsageWriteRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val mathDocsUsageWriteRepository: MathDocsUsageWriteRepository
) {
    @Test
    fun `학습지 사용 집계 자료 영속화`() {
        // given
        val memberId = MEMBER_ID
        val usageDto = getMathDocsUsageCreateDto()

        // when
        val id = mathDocsUsageWriteRepository.create(memberId, usageDto)
        em.flush()
        em.clear()

        assertThat(id).isPositive()
    }
}