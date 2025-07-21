package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMathContentsLikeWriteRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMathContentsRepoWriteRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsRepoWriteRepository
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsRepoRedisRepository
import com.kamcci.numberbox.infra.redis.mock.MockMathContentsLikeRedisRepository
import com.kamcci.numberbox.infra.redis.mock.MockMathContentsRepoRedisRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class MathContentsRepoWritePersistenceRepositoryTest {
    lateinit var mathContentsRepoRedisRepository: MockMathContentsRepoRedisRepository
    lateinit var mathContentsRepoWriteRepository: MockMathContentsRepoWriteRepository
    lateinit var mathContentsRepoWritePersistenceRepository: MathContentsRepoWritePersistenceRepository

    @BeforeEach
    fun setUp() {
        mathContentsRepoRedisRepository = MockMathContentsRepoRedisRepository()
        mathContentsRepoWriteRepository = MockMathContentsRepoWriteRepository()
        mathContentsRepoWritePersistenceRepository = MathContentsRepoWritePersistenceRepository(
            mathContentsRepoRedisRepository,
            mathContentsRepoWriteRepository,
        )
    }

    val modifyDto = MathContentsRepoModifyDto(1L, UUID.randomUUID())

    @Test
    fun save() {
        // when
        mathContentsRepoWritePersistenceRepository.save(modifyDto)

        // then
        assertThat(mathContentsRepoRedisRepository.executeCnt).isOne
    }

    @Test
    fun delete() {
        // when
        mathContentsRepoWritePersistenceRepository.delete(modifyDto)

        // then
        assertThat(mathContentsRepoRedisRepository.executeCnt).isOne
        assertThat(mathContentsRepoWriteRepository.executeCnt).isOne
    }

}