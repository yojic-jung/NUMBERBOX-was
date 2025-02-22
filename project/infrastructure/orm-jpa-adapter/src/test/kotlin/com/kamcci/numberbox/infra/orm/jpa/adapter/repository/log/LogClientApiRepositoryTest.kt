package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberEntityDummy.MEMBER_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.sample.common.CommonSampleData.getClientLoggingInfoEventDto
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class LogClientApiRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val logClientApiRepository: LogClientApiRepository
) {
    @Test
    fun `로깅 정보 영속화 - 성공`() {
        // given
        val loggingDto = getClientLoggingInfoEventDto(MEMBER_ID)

        // when
        val id = logClientApiRepository.save(loggingDto)
        em.flush()
        em.clear()

        // then
        assertThat(id).isGreaterThan(0)
    }

}