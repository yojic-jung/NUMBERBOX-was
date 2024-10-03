package com.kamcci.numberbox.restapi.mapper.member

import com.kamcci.numberbox.app.domain.dto.member.*
import com.kamcci.numberbox.restapi.dto.request.member.*
import org.mapstruct.Mapper
import java.util.*

@Mapper(componentModel = "spring")
interface MemberMapper {
    fun toSignupDto(req: MemberSignupRequest): MemberSignUpDto
    
    fun toSignupPrivateDto(req: MemberPrivateSignupRequest?): MemberPrivateSignUpDto?

    fun toPasswdUpdtDto(memberId: UUID, req: MemberPasswdUpdtRequest): MemberPasswdUpdtDto

    fun toPhoneUpdtDto(memberId: UUID, req: MemberPhoneUpdtRequest): MemberPhoneUpdtDto

    fun toDropDto(memberId: UUID, req: MemberVerifyCodeRequest): MemberDropDto
}