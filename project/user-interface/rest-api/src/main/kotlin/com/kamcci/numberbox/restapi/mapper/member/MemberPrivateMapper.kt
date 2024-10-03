package com.kamcci.numberbox.restapi.mapper.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.restapi.dto.request.member.MemberPhoneUpdtRequest
import org.mapstruct.Mapper
import java.util.*

@Mapper(componentModel = "spring")
interface MemberPrivateMapper {
    fun toPhoneUpdtDto(memberId: UUID, req: MemberPhoneUpdtRequest): MemberPhoneUpdtDto
}