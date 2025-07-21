package com.kamcci.numberbox.infra.redis.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.infra.redis.anntation.TCRedisTest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*


@TCRedisTest
class MathContentsLikeRedisRepositoryTest @Autowired constructor(
    private val mathContentsLikeRedisRepository: MathContentsLikeRedisRepository
) {
    private val existContentsId = 1L
    private val noneExistContentsId = 100L
    private val memberId = UUID.randomUUID()
    private val contentsLikeModifyDto = MathContentsLikeModifyDto(existContentsId, memberId)

    @AfterEach
    fun cleanup() {
        mathContentsLikeRedisRepository.delete(contentsLikeModifyDto)
    }

    @Test
    fun `좋아요 키 존재여부`() {
        val hasLikeKey = mathContentsLikeRedisRepository.hasLikeKey(noneExistContentsId)

        assertThat(hasLikeKey).isFalse()
    }

    @Test
    fun `좋아요 사용자 일괄 저장`() {
        val memberIds = listOf(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
        )

        mathContentsLikeRedisRepository.cacheLikeMember(existContentsId, memberIds)
    }

    @Test
    fun `좋아요 사용자 일괄 저장 - 빈리스트`() {
        val memberIds: List<UUID> = listOf()

        mathContentsLikeRedisRepository.cacheLikeMember(existContentsId, memberIds)
    }


    @Test
    fun `좋아요 키 저장 - 성공`() {
        // when
        val isSaved = mathContentsLikeRedisRepository.save(contentsLikeModifyDto)

        // then
        assertThat(isSaved).isTrue()
    }

    @Test
    fun `좋아요 키 삭제 - 성공`() {
        // given
        mathContentsLikeRedisRepository.save(contentsLikeModifyDto)

        // when
        val deleteCnt = mathContentsLikeRedisRepository.delete(contentsLikeModifyDto)

        // then
        assertThat(deleteCnt).isOne()
    }

    @Test
    fun `좋아요 키,값 존재여부 - 성공`() {
        // when
        val isExist = mathContentsLikeRedisRepository.existByContentsIdAndMemberId(noneExistContentsId, UUID.randomUUID())

        // then
        assertThat(isExist).isFalse()
    }

    @Test
    fun `좋아요 키 집합 갯수 조회 - 성공`() {
        // when
        val setCount = mathContentsLikeRedisRepository.countBy(noneExistContentsId)

        // then
        assertThat(setCount).isZero()
    }
}