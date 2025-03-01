package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import java.util.*

/**
 * MemberVerifyCodeEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MemberVerifyCodeDummyFactory {

    // 인증 코드 조회 목적
    fun getMemberVerifyCodeDummyEntity() =
        ExistEntityInfo(
            "wlrtl@test.com",
            UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20"),
            VerifyCodeType.SignUp
        )

    // 인증 코드 수정 목적
    fun getMemberVerifyCodeDummyEntity4Updt() =
        ExistEntityInfo(
            "wlrtl22@test.com", UUID.fromString("32CA3122-CDA8-EA4D-9BC7-037CB86FDB20"),
            VerifyCodeType.SignUp
        )

    class ExistEntityInfo(
        val email: String,
        val memberId: UUID,
        val verifyCodeType: VerifyCodeType
    )
}