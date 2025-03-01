package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.hwp

import java.util.*

/**
 * HwpConvertContentEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object HwpConvertContentDummyFactory {
    val CONVERT_CON_MEM_ID = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    // 삭제 목적
    fun getHwpContentsDummyEntity4Del() = ExistEntityInfo(4L, UUID.fromString("15ad5466-cda8-ea4d-9bc7-037cb86fdb20"))

    // 수정 목적
    fun getHwpContentsDummyEntity4Updt() = ExistEntityInfo(2L, UUID.fromString("11fd5466-cda8-ea4d-9bc7-037cb86fdb20"))

    class ExistEntityInfo(val id: Long, val memberId: UUID)
}