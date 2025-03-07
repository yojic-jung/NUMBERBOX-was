package com.kamcci.numberbox.app.domain.dto.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiManageInsType
import com.kamcci.numberbox.app.domain.enumeration.math.IpsiPaperType

/**
 * 입시 수학문제 출처 정보 변경 dto
 */
data class MathConIpsiSrcModifyDto(
    // 출제 기관
    val manageIns: IpsiManageInsType,
    // 출제 연도
    val impYear: Int,
    // 출제 월
    val impMonth: Int,
    // 오답률
    val wrongRatio: Int,
    // 가/나형 구분 : 1 (통합) 2 (가) 3 (나)
    val paperType: IpsiPaperType,
    // 홀수형 번호
    val oddQuesNum: Int,
    // 짝수형 번호
    val evenQuesNum: Int,
)