package com.kamcci.numberbox.restapi.stub.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.service.dummy.MemberDummyData
import com.kamcci.numberbox.restapi.dto.request.member.MemberPasswdUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberPhoneUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberPrivateSignupRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberSignupRequest
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import java.util.*

class MockMemberMapper : MemberMapper {
    override fun toSignupDto(req: MemberSignupRequest): MemberSignUpDto {
        return MemberDummyData.getMemberSignupDto()
    }

    override fun toSignupPrivateDto(req: MemberPrivateSignupRequest?): MemberPrivateSignUpDto {
        return MemberDummyData.getMemberPrivateSignUpDto()
    }

    override fun toPasswdUpdtDto(memberId: UUID, req: MemberPasswdUpdtRequest): MemberPasswdUpdtDto {
        return MemberDummyData.getMemberPasswdUpdtDto()
    }

    override fun toPhoneUpdtDto(memberId: UUID, req: MemberPhoneUpdtRequest): MemberPhoneUpdtDto {
        return MemberDummyData.getMemberPhoneUpdtDto()
    }
}