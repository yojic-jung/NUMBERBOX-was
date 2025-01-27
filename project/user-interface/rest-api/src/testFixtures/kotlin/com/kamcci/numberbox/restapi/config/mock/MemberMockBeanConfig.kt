package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.usecase.member.*
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import org.mockito.Mockito.mock
import org.springframework.context.annotation.Bean

class MemberMockBeanConfig {
    @Bean
    fun memberFollowReadUseCase(): MemberFollowReadCase = mock()

    @Bean
    fun memberFollowModifyUseCase(): MemberFollowWriteCase = mock()

    @Bean
    fun memberFindUseCase(): MemberFindReadCase = mock()

    @Bean
    fun memberLoginFailureUsecase(): MemberLoginFailureUseCase = mock()

    @Bean
    fun memberModifyUseCase(): MemberWriteCase = mock()

    @Bean
    fun memberMapper(): MemberMapper = mock()

    @Bean
    fun memberPrivateModifyUseCase(): MemberPrivateWriteCase = mock()

    @Bean
    fun memberProfileReadUseCase(): MemberProfileReadCase = mock()

    @Bean
    fun memberProfileModifyUseCase(): MemberProfileWriteCase = mock()

    @Bean
    fun memberVerifyCodeReadUseCase(): MemberVerifyCodeReadCase = mock()

    @Bean
    fun memberVerifyCodeSaveUseCase(): MemberVerifyCodeWriteCase = mock()

    @Bean
    fun memberReadUseCase(): MemberReadCase = mock()

    @Bean
    fun memberProfileFollowReadCase(): MemberProfileFollowReadCase = mock()
}