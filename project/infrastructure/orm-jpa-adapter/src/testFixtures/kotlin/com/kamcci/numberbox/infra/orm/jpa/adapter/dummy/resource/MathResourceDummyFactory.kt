package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.resource

import java.util.*

/**
 * MathResourceEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MathResourceDummyFactory {
    const val NOT_EXIST_RESOURCE_ID = 999999L
    
    fun getMathResourceDummyEntityWithImg() =
        ExistEntityInfo(1, UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"))

    fun getMathResourceDummyEntity() = ExistEntityInfo(2, UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"))

    class ExistEntityInfo(val id: Long, val memberId: UUID)

}