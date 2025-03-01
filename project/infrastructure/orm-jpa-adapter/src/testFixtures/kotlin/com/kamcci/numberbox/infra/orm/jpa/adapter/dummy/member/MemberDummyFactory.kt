package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member

import java.util.*

object MemberDummyFactory {
    const val NOT_EXIST_MEMBER_EMAIL = "not_exist@test.com"
    val NOT_EXIST_MEMBER_ID = UUID.fromString("88ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    fun getMemberDummyEntity() =
        ExistEntityInfo(UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20"), "wlrtl@test.com", "홍길동", "01009870987")

    // 계정 비활성 목적
    fun getMemberDummyEntity4Disable() = UUID.fromString("82CA3122-CDA8-EA4D-9BC7-037CB86FDB20")

    // 임시 비밀번호 발급 계정
    fun getTmpPwMemberId() = UUID.fromString("32ca3122-cda8-ea4d-9bc7-037cb86fdb20")

    // 비활성화된 계정
    fun getDisabledMemberId() = UUID.fromString("32ca3122-cda8-ea4d-9bc7-037cb86fdb20")

    class ExistEntityInfo(
        val memberId: UUID,
        val email: String,
        val userName: String? = null,
        val phone: String? = null
    )
}