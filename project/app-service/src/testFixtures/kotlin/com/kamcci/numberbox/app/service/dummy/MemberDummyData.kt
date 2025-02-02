package com.kamcci.numberbox.app.service.dummy

import com.kamcci.numberbox.app.domain.dto.member.*
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import java.util.*

object MemberDummyData {
    fun getMemberSignupDto(email: String = "signup@test.com") = MemberSignUpDto(
        email = email,
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

    fun getMemberProfileVo(id: Long = 1L): MemberProfileVo {
        return MemberProfileVo(
            id = id,
            memberId = UUID.randomUUID(),
            nickname = "",
            profileImgName = "",
            profileImgPath = "",
            profileType = ProfileType.Etc,
        )
    }

    fun getMemberProfileVoList(size: Int = 10): List<MemberProfileVo> {
        val profileList: MutableList<MemberProfileVo> = mutableListOf()
        for (i in 1..size) {
            profileList.add(getMemberProfileVo(i.toLong()))
        }
        return profileList
    }

    fun getMemberProfileImgVo(id: Long = 1L): MemberProfileImgVo {
        return MemberProfileImgVo(id, UUID.randomUUID(), null, null)
    }

    fun getMemberProfileImgVoList(size: Int = 100): List<MemberProfileImgVo> {
        val profileImgList: MutableList<MemberProfileImgVo> = mutableListOf()
        for (i in 1..size) {
            profileImgList.add(getMemberProfileImgVo(i.toLong()))
        }
        return profileImgList
    }
}