package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member

import java.util.*

/**
 * MemberPrivateEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MemberPrivateDummyFactory {
    // 조회 목적
    fun getMemberPrivateDummyEntity() =
        ExistEntityInfo(UUID.fromString("33CA3122-CDA8-EA4D-9BC7-037CB86FDB20"), "정길동", "01012870987")

    // 삭제 목적
    fun getMemberPrivateDummyEntity4Del() =
        ExistEntityInfo(UUID.fromString("32CA3122-CDA8-EA4D-9BC7-037CB86FDB20"), "김길동", "01072870987")

    class ExistEntityInfo(val memberId: UUID, val userName: String, val phone: String)
}