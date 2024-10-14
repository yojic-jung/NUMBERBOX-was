package com.kamcci.numberbox.app.port.repository.math

import com.kamcci.numberbox.app.domain.vo.math.MathUnitInfoVo

interface MathUnitInfoReadOrmPort {
    // 단원 정보 전체 조회
    fun findAll(): List<MathUnitInfoVo>
}