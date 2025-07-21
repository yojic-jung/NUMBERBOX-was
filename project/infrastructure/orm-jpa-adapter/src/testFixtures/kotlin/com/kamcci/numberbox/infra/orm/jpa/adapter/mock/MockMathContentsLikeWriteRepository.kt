package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsLikeWriteRepository

class MockMathContentsLikeWriteRepository: MathContentsLikeWriteRepository() {
    var executeCnt = 0

    override fun delete(modifyDto: MathContentsLikeModifyDto): Long {
        executeCnt++
        return 1L
    }
}