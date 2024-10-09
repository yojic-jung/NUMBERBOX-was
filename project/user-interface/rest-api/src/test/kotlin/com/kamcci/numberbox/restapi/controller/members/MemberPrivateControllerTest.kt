package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.usecase.member.MemberPrivateModifyUseCase
import com.kammci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kammci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@WebMvcUnitTest
class MemberPrivateControllerTest : BaseMockMvcTest() {
    @Autowired
    lateinit var memberPrivateModifyUseCase: MemberPrivateModifyUseCase

    companion object {
        const val UPDATE_PHONE_URL = "/member/phone"
    }

    @Test
    fun `휴대폰 번호 변경 - 성공`() {
        // given
        val verifyCode = UUID.randomUUID()
        val reqBody = mapOf(
            "verifyCode" to verifyCode,
            "phoneNumber" to "01012345678"
        )
        `when`(memberPrivateModifyUseCase.updatePhoneNumber(any())).thenReturn(true)

        //when
        val resultAction = requestJsonPut(UPDATE_PHONE_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun `휴대폰 번호 변경 - 실패(유효하지 않은 휴대폰 번호)`() {
        // given
        val verifyCode = UUID.randomUUID()
        val reqBody = mapOf(
            "verifyCode" to verifyCode,
            "phoneNumber" to "010123456"
        )

        //when
        val resultAction = requestJsonPut(UPDATE_PHONE_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

}