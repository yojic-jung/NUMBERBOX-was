package com.kamcci.numberbox.restapi.mock.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsModifyRequest
import com.kamcci.numberbox.restapi.mapper.math.MathContentsMapper
import java.util.*

class MockMathContentsMapper : MathContentsMapper {
    override fun toContents(memberId: UUID, request: MathContentsModifyRequest): MathContentsModifyDto {
        return MathContentsSampleData.getMathContentsModifyDto()
    }
}