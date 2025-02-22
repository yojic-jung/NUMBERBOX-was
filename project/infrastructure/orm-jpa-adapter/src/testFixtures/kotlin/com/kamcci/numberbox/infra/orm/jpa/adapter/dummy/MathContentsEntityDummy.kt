package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType

object MathContentsEntityDummy {
    const val INHOUSE_CONTENTS_ID = 1L

    // 자체제작 수학 문제
    fun getInHouseContentsEntity() =
        ExistEntityInfo(INHOUSE_CONTENTS_ID, 22003, 1, ContentsClassifyType.InHouse, 2)

    // 입시 수학문제 정보
    fun getIpsiContentsEntity() = listOf(
        ExistEntityInfo(4908, 31001, 2, ContentsClassifyType.Ipsi, 3),
        ExistEntityInfo(4907, 31002, 3, ContentsClassifyType.Ipsi, 3),
        ExistEntityInfo(4910, 31003, 2, ContentsClassifyType.Ipsi, 4),
        ExistEntityInfo(4909, 31004, 3, ContentsClassifyType.Ipsi, 4),
    )


    class ExistEntityInfo(
        val id: Long,
        val unitId: Int,
        val typeId: Int,
        val contentsClassify: ContentsClassifyType,
        val quesLevel: Int
    )
}