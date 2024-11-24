package com.kamcci.numberbox.restapi.config.member

import com.kamcci.numberbox.app.usecase.member.*
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MemberControllerConfig {
    @Bean
    fun memberFollowReadUseCase(): MemberFollowReadUseCase = mock()

    @Bean
    fun memberFollowModifyUseCase(): MemberFollowWriteUseCase = mock()

    @Bean
    fun memberFindUseCase(): MemberFindUseCase = mock()

    @Bean
    fun memberLoginFailureUsecase(): MemberLoginFailureUsecase = mock()

    @Bean
    fun memberModifyUseCase(): MemberWriteUseCase = mock()

    @Bean
    fun memberMapper(): MemberMapper = mock()

    @Bean
    fun memberPrivateModifyUseCase(): MemberPrivateWriteUseCase = mock()

    @Bean
    fun memberProfileReadUseCase(): MemberProfileReadUseCase = mock()

    @Bean
    fun memberProfileModifyUseCase(): MemberProfileWriteUseCase = mock()

    @Bean
    fun memberVerifyCodeReadUseCase(): MemberVerifyCodeReadUseCase = mock()

    @Bean
    fun memberVerifyCodeSaveUseCase(): MemberVerifyCodeWriteUseCase = mock()

    @Bean
    fun memberReadUseCase(): MemberReadUseCase = mock()
}