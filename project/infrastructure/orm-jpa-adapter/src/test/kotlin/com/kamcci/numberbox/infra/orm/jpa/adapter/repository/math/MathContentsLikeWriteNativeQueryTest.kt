package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathContentsLikeWriteNativeQueryTest(
    @Autowired
    private val mathContentsLikeWriteNativeQuery: MathContentsLikeWriteNativeQuery
) {
    @Test
    fun `좋아요 bulk insert - 성공`() {
        // given
        val contentsId = 1L
        val memberIdList = listOf(UUID.randomUUID().toString())

        // when
        mathContentsLikeWriteNativeQuery.bulkInsertLikes(contentsId, memberIdList)
    }

    @Test
    fun `좋아요 bulk insert - id 빈 리스트`() {
        // given
        val contentsId = 1L
        val memberIdList: List<String> = listOf()

        // when
        mathContentsLikeWriteNativeQuery.bulkInsertLikes(contentsId, memberIdList)
    }

    @Test
    fun `저장소 bulk insert - 성공`() {
        // given
        val contentsId = 1L
        val memberIdList = listOf(UUID.randomUUID().toString())

        // when
        mathContentsLikeWriteNativeQuery.bulkInsertRepo(contentsId, memberIdList)
    }

    @Test
    fun `저장소 bulk insert - id 빈 리스트`() {
        // given
        val contentsId = 1L
        val memberIdList: List<String> = listOf()

        // when
        mathContentsLikeWriteNativeQuery.bulkInsertRepo(contentsId, memberIdList)
    }
}