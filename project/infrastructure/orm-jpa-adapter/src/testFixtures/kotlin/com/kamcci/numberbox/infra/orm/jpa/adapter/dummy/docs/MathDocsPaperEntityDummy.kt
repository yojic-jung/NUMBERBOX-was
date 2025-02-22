package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs

import java.util.*

object MathDocsPaperEntityDummy {
    val DOCS_PAPER_MEMBER_ID = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    // 조회용
    fun getDocsPaperEntity4Read() = ExistEntityInfo(1L, DOCS_PAPER_MEMBER_ID)

    // 삭제용
    fun getDocsPaperEntity4Del() = ExistEntityInfo(2L, UUID.fromString("13ed5466-cda8-ea4d-9bc7-037cb86fdb20"))

    // memberId 소유 전체 삭제용
    fun getDocsPaperEntity4AllDel() = ExistEntityInfo(4L, UUID.fromString("14ed5466-cda8-ea4d-9bc7-037cb86fdb20"))

    // 수정 목적
    fun getDocsPaperEntity4Updt() = ExistEntityInfo(6L, UUID.fromString("14ad5466-cda8-ea4d-9bc7-037cb86fdb20"))

    class ExistEntityInfo(val id: Long, val memberId: UUID)
}