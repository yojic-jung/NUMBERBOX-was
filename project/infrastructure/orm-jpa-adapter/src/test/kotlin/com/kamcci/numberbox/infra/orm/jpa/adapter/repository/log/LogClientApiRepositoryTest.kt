package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto
import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
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
        val requestLoggingDto = HttpRequestLoggingDto(
            memberId = UUID.randomUUID(),
            browser = "window",
            os = "mac",
            ip = "127.0.0.1",
            method = "POST",
            uri = "/example",
            reqBody = "{sfjl:dsjf}"
        )
        val responseLoggingDto = HttpResponseLoggingDto(200)
        val loggingDto = ClientLoggingInfoEventDto(requestLoggingDto, responseLoggingDto)

        // when
        val id = logClientApiRepository.save(loggingDto)
        em.flush()
        em.clear()

        // then
        assertThat(id).isGreaterThan(0)
    }

}