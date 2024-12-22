package com.kamcci.numberbox.restapi.mapper.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.restapi.dto.request.member.MemberPasswdUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberPhoneUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberPrivateSignupRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberSignupRequest
import org.mapstruct.Mapper
import java.util.*

@Mapper(componentModel = "spring")
interface MemberMapper {
    fun toSignupDto(req: MemberSignupRequest): MemberSignUpDto

    fun toSignupPrivateDto(req: MemberPrivateSignupRequest?): MemberPrivateSignUpDto?

    fun toPasswdUpdtDto(memberId: UUID, req: MemberPasswdUpdtRequest): MemberPasswdUpdtDto

    fun toPhoneUpdtDto(memberId: UUID, req: MemberPhoneUpdtRequest): MemberPhoneUpdtDto
}