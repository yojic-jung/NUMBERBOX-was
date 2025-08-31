package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.hwp

import java.util.*

/**
 * HwpConvertContentEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object HwpConvertFileDummyFactory {
    fun getHwpConvertFileDummyEntity() = ExistEntityInfo(1L, UUID.fromString("11EF8609-5BAA-74F7-9600-E73F9022DD9F"))

    class ExistEntityInfo(val id: Long, val memberId: UUID)
}