package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsRepoWriteRepository

class MockMathContentsRepoWriteRepository: MathContentsRepoWriteRepository() {
    var executeCnt = 0

    override fun delete(modifyDto: MathContentsRepoModifyDto): Long {
        executeCnt++
        return 1L
    }
}