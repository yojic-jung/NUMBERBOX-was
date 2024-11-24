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
    fun memberFollowModifyUseCase(): MemberFollowModifyUseCase = mock()

    @Bean
    fun memberFindUseCase(): MemberFindUseCase = mock()

    @Bean
    fun memberLoginFailureUsecase(): MemberLoginFailureUsecase = mock()

    @Bean
    fun memberModifyUseCase(): MemberModifyUseCase = mock()

    @Bean
    fun memberMapper(): MemberMapper = mock()

    @Bean
    fun memberPrivateModifyUseCase(): MemberPrivateModifyUseCase = mock()

    @Bean
    fun memberProfileReadUseCase(): MemberProfileReadUseCase = mock()

    @Bean
    fun memberProfileModifyUseCase(): MemberProfileModifyUseCase = mock()

    @Bean
    fun memberVerifyCodeReadUseCase(): MemberVerifyCodeReadUseCase = mock()

    @Bean
    fun memberVerifyCodeSaveUseCase(): MemberVerifyCodeModifyUseCase = mock()

    @Bean
    fun memberReadUseCase(): MemberReadUseCase = mock()
}