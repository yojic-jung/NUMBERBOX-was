package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import java.util.*

/**
 * MathContentsEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MathContentsDummyFactory {
    const val NOT_EXIST_CONTENTS_ID = 999999L

    // 자체 제작 수학문제
    fun getInHouseContentsDummyEntity() =
        ExistEntityInfo(
            1,
            UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"),
            22003,
            1,
            2,
            ContentsClassifyType.InHouse,
            0
        )

    // 입시 수학문제
    fun getIpsiContentsDummyEntity() =
        ExistEntityInfo(
            4907,
            UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"),
            31002,
            3,
            3,
            ContentsClassifyType.Ipsi,
            0
        )

    // 입시 수학문제 리스트
    fun getIpsiContentsDummyEntityList() = listOf(
        ExistEntityInfo(
            4908,
            UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"),
            31001,
            2,
            3,
            ContentsClassifyType.Ipsi,
            3
        ),
        ExistEntityInfo(
            4907,
            UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"),
            31002,
            3,
            3,
            ContentsClassifyType.Ipsi,
            3
        ),
        ExistEntityInfo(
            4910,
            UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"),
            31003,
            2,
            4,
            ContentsClassifyType.Ipsi,
            4
        ),
        ExistEntityInfo(
            4909,
            UUID.fromString("10ED5466-CDA8-EA4D-9BC7-037CB86FDB20"),
            31004,
            3,
            4,
            ContentsClassifyType.Ipsi,
            4
        ),
    )

    class ExistEntityInfo(
        val contentsId: Long,
        val memberId: UUID,
        val unitId: Int,
        val typeId: Int,
        val quesLevel: Int,
        val contentsClassifyType: ContentsClassifyType,
        val transConCtn: Int
    )
}