package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.*
import java.util.*

object MemberFixture {
    fun getMemberSignupDto() = MemberSignUpDto(
        email = "signup@test.com",
        password = "1234"
    )

    fun getMemberPrivateSignUpDto() = MemberPrivateSignUpDto(
        userName = "김회원",
        phoneNumber = "01012341234",
        birth = "930123",
    )

    fun getMemberPasswdUpdtDto() = MemberPasswdUpdtDto(
        UUID.randomUUID(),
        "prevPW",
        "newPW",
        "newPW",
    )

    fun getMemberPasswdConfirmDto() = MemberPasswdConfirmDto(
        UUID.randomUUID(),
        "prevPW"
    )

    fun getMemberPhoneUpdtDto() = MemberPhoneUpdtDto(
        UUID.randomUUID(),
        "01012341234"
    )

    fun getMemberProfileImgUpdtDto() = MemberProfileImgUpdtDto(
        memberId = UUID.randomUUID(),
        profileImgPath = "path",
        profileImgName = "name",
    )
}