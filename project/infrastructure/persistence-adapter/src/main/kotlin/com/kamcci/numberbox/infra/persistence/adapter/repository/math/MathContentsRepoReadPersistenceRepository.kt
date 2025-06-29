package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsRepoReadRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.*

@Primary
@Repository
class MathContentsRepoReadPersistenceRepository(
    private val mathContentsRepoReadRepository: MathContentsRepoReadRepository
) : MathContentsRepoReadCase {
    override fun readContentsIdByMemberId(memberId: UUID): List<Long> {
        return mathContentsRepoReadRepository.readContentsIdByMemberId(memberId)
    }

    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return mathContentsRepoReadRepository.existByContentsIdAndMemberId(contentsId, memberId)
    }
}