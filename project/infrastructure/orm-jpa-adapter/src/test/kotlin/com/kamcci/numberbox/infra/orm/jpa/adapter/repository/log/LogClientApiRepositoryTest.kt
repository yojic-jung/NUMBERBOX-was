package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto
import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getMemberDummyEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class LogClientApiRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val logClientApiRepository: LogClientApiRepository
) {
    @Test
    fun `로깅 정보 영속화 - 성공`() {
        // given
        val memberId = getMemberDummyEntity().memberId
        val loggingDto = getClientLoggingInfoEventDto(memberId)

        // when
        val id = logClientApiRepository.save(loggingDto)
        em.flush()
        em.clear()

        // then
        assertThat(id).isPositive()
    }

    private fun getClientLoggingInfoEventDto(memberId: UUID, httpStatus: Int = 200) =
        ClientLoggingInfoEventDto(
            HttpRequestLoggingDto(
                memberId,
                "Chrome",
                "Mac",
                "127.0.0.1",
                "GET",
                "/sdfa/adf",
                "sadf"
            ), HttpResponseLoggingDto(httpStatus)
        )
}