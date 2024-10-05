package com.kamcci.numberbox.restapi.config.member

import com.kamcci.numberbox.app.usecase.member.*
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kamcci.numberbox.restapi.mapper.member.MemberPrivateMapper
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MemberControllerConfig {
    @Bean
    fun memberFollowReadUseCase(): MemberFollowReadUseCase = Mockito.mock()

    @Bean
    fun memberFollowModifyUseCase(): MemberFollowModifyUseCase = Mockito.mock()

    @Bean
    fun memberFindUseCase(): MemberFindUseCase = Mockito.mock()

    @Bean
    fun memberLoginFailureUsecase(): MemberLoginFailureUsecase = Mockito.mock()

    @Bean
    fun memberModifyUseCase(): MemberModifyUseCase = Mockito.mock()

    @Bean
    fun memberMapper(): MemberMapper = Mockito.mock()

    @Bean
    fun memberPrivateModifyUseCase(): MemberPrivateModifyUseCase = Mockito.mock()

    @Bean
    fun memberPrivateMapper(): MemberPrivateMapper = Mockito.mock()

    @Bean
    fun memberProfileReadUseCase(): MemberProfileReadUseCase = Mockito.mock()

    @Bean
    fun memberProfileModifyUseCase(): MemberProfileModifyUseCase = Mockito.mock()

    @Bean
    fun memberVerifyCodeReadUseCase(): MemberVerifyCodeReadUseCase = Mockito.mock()

    @Bean
    fun memberVerifyCodeSaveUseCase(): MemberVerifyCodeSaveUseCase = Mockito.mock()
}