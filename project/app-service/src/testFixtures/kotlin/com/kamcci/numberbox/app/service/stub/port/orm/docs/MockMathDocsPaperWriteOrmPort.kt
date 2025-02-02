package com.kamcci.numberbox.app.service.stub.port.orm.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import java.util.*

class MockMathDocsPaperWriteOrmPort : MathDocsPaperWriteOrmPort {
    override fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long {
        return if (memberId == FAIL_MEMBER_ID) return 0L else 1L
    }

    override fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto): Long {
        return if (memberId == FAIL_MEMBER_ID) return 0L else 1L
    }

    override fun updateDocsSttsByIdAndMemberId(docsId: Long, memberId: UUID, docsStts: DocsStatusType): Long {
        return if (docsId == FAIL_ID) return 0L else 1L
    }

    override fun delete(docsId: Long, memberId: UUID): Long {
        return if (docsId == FAIL_ID) return 0L else 1L
    }

    override fun delete(memberId: UUID): Long {
        return if (memberId == FAIL_MEMBER_ID) return 0L else 1L
    }

}