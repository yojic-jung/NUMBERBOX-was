package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberPrivateWriteOrmPort
import com.kamcci.numberbox.app.service.sample.MemberSampleData.getMemberPhoneUpdtDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MemberPrivateWriteServiceTest {
    private val memberPrivateWriteService = MemberPrivateWriteService(MockMemberPrivateWriteOrmPort())

    @Test
    fun `휴대폰 번호 변경 - 성공`() {
        // given
        val phoneUpdtDto = getMemberPhoneUpdtDto()

        // when
        val isUpdated = memberPrivateWriteService.updatePhoneNumber(phoneUpdtDto)

        // then
        assertThat(isUpdated).isTrue()
    }

    @Test
    fun `휴대폰 번호 변경 - 실패`() {
        // given
        val phoneUpdtDto = getMemberPhoneUpdtDto(FAIL_MEMBER_ID)

        // when
        val isUpdated = memberPrivateWriteService.updatePhoneNumber(phoneUpdtDto)

        // then
        assertThat(isUpdated).isFalse()
    }
}