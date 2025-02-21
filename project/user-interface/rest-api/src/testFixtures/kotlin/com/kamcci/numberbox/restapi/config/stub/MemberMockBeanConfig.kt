package com.kamcci.numberbox.restapi.config.stub

import com.kamcci.numberbox.app.service.mock.usecase.member.*
import com.kamcci.numberbox.app.usecase.member.*
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kamcci.numberbox.restapi.mock.member.MockMemberMapper
import org.springframework.context.annotation.Bean

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
    fun memberMapper(): MemberMapper = MockMemberMapper()

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