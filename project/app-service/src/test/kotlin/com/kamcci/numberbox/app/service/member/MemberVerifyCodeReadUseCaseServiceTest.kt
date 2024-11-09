package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.vo.member.MemberEmailVerifyCodeVo
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeReadOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
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
    fun `인증 코드 미존재 - 실패`() {
        `when`(memberVerifyCodeReadOrmPort.readByEmailAndCodeType(signUpDto.email, signUpDto.verifyCodeType))
            .thenReturn(null)

        // when & then
        assertThrows<BusinessValidException> {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
    }

    @Test
    fun `인증 검증 코드 만료 시간 지남 - 실패`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = VERIFY_CODE,
            sysCreateTime = LocalDateTime.now().minusSeconds(181)
        )
        `when`(memberVerifyCodeReadOrmPort.readByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp))
            .thenReturn(memberEmailVerifyCodeVo)

        // when & then
        assertThrows<BusinessValidException> {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
    }

    @Test
    fun `인증 코드 불일치 - 실패`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = "5c1d1a9a-3e12-488c-be48-88fdb92c2dd0",
            sysCreateTime = LocalDateTime.now().minusSeconds(180)
        )
        `when`(memberVerifyCodeReadOrmPort.readByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp)).thenReturn(
            memberEmailVerifyCodeVo
        )

        // when & then
        assertThrows<BusinessValidException> {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
    }

    @Test
    fun `인증 유효성 검사 - 성공`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = VERIFY_CODE,
            sysCreateTime = LocalDateTime.now().minusSeconds(180)
        )
        `when`(memberVerifyCodeReadOrmPort.readByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp)).thenReturn(
            memberEmailVerifyCodeVo
        )

        // when & then
        assertDoesNotThrow {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
    }
}