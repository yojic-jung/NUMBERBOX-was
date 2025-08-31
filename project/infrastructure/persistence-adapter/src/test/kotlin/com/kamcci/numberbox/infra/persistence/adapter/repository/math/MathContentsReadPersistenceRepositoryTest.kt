package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.NOT_EXIST_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMathContentsLikeReadRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMathContentsReadRepository
import com.kamcci.numberbox.infra.redis.mock.MockMathContentsLikeRedisRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class MathContentsReadPersistenceRepositoryTest {
    lateinit var mathContentsReadRepository: MockMathContentsReadRepository
    lateinit var mathContentsLikeReadRepository: MockMathContentsLikeReadRepository
    lateinit var mathContentsLikeRedisRepository: MockMathContentsLikeRedisRepository
    lateinit var mathContentsReadPersistenceRepository: MathContentsReadPersistenceRepository

    @BeforeEach
    fun setUp() {
        mathContentsReadRepository = MockMathContentsReadRepository()
        mathContentsLikeReadRepository = MockMathContentsLikeReadRepository()
        mathContentsLikeRedisRepository = MockMathContentsLikeRedisRepository()
        mathContentsReadPersistenceRepository = MathContentsReadPersistenceRepository(
            mathContentsReadRepository,
            mathContentsLikeReadRepository,
            mathContentsLikeRedisRepository
        )
    }

    @Test
    fun `readById 캐싱 후 조회`() {
        val contentId = NOT_EXIST_ID

        mathContentsReadPersistenceRepository.readById(contentId)
    }

    @Test
    fun `readById 이미 캐싱된 경우 캐싱 미처리`() {
        val contentId = EXIST_ID

        mathContentsReadPersistenceRepository.readById(contentId)
    }

    @Test
    fun `(단순호출) testReadById`() {
        val contentsId = listOf(1L, 2L)
        val pageReq = PageRequestImpl(0, 100)

        mathContentsReadPersistenceRepository.readById(contentsId, pageReq)
    }

    @Test
    fun `(단순호출) readDetailByContentsIdAndMemberId`() {
        mathContentsReadPersistenceRepository.readDetailByContentsIdAndMemberId(1L, UUID.randomUUID())
    }

    @Test
    fun `(단순호출) readDetailByMemberId`() {
        mathContentsReadPersistenceRepository.readDetailByMemberId(
            UUID.randomUUID(),
            ContentsSvcPosbSttsType.Approved,
            PageRequestImpl(0, 100)
        )
    }

    @Test
    fun `(단순호출) readDetailByMemberId2`() {
        mathContentsReadPersistenceRepository.readDetailByMemberId(
            UUID.randomUUID(),
            UUID.randomUUID(),
            ContentsSvcPosbSttsType.Approved,
            PageRequestImpl(0, 100)
        )
    }

    @Test
    fun `(단순호출) readDetailByUnitId`() {
        mathContentsReadPersistenceRepository.readDetailByUnitId(
            UUID.randomUUID(),
            listOf(1001, 1002),
            PageRequestImpl(0, 100)
        )
    }

    @Test
    fun `readInHouseContentsById 캐싱 후 조회`() {
        val contentId = NOT_EXIST_ID

        mathContentsReadPersistenceRepository.readInHouseContentsById(contentId)
    }

    @Test
    fun `readIpsiContentsById 캐싱 후 조회`() {
        val contentId = NOT_EXIST_ID
        mathContentsReadPersistenceRepository.readIpsiContentsById(contentId)
    }

    @Test
    fun `readTransContCntById 캐싱 후 조회`() {
        val contentId = NOT_EXIST_ID
        mathContentsReadPersistenceRepository.readTransContCntById(contentId)
    }

    @Test
    fun `readContentsOnly 캐싱 후 조회`() {
        val contentId = NOT_EXIST_ID
        mathContentsReadPersistenceRepository.readContentsOnly(contentId, UUID.randomUUID())
    }

    @Test
    fun `(단순호출) countByUnitId`() {
        mathContentsReadPersistenceRepository.countByUnitId(listOf(1))
    }

    @Test
    fun `(단순호출) existById`() {
        mathContentsReadPersistenceRepository.existById(1)
    }

}