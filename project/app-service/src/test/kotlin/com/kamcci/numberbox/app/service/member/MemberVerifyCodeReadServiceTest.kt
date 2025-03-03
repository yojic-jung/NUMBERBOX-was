package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.member.MemberVerifyCodeReadService.Companion.NOT_EXIST_CODE
import com.kamcci.numberbox.app.service.member.MemberVerifyCodeReadService.Companion.NOT_MATCHED_CODE
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberVerifyCodeReadOrmPort.Companion.CODE_NOT_EXIST_EMAIL
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberVerifyCodeReadOrmPort.Companion.EXPIRE_CODE_EMAIL
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberVerifyCodeReadOrmPort.Companion.MIS_MATCH_CODE
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberVerifyCodeReadOrmPort.Companion.VALID_RETURN_CODE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*

class MemberVerifyCodeReadServiceTest {

    private val memberVerifyCodeReadOrmPort = MockMemberVerifyCodeReadOrmPort()
    private val memberVerifyCodeReadUseCase =
        MemberVerifyCodeReadService(memberVerifyCodeReadOrmPort)

    companion object {
        const val VERIFY_CODE = "3e0c5f0e-3e12-488c-be48-88fdb92c2dd0"
    }


    @Test
    fun `인증 코드 미존재 - 실패`() {
        // given
        val email = CODE_NOT_EXIST_EMAIL
        val signUpDto =
            MemberVerifyCodeDto(email, UUID.fromString(VERIFY_CODE), VerifyCodeType.SignUp)

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
        assertThat(exception.msg).isEqualTo(NOT_EXIST_CODE)
    }

    @Test
    fun `인증 검증 코드 만료 시간 지남 - 실패`() {
        // given
        val email = EXPIRE_CODE_EMAIL
        val signUpDto =
            MemberVerifyCodeDto(email, UUID.fromString(VERIFY_CODE), VerifyCodeType.SignUp)


        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
        assertThat(exception.msg).isEqualTo(MemberVerifyCodeReadService.EXPIRED_CODE)
    }

    @Test
    fun `인증 코드 불일치 - 실패`() {
        // given
        val verifyCode = MIS_MATCH_CODE
        val signUpDto = MemberVerifyCodeDto(
            email = "any@test.com",
            verifyCode = UUID.fromString(verifyCode),
            VerifyCodeType.SignUp
        )

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
        assertThat(exception.msg).isEqualTo(NOT_MATCHED_CODE)
    }

    @Test
    fun `인증 유효성 검사 - 성공`() {
        // given
        val code = VALID_RETURN_CODE
        val signUpDto = MemberVerifyCodeDto(
            email = "any@test.com",
            verifyCode = UUID.fromString(code),
            VerifyCodeType.SignUp
        )

        // when & then
        assertDoesNotThrow {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
    }
}