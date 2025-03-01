package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math

import java.util.*

/**
 * MathConLikeEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MathConLikeDummyFactory {
    // 조회 목적
    fun getMathConLikeDummyEntity() = ExistEntityInfo(1, UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"))

    // 삭제 목적
    fun getMathConLikeDummyEntity4Del() = ExistEntityInfo(2, UUID.fromString("11ED5466-CDA8-EA4D-9BC7-037CB86FDB20"))

    class ExistEntityInfo(val contentsId: Long, val memberId: UUID)
}