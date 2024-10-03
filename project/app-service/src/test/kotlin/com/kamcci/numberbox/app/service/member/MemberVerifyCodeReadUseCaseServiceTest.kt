package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.vo.member.MemberEmailVerifyCodeVo
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeResultVo.VerifyResultMSg.*
import com.kamcci.numberbox.app.port.repository.member.MemberVerifyCodeReadOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import java.util.*

class MemberVerifyCodeReadUseCaseServiceTest {

    private val memberVerifyCodeReadOrmPort: MemberVerifyCodeReadOrmPort = mock()
    private val memberVerifyCodeReadUseCase =
        MemberVerifyCodeReadUseCaseService(memberVerifyCodeReadOrmPort)

    companion object {
        const val EMAIL = "test@test.com"
        const val VERIFY_CODE = "3e0c5f0e-3e12-488c-be48-88fdb92c2dd0"

        val signUpDto =
            MemberVerifyCodeDto(EMAIL, UUID.fromString(VERIFY_CODE), VerifyCodeType.SignUp)
    }


    @Test
    fun `이메일 검증 코드 미존재 - 실패`() {
        `when`(memberVerifyCodeReadOrmPort.findByEmailAndCodeType(signUpDto.email, signUpDto.verifyCodeType))
            .thenReturn(null)
        // when
        val resultVo = memberVerifyCodeReadUseCase.validate(signUpDto)

        // then
        assertThat(resultVo.isSuccess).isFalse()
        assertThat(resultVo.messageType).isEqualTo(NOT_EXIST)
    }

    @Test
    fun `이메일 검증 코드 유효 시간 지남 - 실패`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = VERIFY_CODE,
            sysCreateTime = LocalDateTime.now().minusSeconds(181)
        )
        `when`(memberVerifyCodeReadOrmPort.findByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp))
            .thenReturn(memberEmailVerifyCodeVo)

        // when
        val resultVo = memberVerifyCodeReadUseCase.validate(signUpDto)

        assertThat(resultVo.isSuccess).isFalse()
        assertThat(resultVo.messageType).isEqualTo(EXPIRED_MSG)
    }

    @Test
    fun `이메일 검증 코드 불일치 - 실패`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = "5c1d1a9a-3e12-488c-be48-88fdb92c2dd0",
            sysCreateTime = LocalDateTime.now().minusSeconds(180)
        )
        `when`(memberVerifyCodeReadOrmPort.findByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp)).thenReturn(
            memberEmailVerifyCodeVo
        )

        // when
        val resultVo = memberVerifyCodeReadUseCase.validate(signUpDto)

        assertThat(resultVo.isSuccess).isFalse()
        assertThat(resultVo.messageType).isEqualTo(NOT_MATCH_CODE_MSG)
    }

//    @Test
//    fun `중복 이메일 존재 - 실패`() {
//        // given
//        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
//            verifyCode = VERIFY_CODE,
//            sysCreateTime = LocalDateTime.now().minusSeconds(180)
//        )
//        `when`(memberVerifyCodeReadOrmPort.findByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp)).thenReturn(
//            memberEmailVerifyCodeVo
//        )
//
//        // when
//        val resultVo = memberVerifyCodeReadUseCase.validate(signUpDto)
//
//        assertThat(resultVo.isSuccess).isFalse()
//        assertThat(resultVo.messageType).isEqualTo(EXIST_EMAIL_MSG)
//    }

    @Test
    fun `회원가입 양식 유효성 검사 - 성공`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = VERIFY_CODE,
            sysCreateTime = LocalDateTime.now().minusSeconds(180)
        )
        `when`(memberVerifyCodeReadOrmPort.findByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp)).thenReturn(
            memberEmailVerifyCodeVo
        )

        // when
        val resultVo = memberVerifyCodeReadUseCase.validate(signUpDto)

        assertThat(resultVo.isSuccess).isTrue()
    }
}