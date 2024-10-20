package com.kamcci.numberbox.restapi.mapper.mapper

import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsCreateDto
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsCreateRequest
import org.mapstruct.Mapper
import java.util.*

@Mapper(componentModel = "spring")
interface MathContentsMapper {
    fun toContents(memberId: UUID, request: MathContentsCreateRequest): MathContentsCreateDto
    fun toLicense(request: MathContentsCreateRequest): MathConLicenseCreateDto
}