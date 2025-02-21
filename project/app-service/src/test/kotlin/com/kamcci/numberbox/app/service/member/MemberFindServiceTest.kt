package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_EMAIL
import com.kamcci.numberbox.app.service.mock.port.email.MockEmailMessageTemplate
import com.kamcci.numberbox.app.service.mock.port.email.member.MockMemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.service.mock.port.etc.MockMemberPasswordEncoder
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberWriteOrmPort
import com.kamcci.numberbox.app.service.mock.usecase.member.MockMemberReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MemberFindServiceTest {
    private val memberFindService = MemberFindService(
        MockMemberReadCase(),
        MockMemberWriteOrmPort(),
        MockMemberVerifyCodeEmailPort(),
        MockMemberPasswordEncoder(),
        MockEmailMessageTemplate(),
    )

    @Test
    fun `내 email 조회 - 성공`() {
        // given
        val userName = "김회원"
        val phoneNumber = "01012341234"

        // when
        val email = memberFindService.readMyEmail(userName, phoneNumber)

        // then
        assertThat(email).isNotNull()
    }

    @Test
    fun `임시 비밀번호 발급 - 성공`() {
        // given
        val email = EXIST_EMAIL

        // when
        memberFindService.sendNewTempPassword(email)
    }

    @Test
    fun `임시 비밀번호 발급 - 실패`() {
        // given
        val email = EXIST_EMAIL.reversed()

        // when
        val exception = assertThrows<BusinessInValidException> {
            memberFindService.sendNewTempPassword(email)
        }
        assertThat(exception.msg).isEqualTo(MemberFindService.NOT_EXIST_USER)
    }
}