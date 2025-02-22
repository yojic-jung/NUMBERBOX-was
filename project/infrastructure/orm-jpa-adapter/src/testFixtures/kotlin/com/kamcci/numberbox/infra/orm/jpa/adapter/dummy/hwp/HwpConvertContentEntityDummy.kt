package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.hwp

import java.util.*

object HwpConvertContentEntityDummy {
    val CONVERT_CON_MEM_ID = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    fun getHwpContents4Del() = ExistEntityInfo(4L, UUID.fromString("15ad5466-cda8-ea4d-9bc7-037cb86fdb20"))

    fun getHwpContents4Updt() = ExistEntityInfo(2L, UUID.fromString("11fd5466-cda8-ea4d-9bc7-037cb86fdb20"))

    class ExistEntityInfo(val id: Long, val memberId: UUID)
}