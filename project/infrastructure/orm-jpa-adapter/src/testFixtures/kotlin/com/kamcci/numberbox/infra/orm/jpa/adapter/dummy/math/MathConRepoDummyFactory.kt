package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math

import java.util.*

/**
 * MathConRepoistoryEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MathConRepoDummyFactory {
    const val NOT_EXIST_CONTENTS_ID = 999999L

    // 조회 목적
    fun getMathConRepoDummyEntity() = ExistEntityInfo(1L, UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"))

    // 삭제 목적
    fun getMathConRepoDummyEntity4Del() = ExistEntityInfo(2L, UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"))

    class ExistEntityInfo(
        val contentsId: Long,
        val memberId: UUID,
    )
}