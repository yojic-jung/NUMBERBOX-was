package com.kamcci.numberbox.restapi.stub

import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberPasswdUpdtDto
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberPhoneUpdtDto
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberPrivateSignUpDto
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberSignupDto
import com.kamcci.numberbox.app.service.stub.usecase.member.*
import com.kamcci.numberbox.app.usecase.member.*
import com.kamcci.numberbox.restapi.dto.request.member.MemberPasswdUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberPhoneUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberPrivateSignupRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberSignupRequest
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import org.springframework.context.annotation.Bean
import java.util.*

class MemberMockBeanConfig {
    @Bean
    fun memberFollowReadUseCase(): MemberFollowReadCase = MockMemberFollowReadCase()

    @Bean
    fun memberFollowModifyUseCase(): MemberFollowWriteCase = MockMemberFollowWriteCase()

    @Bean
    fun memberFindUseCase(): MemberFindReadCase = MockMemberFindReadCase()

    @Bean
    fun memberPrivateModifyUseCase(): MemberPrivateWriteCase = MockMemberPrivateWriteCase()

    @Bean
    fun memberLoginFailureUsecase(): MemberLoginFailureUseCase = MockMemberLoginFailureUseCase()

    @Bean
    fun memberModifyUseCase(): MemberWriteCase = MockMemberWriteCase()

    @Bean
    fun memberMapper(): MemberMapper = object : MemberMapper {
        override fun toSignupDto(req: MemberSignupRequest): MemberSignUpDto {
            return getMemberSignupDto()
        }

        override fun toSignupPrivateDto(req: MemberPrivateSignupRequest?): MemberPrivateSignUpDto {
            return getMemberPrivateSignUpDto()
        }

        override fun toPasswdUpdtDto(memberId: UUID, req: MemberPasswdUpdtRequest): MemberPasswdUpdtDto {
            return getMemberPasswdUpdtDto()
        }

        override fun toPhoneUpdtDto(memberId: UUID, req: MemberPhoneUpdtRequest): MemberPhoneUpdtDto {
            return getMemberPhoneUpdtDto()
        }
    }


    @Bean
    fun memberProfileReadUseCase(): MemberProfileReadCase = MockMemberProfileReadCase()

    @Bean
    fun memberProfileModifyUseCase(): MemberProfileWriteCase = MockMemberProfileWriteCase()

    @Bean
    fun memberVerifyCodeReadUseCase(): MemberVerifyCodeReadCase = MockMemberVerifyCodeReadCase()

    @Bean
    fun memberVerifyCodeSaveUseCase(): MemberVerifyCodeWriteCase = MockMemberVerifyCodeWriteCase()

    @Bean
    fun memberReadUseCase(): MemberReadCase = MockMemberReadCase()

    @Bean
    fun memberProfileFollowReadCase(): MemberProfileFollowReadCase = MockMemberProfileFollowReadCase()
}