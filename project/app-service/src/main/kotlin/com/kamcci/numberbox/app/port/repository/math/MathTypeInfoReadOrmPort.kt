package com.kamcci.numberbox.app.port.repository.math

import com.kamcci.numberbox.app.domain.vo.math.MathTypeInfoVo

/**
 * 수학 유형 정보 조회
 */
interface MathTypeInfoReadOrmPort {
    // 단원 하위 유형 조회
    fun readByUnitId(unitId: Int): List<MathTypeInfoVo>


    // 단원 하위 유형 조회
    fun readByUnitId(unitIdList: List<Int>): List<MathTypeInfoVo>
}