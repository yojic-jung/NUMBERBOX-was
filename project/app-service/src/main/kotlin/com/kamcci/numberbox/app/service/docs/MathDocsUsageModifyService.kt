package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageModifyUseCase

@UseCase
class MathDocsUsageModifyService : MathDocsUsageModifyUseCase {

    override fun create(createDto: MathDocsUsageCreateDto): Long {
        TODO("Not yet implemented")
    }
}