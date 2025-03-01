package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member

import java.util.*

/**
 * MemberProfileEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MemberProfileDummyFactory {

    fun getExistProfileIdList() = listOf(1L, 2L)

    // 조회 목적
    fun getMemberProfileDummyEntity() =
        ExistEntityInfo(
            1L,
            UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"),
            "/test/path",
            "testImg.png"
        )

    // 삭제 목적
    fun getMemberProfileDummyEntity4Del() =
        ExistEntityInfo(
            2L,
            UUID.fromString("33CA3122-CDA8-EA4D-9BC7-037CB86FDB20"),
            "/test/path",
            "testImg2.png"
        )

    class ExistEntityInfo(
        val profileId: Long,
        val memberId: UUID,
        val profileImgPath: String,
        val profileImgName: String
    )
}