package com.kamcci.numberbox.app.service.stub.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperWriteCase
import java.util.*

class MockMathDocsPaperWriteCase : MathDocsPaperWriteCase {
    override fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long {
        TODO("Not yet implemented")
    }

    override fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto) {
        TODO("Not yet implemented")
    }

    override fun delete(docsId: Long, memberId: UUID) {
        TODO("Not yet implemented")
    }
}