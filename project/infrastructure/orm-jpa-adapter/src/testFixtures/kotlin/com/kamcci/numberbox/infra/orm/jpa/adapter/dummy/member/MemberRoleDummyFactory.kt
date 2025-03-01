package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member

import java.util.*

object MemberRoleDummyFactory {

    val NOT_EXIST_ROLE_MEMBER = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    // 권한 조회 목적
    fun getMemberRoleDummyEntity() =
        ExistEntityInfo(1L, UUID.fromString("10CA3122-CDA8-EA4D-9BC7-037CB86FDB20"))

    // 권한 수정 목적
    fun getMemberRoleDummyEntity4Updt() =
        ExistEntityInfo(2L, UUID.fromString("33CA3122-CDA8-EA4D-9BC7-037CB86FDB20"))

    class ExistEntityInfo(
        val id: Long,
        val memberId: UUID,
    )
}