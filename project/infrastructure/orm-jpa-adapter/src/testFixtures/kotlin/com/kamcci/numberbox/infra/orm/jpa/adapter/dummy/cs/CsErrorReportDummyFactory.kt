package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.cs

import java.util.*

/**
 * CsErrorReportEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object CsErrorReportDummyFactory {
    fun getCsErrorReportDummyEntity() = ExistEntityInfo(1L, UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"))

    // 모든 값 다 존재하는 더미 데이터
    fun getCsErrorReportAllValueDummyEntity() =
        ExistEntityInfo(3L, UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"))

    class ExistEntityInfo(val id: Long, val memberId: UUID)

}