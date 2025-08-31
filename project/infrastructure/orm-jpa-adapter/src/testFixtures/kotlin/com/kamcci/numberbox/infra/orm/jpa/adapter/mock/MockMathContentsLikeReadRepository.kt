package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsLikeReadRepository
import java.util.UUID

class MockMathContentsLikeReadRepository: MathContentsLikeReadRepository() {

    override fun countBy(contentsId: Long): Long {
        return 1L
    }

    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return true
    }

    override fun readMemberIdListById(contentsId: Long): List<UUID> {
        return listOf()
    }
}