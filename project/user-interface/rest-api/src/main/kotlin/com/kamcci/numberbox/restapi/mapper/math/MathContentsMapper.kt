package com.kamcci.numberbox.restapi.mapper.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsModifyRequest
import org.mapstruct.Mapper
import java.util.*

@Mapper(componentModel = "spring")
interface MathContentsMapper {
    fun toContents(memberId: UUID, request: MathContentsModifyRequest): MathContentsModifyDto
}