package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMathContentsLikeWriteRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMemberRefreshTokenRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMemberRepositorySupport
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsLikeWriteRepository
import com.kamcci.numberbox.infra.persistence.adapter.repository.auth.LoginSuccessEventListener
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsLikeRedisRepository
import com.kamcci.numberbox.infra.redis.mock.MockMathContentsLikeRedisRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class MathContentsLikeWritePersistenceRepositoryTest {
    lateinit var mathContentsLikeRedisRepository: MockMathContentsLikeRedisRepository
    lateinit var mathContentsLikeWriteRepository: MockMathContentsLikeWriteRepository
    lateinit var mathContentsLikeWritePersistenceRepository: MathContentsLikeWritePersistenceRepository

    @BeforeEach
    fun setUp() {
        mathContentsLikeRedisRepository = MockMathContentsLikeRedisRepository()
        mathContentsLikeWriteRepository = MockMathContentsLikeWriteRepository()
        mathContentsLikeWritePersistenceRepository = MathContentsLikeWritePersistenceRepository(
            mathContentsLikeRedisRepository,
            mathContentsLikeWriteRepository,
        )
    }

    @Test
    fun `(단순호출) save - 성공`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(1L, UUID.randomUUID())

        // when
        mathContentsLikeWritePersistenceRepository.save(modifyDto)

        // then
        assertThat(mathContentsLikeRedisRepository.executeCnt).isOne
    }

    @Test
    fun `(단순호출) delete - 성공`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(1L, UUID.randomUUID())

        // when
        mathContentsLikeWritePersistenceRepository.delete(modifyDto)

        // then
        assertThat(mathContentsLikeRedisRepository.executeCnt).isOne
        assertThat(mathContentsLikeWriteRepository.executeCnt).isOne
    }

}