package com.kamcci.numberbox.infra.redis.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.infra.redis.anntation.TCRedisTest
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TCRedisTest
class MathContentsRepoRedisRepositoryTest @Autowired constructor(
    private val mathContentsRepoRedisRepository: MathContentsRepoRedisRepository
) {
    private val existContentsId = 1L
    private val memberId = UUID.randomUUID()
    private val contentsRepoModifyDto = MathContentsRepoModifyDto(existContentsId, memberId)

    @Test
    fun `저장소 저장 - 성공`() {
        // when
        val isSaved = mathContentsRepoRedisRepository.save(contentsRepoModifyDto)

        // then
        AssertionsForClassTypes.assertThat(isSaved).isTrue()
    }


    @Test
    fun `저장소 삭제 - 성공`() {
        // given
        mathContentsRepoRedisRepository.save(contentsRepoModifyDto)

        // when
        val deleteCnt = mathContentsRepoRedisRepository.delete(contentsRepoModifyDto)

        // then
        AssertionsForClassTypes.assertThat(deleteCnt).isOne()
    }
}