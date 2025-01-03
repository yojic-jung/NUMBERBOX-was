package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.port.etc.MemberPasswordEncoder
import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

class MemberFindServiceTest {
    private val memberReadCase: MemberReadCase = mock()
    private val memberWriteOrmPort: MemberWriteOrmPort = mock()
    private val memberVerifyCodeEmailPort: MemberVerifyCodeEmailPort = mock()
    private val passwordEncoder: MemberPasswordEncoder = mock()
    private val emailMessageTemplate: EmailMessageTemplate = mock()

    private val memberFindService = MemberFindService(
        memberReadCase,
        memberWriteOrmPort,
        memberVerifyCodeEmailPort,
        passwordEncoder,
        emailMessageTemplate,
    )

    @Test
    fun `내 email 조회 - 성공`() {
        // given
        val userName = "김회원"
        val phoneNumber = "01012341234"

        // when
        memberFindService.readMyEmail(userName, phoneNumber)

        // then
        verify(memberReadCase).readEmailByUsernameAndPhone(userName, phoneNumber)
    }

    @Test
    fun `내 비밀번호 조회 - 성공`() {
        // given
        val email = "email@email.net"
        val encodedPassword = "encodedPassword"
        Mockito.`when`(memberReadCase.existsByEmail(email)).thenReturn(true)
        Mockito.`when`(passwordEncoder.encode(any())).thenReturn(encodedPassword)

        // when
        memberFindService.readMyPassword(email)
    }

    @Test
    fun `내 비밀번호 조회 - 실패`() {
        // given
        val email = "email@email.net"
        Mockito.`when`(memberReadCase.existsByEmail(email)).thenReturn(false)

        // when
        val exception = assertThrows<BusinessInValidException> {
            memberFindService.readMyPassword(email)
        }
        assertThat(exception.msg).isEqualTo(MemberFindService.NOT_EXIST_USER)
    }
}