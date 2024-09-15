package com.kamcci.numberbox.restapi.mapper.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.restapi.dto.request.member.MemberPrivateSignupRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberSignupRequest
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface MemberSignupMapper {
    fun toDto(req: MemberSignupRequest): MemberSignUpDto
    fun toPrivateDto(req: MemberPrivateSignupRequest?): MemberPrivateSignUpDto?
}