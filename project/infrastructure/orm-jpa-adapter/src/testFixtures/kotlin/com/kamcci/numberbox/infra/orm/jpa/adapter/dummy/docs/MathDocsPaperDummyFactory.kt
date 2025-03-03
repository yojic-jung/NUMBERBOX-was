package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs

import java.util.*

/**
 * MathDocsPaperEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MathDocsPaperDummyFactory {
    // 조회 목적
    fun getDocsPaperDummyEntity4Read() = ExistEntityInfo(1L, UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20"))

    // 삭제 목적
    fun getDocsPaperDummyEntity4Del() = ExistEntityInfo(2L, UUID.fromString("13ed5466-cda8-ea4d-9bc7-037cb86fdb20"))

    // memberId 소유 전체 삭제 목적
    fun getDocsPaperDummyEntity4AllDel() = ExistEntityInfo(4L, UUID.fromString("14ed5466-cda8-ea4d-9bc7-037cb86fdb20"))

    // 수정 목적
    fun getDocsPaperDummyEntity4Updt() = ExistEntityInfo(6L, UUID.fromString("14ad5466-cda8-ea4d-9bc7-037cb86fdb20"))

    class ExistEntityInfo(val id: Long, val memberId: UUID)
}