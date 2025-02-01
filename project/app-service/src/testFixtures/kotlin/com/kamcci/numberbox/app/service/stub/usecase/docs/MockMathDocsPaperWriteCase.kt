package com.kamcci.numberbox.app.service.stub.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.service.constant.FailConstant
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperWriteCase
import java.util.*

class MockMathDocsPaperWriteCase : MathDocsPaperWriteCase {
    override fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long {
        return 1L
    }

    override fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto) {

    }

    override fun delete(docsId: Long, memberId: UUID) {
        if (docsId == FailConstant.FAIL_ID) throw RuntimeException()
    }
}